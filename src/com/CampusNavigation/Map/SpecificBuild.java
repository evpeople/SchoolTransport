package com.CampusNavigation.Map;

import com.CampusNavigation.GraphImport.Graph.Dot;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Route;
import com.CampusNavigation.GraphImport.graphManage.graphManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.abs;
import static java.lang.Math.max;


/**
 * 楼类有层，有图的数组，有道路的数组.
 */
public class SpecificBuild extends Building {

  private static final int length = 10;
  private static final int maxOfFloor = 10;

  Map[] mapOfFloor = new Map[maxOfFloor];

  //  ArrayList<Map> mapOfFloor=new ArrayList<>();
  Path[] upStairs = new Path[maxOfFloor + 1];//楼梯数组
  Path[] downStairs = new Path[maxOfFloor];//楼梯数组

  public SpecificBuild(Dot dot, Map map) throws IOException {
    super(dot, map);
    switch (dot.getType()) {
      case bus:
      case car:
      case crossing:
      case buildingCrossing:
      case runway:

        floor=0;
      case soccer:
      case canteen:
        floor = 0;

        break;
      default:
        floor=0;
        break;
    }
    for (int i = floor-1; i >=0; i--) {
      mapOfFloor[i] = new Map(1);
    }
    for (int i = floor-1; i >=0; i--){
      if (i != floor - 1) {
        upStairs[i] = new Path(length, false, mapOfFloor[i].getBuildings()[0],
            mapOfFloor[i + 1].getBuildings()[0]);
      }
      if (i > 0) {
        downStairs[i] = new Path(length, false, mapOfFloor[i].getBuildings()[0],
            mapOfFloor[i - 1].getBuildings()[0]);
      }
    }
    mapOfFloor = new Map[maxOfFloor];
    mapOfFloor[0] = map;
  }

//    /**
//     * 具体建筑物的构造器方法.
//     *
//     * @param nameOfBuilding 建筑物的名字
//     * @param schoolNum      1是1号校区 2是2号校区
//     * @param guiCorrdinate  gui坐标，目前是int数组，
//     * @param mathCoordinate 连接表的坐标，是int数组
//     * @param numOfFloor     层数
//     * @param mapOfFloor     每个楼层的图构建成的数组
//     * @param paths          设计中提及的，暂时还不知道啥意思,猜测是用于楼梯？
//     */
//
//
//    SpecificBuild(String nameOfBuilding, int schoolNum, int[] guiCorrdinate, double[] mathCoordinate,
//                  int numOfFloor, Map[] mapOfFloor, Path[] paths,
//                  Exit exitDoor) {
//        super(nameOfBuilding, schoolNum, guiCorrdinate,
//                mathCoordinate, exitDoor);
//        this.mapOfFloor = mapOfFloor.clone();
//        this.paths = paths.clone();
//
//    }

  /**
   * 建筑物寻找最短路径的算法，按照是否在同一建筑物分类.
   */

  public HashMap<Building, Path> getShortestRoute(Position nowPosition, Building destination,
      String strategy) {
//    Route[] a = mapOfFloor[nowPosition.getNowFloor()].getShortestRoute(
//        nowPosition.getNowBuilding(), destination, strategy);

    // todo 不同的建筑物。
    HashMap<Building, Path> shortestRoute = new HashMap<>();
    HashMap<Building, Path> estRoute;

    //在同一建筑物中 ，this 就是当前的建筑物
    if (inBuilding(destination.getNameOfBuildingInEnglish())) {
      if (nowPosition.getNowFloor() == destination.floorForDestination) {
        return super
            .getShortestRoute(nowPosition.getNowFloor(), destination.floorForDestination, strategy,
                mapOfFloor[nowPosition.getNowFloor()]);
      } else {
        //todo : 更改 0 ,新增获取exit
        shortestRoute = super
            .getShortestRoute(nowPosition.getNowFloor(), 0, strategy, mapOfFloor[nowPosition
                .getNowFloor()]);
        if (nowPosition.getNowFloor() < destination.floorForDestination) {
          for (int i = nowPosition.getNowFloor(); i < destination.floorForDestination; i++) {
            shortestRoute.put(mapOfFloor[i].getBuildings()[0], upStairs[i]);
          }
        } else {
          for (int i = destination.floorForDestination; i > nowPosition.getNowFloor(); i--) {
            shortestRoute.put(mapOfFloor[i].getBuildings()[0], downStairs[i]);
          }
        }

        estRoute = super.getShortestRoute(0, destination.floorForDestination, strategy,
            mapOfFloor[destination.floorForDestination]);
        shortestRoute.putAll(estRoute);
      }
      //此处为不在同一建筑物中。
    } else {
      //a 代表当前位置到楼梯口的最短路径，b是目的地到楼梯口，c是上下楼梯
      shortestRoute = super
          .getShortestRoute(nowPosition.getNowFloor(), 0, strategy, mapOfFloor[nowPosition
              .getNowFloor()]);

      for (int i = nowPosition.getNowFloor(); i > 0; i--) {
        shortestRoute.put(mapOfFloor[i].getBuildings()[0], downStairs[i]);
      }

//      System.out.println("下楼！下了 "+nowPosition.getNowFloor()+"层");
//      System.out.println("上楼！上了 "+destination.floorForDestination+"层")
      for (int i = 0; i < destination.floorForDestination; i++) {
        shortestRoute.put(mapOfFloor[i].getBuildings()[0], upStairs[i]);
      }
      estRoute = super.getShortestRoute(0, destination.floorForDestination, strategy,
          mapOfFloor[destination.floorForDestination]);
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

}
