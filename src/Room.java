import java.io.File;

public class Room extends Building{
  /**
   * 默认是属于校区，值是School1，or  School2
   * 层数默认是0，在Building类中设定的初始值
   * */
  SpecificBuild belongToBuilding;
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
  Room(String nameOfBuilding, String detailOfBuilding, File imgOfBuilding, int schoolNum,
      int[] guiCorrdinate, double[] mathCoordinate, Exit exitDoor,SpecificBuild belongToBuilding) {
    super(nameOfBuilding, detailOfBuilding, imgOfBuilding, schoolNum, guiCorrdinate, mathCoordinate,
        exitDoor);
    this.belongToBuilding=belongToBuilding;
  }


  public Route[] getShortestRoute(Building destination) {
    //注意：返回值告诉你是否到了终点，这个返回还是要原封不动送还算法策略,由于校区也抽象成楼了，所以不必判断是否在楼内？
    Route[] a= this.belongToBuilding.getShortestRoute(this.toPosition(),destination);//注意：返回值告诉你是否到了终点，这个返回还是要原封不动送还算法策略

    return new Route[0];
  }
  private Position toPosition(){
    return new Position();//todo 需要详细设计
  }
}
