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
    super(dot, map);
    switch (dot.getType()) {
      case bus:
      case car:
        break;
      case crossing:
      case buildingCrossing:
      case runway:
      case soccer:
        break;
      default:
        break;
    }

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

  public Route[] getShortestRoute(Position nowPosition, Building destination,
      String strategy) {
    Route[] a = mapOfFloor[nowPosition.getNowFloor()].getShortestRoute(
        nowPosition.getNowBuilding(), destination, strategy);
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
      Route[] b = mapOfFloor[destination.floor]
          .getShortestRoute(nowPosition.getNowBuilding(), destination, strategy);

    }
    return new Route[0];
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
