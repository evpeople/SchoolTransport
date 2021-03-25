import java.io.File;


/**
 * 建筑物类,一个抽象类.
 */
public class Building {

  Exit exit;
  double[] mathCoordinate;
  int[] guiCorrdinate; //todo gui的具体设计
  int schoolNum;
  int floor; //默认是0，代表地面上的平房。
  String nameOfBuilding;


  /**
   * 构造器方法.
   * @param nameOfBuilding   建筑物的名字
   * @param schoolNum        1是1号校区 2是2号校区
   * @param guiCorrdinate    gui坐标，目前是int数组，
   * @param mathCoordinate   连接表的坐标，是double数组
   * @param exitDoor         出口
   */
  Building(String nameOfBuilding, int schoolNum,
      int[] guiCorrdinate, double[] mathCoordinate, Exit exitDoor) {
    this.guiCorrdinate = guiCorrdinate.clone();
    this.mathCoordinate = mathCoordinate.clone();
    this.schoolNum = schoolNum;
    this.nameOfBuilding = nameOfBuilding;
    this.exit = exitDoor;
  }

  public Route[] getShortestRoute() {
    return new Route[0];
  }

  /**
   * 设定具体目的地的楼层的方法.
   */
  public void setFloor(int floor) {
    this.floor = floor;
  }

  public static void main(String[] args)
  {
    int []a={1,2};
    double []b={2.0,3.0};
    Exit ex=ex();
    Building test=Building("我是测试楼",1,a,b,ex);
  }

}