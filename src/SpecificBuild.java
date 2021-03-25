import static java.lang.Math.abs;

import java.io.File;

/**
 * 楼类有层，有图的数组，有道路的数组.
 */
public class SpecificBuild extends Building {

  int numOfFloor;
  Map[] mapOfFloor;
  Path[] paths;

  /**
   * 具体建筑物的构造器方法.
   *
   * @param nameOfBuilding   建筑物的名字
   * @param detailOfBuilding 建筑物的详细信息
   * @param imgOfBuilding    建筑物的图片，目前是一个File类
   * @param schoolNum        1是1号校区 2是2号校区
   * @param guiCorrdinate    gui坐标，目前是int数组，
   * @param mathCoordinate   连接表的坐标，是int数组
   * @param numOfFloor       层数
   * @param mapOfFloor       每个楼层的图构建成的数组
   * @param paths            设计中提及的，暂时还不知道啥意思,猜测是用于楼梯？
   */
  SpecificBuild(String nameOfBuilding, String detailOfBuilding, File imgOfBuilding,
      int schoolNum,
      int[] guiCorrdinate,double[] mathCoordinate, int numOfFloor, Map[] mapOfFloor, Path[] paths,
      Exit exitDoor) {
    super(nameOfBuilding, detailOfBuilding, imgOfBuilding, schoolNum, guiCorrdinate,
        mathCoordinate, exitDoor);
    this.numOfFloor = numOfFloor;
    this.mapOfFloor = mapOfFloor.clone();
    this.paths = paths.clone();

  }

  /**
   * 建筑物寻找最短路径的算法，按照是否在同一建筑物分类.
   */

  public Route[] getShortestRoute(Position nowPosition, Building destination) {
    Route[] a = mapOfFloor[nowPosition.nowFloor].getShortestRoute();
    //todo 需要route完成后再详细的写
    if (!inBuilding(destination.nameOfBuilding)) {
      boolean b = a[0].isToDestination;
      //此处的布尔变量 b 传回算法策略，告诉他我完成了目前的工作，但是还没到达终点。
    } else {
      //a 代表当前位置到楼梯口的最短路径，b是目的地到楼梯口，c是上下楼梯
      int c = abs(nowPosition.nowFloor - destination.floor);
      if(c==0){
        return new Route[0];///同一层
      }
      Route[] b = mapOfFloor[destination.floor].getShortestRoute();

    }
    return new Route[0];
  }


  /**
   * 通过名字是否相同判断是否在同一栋楼.
   */
  private boolean inBuilding(String nameOfBuilding) {
    return this.nameOfBuilding.equals(nameOfBuilding);
  }


}
