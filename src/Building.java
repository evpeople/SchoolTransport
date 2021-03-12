import java.io.File;
/** 建筑物类*/
public class Building {

  int[] mathCoordinate;
  int[] guiCorrdinate;
  int schoolNum;
  String nameOfBuilding;
  String detailOfBuilding;
  File imgOfBuilding;

  /**
   * @param nameOfBuilding 建筑物的名字
   * @param detailOfBuilding  建筑物的详细信息
   * @param imgOfBuilding 建筑物的图片，目前是一个File类
   * @param schoolNum 1是1号校区 2是2号校区
   * @param guiCorrdinate gui坐标，目前是int数组，
   *                      //todo:gui的具体设计
   * @param mathCoordinate 连接表的坐标，是int数组
   */
  Building(String nameOfBuilding, String detailOfBuilding, File imgOfBuilding, int schoolNum,
      int[] guiCorrdinate, int[] mathCoordinate) {
    this.detailOfBuilding = detailOfBuilding;
    this.guiCorrdinate = guiCorrdinate.clone();
    this.imgOfBuilding = imgOfBuilding;
    this.mathCoordinate = mathCoordinate.clone();
    this.schoolNum = schoolNum;
    this.nameOfBuilding = nameOfBuilding;
  }
  /** 返回详细信息的方法*/
  public String getDetailOfBuilding() {
    return detailOfBuilding;
  }
}