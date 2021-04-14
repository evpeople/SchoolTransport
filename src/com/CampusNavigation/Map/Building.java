package com.CampusNavigation.Map;

import com.CampusNavigation.GraphImport.Graph.BuildingType;
import com.CampusNavigation.GraphImport.Graph.Dot;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Route;
import java.util.HashMap;

/**
 * 建筑物类,一个抽象类.
 */
public class Building {

  private Exit exit = null;
  public final double mathX;
  public final double mathY;
  public final int schoolNum;
  public final int floor; //默认是0，代表地面上的平房。
  public final String nameOfBuildingInEnglish;
  public final String nameOfBuildingInChinese = "";
  public final BuildingType type;
  public final int index; //在邻接矩阵中的序号
  public final Map map;

  /**
   *  通过生成的图构建邻接表.

   * @param dot 点
   * @param map 地图
   */
  public Building(Dot dot, Map map) {
    this.type = dot.getType();
    this.mathX = dot.X;
    this.mathY = dot.Y;
    this.nameOfBuildingInEnglish = dot.getPosition();
    this.floor = 0;
    //this.floor=dot.getType().getFloorNum();
    this.schoolNum = 0;
    this.index = dot.getIndex();
    this.map = map;
  }



  //1代表最短路径，2代表最短时间，3代表途径最短距离，4代表交通工具最短
  public HashMap<Building, Path> getShortestRoute(Position nowPosition, Building destination,
      String strategy) {
    int nowPositinIndex=map.getBuildingsOrder(nowPosition.getNowBuilding().getNameOfBuildingInEnglish());
    int destinationIndex=map.getBuildingsOrder(destination.getNameOfBuildingInEnglish());
    return map.getShortestRoute(nowPositinIndex,destinationIndex);

  }

  public String getNameOfBuildingInEnglish() {
    return nameOfBuildingInEnglish;
  }

  @Override
  public String toString() {
    return "Building{"
        +
        "name="
        + nameOfBuildingInEnglish
        +
        '}';
  }

}