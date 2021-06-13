package com.CampusNavigation.Map;

import android.content.res.AssetManager;

import com.CampusNavigation.GraphImport.Graph.Dot;
import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Route;
import com.CampusNavigation.GraphImport.graphManage.graphManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.abs;
import static java.lang.Math.max;

/**
 * @see SpecificBuild 楼类有层，有图的数组，有道路的数组
 * @see SpecificBuild#SpecificBuild(Dot, Map, AssetManager)  通过生成的图构建邻接表，构造具体建筑物对象
 * @see SpecificBuild#getExit(int)
 * @see SpecificBuild#inBuilding(String)
 * @see SpecificBuild#getShortestRoute(int, int, String, Map)
 * @see SpecificBuild#getMapOfFloor(int)
 * @see SpecificBuild#mapOfFloor
 * @see SpecificBuild#upStairs
 * @see SpecificBuild#downStairs
 */
public class SpecificBuild extends Building {
    public static final String dormPath="dorm.txt";
    public static final String libPath="lib.txt";
    public static final String canteenPath="canteen.txt";
    public static final String officepath="office.txt";
    public static final String teachBuildPath="teachBuild.txt";
    private static final int length = 15;
    private static final int maxOfFloor = 5;

    Map[] mapOfFloor = new Map[maxOfFloor];//0是父地图
    Path[] upStairs = new Path[maxOfFloor + 2];//上楼梯数组 i->i+1
    Path[] downStairs = new Path[maxOfFloor+2];//下楼梯数组 i->i-1

    /**
     * @param dot
     * @param map
     * @param assetManager
     * @exception IOException 读取文件错误
     * */
    public SpecificBuild(Dot dot, Map map, AssetManager assetManager) throws IOException {
        super(dot, map);
        //floor=dot.getType().getFloorNum();
        for (int i = floor; i >= 1; i--) {
            String path;
            switch (dot.getType()){
                case dorm:path=dormPath;break;
                case office:path=officepath;break;
                case librariy:path=libPath;break;
                case canteen:path=canteenPath;break;
                case teach:path=teachBuildPath;break;
                default:path=teachBuildPath;
            }
            Graph graph=graphManager.manage(assetManager,path);
            mapOfFloor[i] = new Map(map,floor,graph,assetManager,this);
        }
        mapOfFloor[0] = map;
        for (int i = floor ; i >= 1; i--) {
            if (i != floor ) {
                upStairs[i] = new Path(length, false, getExit(i),getExit(i+1));
            }else  if (i >= 2) {
                downStairs[i] = new Path(length, false,getExit(i),getExit(i-1));
            }
            upStairs[floor]=null;
            downStairs[1]=new Path(1,false,getExit(1),this);
        }

    }

    /**
     * @param Floorindex
     * */
    private Building getExit(int Floorindex){
        return mapOfFloor[Floorindex].getBuilding(mapOfFloor[Floorindex].IndexOfExit());
    }

    /**
     * 建筑物寻找最短路径的算法，按照是否在同一建筑物分类.
     */

    public HashMap<Building, Path> getShortestRoute(Position nowPosition, Building destination,
                                                    String strategy,int floorForDestination) {
//    Route[] a = mapOfFloor[nowPosition.getNowFloor()].getShortestRoute(
//        nowPosition.getNowBuilding(), destination, strategy);

        HashMap<Building, Path> shortestRoute;
        HashMap<Building, Path> estRoute;

        //在同一建筑物中 ，this 就是当前的建筑物
        if (inBuilding(destination.getNameOfBuildingInEnglish())) {
            if (nowPosition.getNowFloor() == floorForDestination) {
                return getShortestRoute(nowPosition.getNowFloor(), floorForDestination, strategy,
                                mapOfFloor[nowPosition.getNowFloor()]);
            } else {
                shortestRoute = getShortestRoute(nowPosition.getNowFloor(), 0, strategy, mapOfFloor[nowPosition
                                .getNowFloor()]);
                if (nowPosition.getNowFloor() < floorForDestination) {
                    for (int i = nowPosition.getNowFloor(); i < floorForDestination; i++) {
                        shortestRoute.put(mapOfFloor[i].getBuilding(0), upStairs[i]);
                    }
                } else {
                    for (int i = floorForDestination; i > nowPosition.getNowFloor(); i--) {
                        shortestRoute.put(mapOfFloor[i].getBuilding(0), downStairs[i]);
                    }
                }

                estRoute = getShortestRoute(0, floorForDestination, strategy,
                        mapOfFloor[floorForDestination]);
                shortestRoute.putAll(estRoute);
            }
            //此处为不在同一建筑物中。
        } else {
            //a 代表当前位置到楼梯口的最短路径，b是目的地到楼梯口，c是上下楼梯
            shortestRoute = getShortestRoute(nowPosition.getNowFloor(), 0, strategy, mapOfFloor[nowPosition
                            .getNowFloor()]);

            for (int i = nowPosition.getNowFloor(); i > 0; i--) {
                shortestRoute.put(mapOfFloor[i].getBuilding(0), downStairs[i]);
            }

//      System.out.println("下楼！下了 "+nowPosition.getNowFloor()+"层");
//      System.out.println("上楼！上了 "+floorForDestination+"层")
            for (int i = 0; i < floorForDestination; i++) {
                shortestRoute.put(mapOfFloor[i].getBuilding(0), upStairs[i]);
            }
            estRoute = getShortestRoute(0, floorForDestination, strategy,
                    mapOfFloor[floorForDestination]);
            shortestRoute.putAll(estRoute);
        }
//    return new Route[0];
        return shortestRoute;//todo
    }


    /**
     * 通过名字是否相同判断是否在同一栋楼.
     */
    private boolean inBuilding(String nameOfBuilding) {
        return this.nameOfBuildingInChinese.equals(nameOfBuilding);
    }
//
//    public static void main(String[] args) throws IOException {
//        int[] a = {1, 2};
//        double[] b = {2.0, 3.0};
//        Exit ex = new Exit();
//        Map[] c = {
//                new Map(),
//                new Map(),
//        };
//        Building test2 = new Building("我是测试", 1, a, b, ex);
//        Building test3 = new Building("我是测试", 1, a, b, ex);
//        Path[] d = {
//                new Path(10, false, test2, test3)
//        };
//        SpecificBuild test1 = new SpecificBuild("I am test", 3, a, b, 4, c, d, ex);
//    }
    /**
     *
     * 实际使用的最短路径
     * @param nowPositionIndex 当前建筑为序列号
     * @param destinationIndex 目标建筑为序列号
     * @param strategy  导航方式
     * @param map  在那张地图上使用
     * @return  Ver的哈希表
     */
    public static   HashMap<Building, Path> getShortestRoute(int nowPositionIndex, int destinationIndex,
                                                     String strategy, Map map) {
        return map.getTheShortestRoute(nowPositionIndex, destinationIndex);

    }

    /**
     * @param index
     * */
    public Map getMapOfFloor (int index) {
        return mapOfFloor[index];
    }
}