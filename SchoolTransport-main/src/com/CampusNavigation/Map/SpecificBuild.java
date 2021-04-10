package com.CampusNavigation.Map;

import com.CampusNavigation.GraphImport.Graph.Dot;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Route;

import java.io.IOException;

import static java.lang.Math.abs;


/**
 * 楼类有层，有图的数组，有道路的数组.
 */
public class SpecificBuild extends Building {

    Map[] mapOfFloor;
    Path[] paths;


    public SpecificBuild(Dot dot, Map map) throws IOException {
        super(dot,map);
        switch (dot.getType()){
            case bus:case car:  break;
            case crossing:case buildingCrossing:case runway:case soccer:break;
            default:break;
        }

    }


    /**
     * 建筑物寻找最短路径的算法，按照是否在同一建筑物分类.
     */

    public Route[] getShortestRoute(Position nowPosition, Building destination,
                                    String strategy) {
        Route[] a = mapOfFloor[nowPosition.getNowFloor()].getShortestRoute(
            nowPosition.getNowBuilding(), destination,strategy);
        //todo 需要route完成后再详细的写
        if (!inBuilding(destination.nameOfBuildingInChinese)) {
            boolean b = a[0].isToDestination;
            //此处的布尔变量 b 传回算法策略，告诉他我完成了目前的工作，但是还没到达终点。
        } else {
            //a 代表当前位置到楼梯口的最短路径，b是目的地到楼梯口，c是上下楼梯
            int c = abs(nowPosition.getNowFloor() - destination.floor);
            if (c == 0) {
                return new Route[0];///同一层
            }
            Route[] b = mapOfFloor[destination.floor].getShortestRoute(nowPosition.getNowBuilding(),destination,strategy);

        }
        return new Route[0];
    }


    /**
     * 通过名字是否相同判断是否在同一栋楼.
     */
    private boolean inBuilding(String nameOfBuilding) {
        return this.nameOfBuildingInChinese.equals(nameOfBuilding);
    }
}
