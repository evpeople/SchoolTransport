package com.CampusNavigation.Map;

import com.CampusNavigation.GraphImport.Graph.BuildingType;
import com.CampusNavigation.GraphImport.Graph.Dot;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Route;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * 建筑物类,一个抽象类.
 */
public class Building {

  private Exit exit=null;
  public final double X;
  public final double Y;
  public final int schoolNum;
  public final int floor; //默认是0，代表地面上的平房。
  public final String nameOfBuildingInEnglish;
  public final String nameOfBuildingInChinese="";
  public final BuildingType type;
  public final int index;//在邻接矩阵中的序号
  public final Map map;
  public Building(Dot dot,Map map) throws IOException {
      this.type=dot.getType();
      this.X=dot.X;
      this.Y=dot.Y;
      this.nameOfBuildingInEnglish=dot.getName();
      this.floor=0;
      //this.floor=dot.getType().getFloorNum();
      this.schoolNum=0;
      this.index=dot.getIndex();
      this.map=map;
  }
//  /**
//   * 构造器方法.
//   * @param nameOfBuilding   建筑物的名字
//   * @param schoolNum        1是1号校区 2是2号校区
//   * @param guiCorrdinate    gui坐标，目前是int数组，
//   * @param mathCoordinate   连接表的坐标，是double数组
//   * @param exitDoor         出口
//   */
//
//
//
//  public Building(String nameOfBuilding, int schoolNum,
//                  int[] guiCorrdinate, double[] mathCoordinate, Exit exitDoor) {
//
//    this.X = mathCoordinate[0];
//    this.Y=mathCoordinate[1];
//    this.schoolNum = schoolNum;
//    this.nameOfBuildingInEnglish = nameOfBuilding;
//    this.exit = exitDoor;
//    this.floor=0;
//    this.type=BuildingType.Default;
//  }
//1代表最短路径，2代表最短时间，3代表途径最短距离，4代表交通工具最短
  public Route[] getShortestRoute(Position nowPosition, Building destination,
                                  String strategy) {
    return new Route[0];
  }

//
//  public static void main(String[] args) throws IOException {
//    int []a={1,2};
//    double []b={2.0,3.0};
//    Exit ex= new Exit();
//    Building test= new Building("我是测试",1,a,b,ex);
//  }

}