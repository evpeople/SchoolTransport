import java.io.File;

/**
 * 建筑物类,一个抽象类.
 */
public class Building {

  Exit exit;
  int[] mathCoordinate;
  int[] guiCorrdinate; //todo gui的具体设计
  int schoolNum;
  int floor; //默认是0，代表地面上的平房。
  String nameOfBuilding;
  private String detailOfBuilding;
  File imgOfBuilding;

  /**
   * 构造器方法.
   *
   * @param nameOfBuilding   建筑物的名字
   * @param detailOfBuilding 建筑物的详细信息
   * @param imgOfBuilding    建筑物的图片，目前是一个File类
   * @param schoolNum        1是1号校区 2是2号校区
   * @param guiCorrdinate    gui坐标，目前是int数组，
   * @param mathCoordinate   连接表的坐标，是int数组
   * @param exitDoor         出口
   */
  Building(String nameOfBuilding, String detailOfBuilding, File imgOfBuilding, int schoolNum,
      int[] guiCorrdinate, int[] mathCoordinate, Exit exitDoor) {
    this.detailOfBuilding = detailOfBuilding;
    this.guiCorrdinate = guiCorrdinate.clone();
    this.imgOfBuilding = imgOfBuilding;
    this.mathCoordinate = mathCoordinate.clone();
    this.schoolNum = schoolNum;
    this.nameOfBuilding = nameOfBuilding;
    this.exit = exitDoor;
  }

  /**
   * 返回详细信息的方法.
   */
  public String getDetailOfBuilding() {
    return detailOfBuilding;
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
}