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
  public int floor; //默认是0，代表地面上的平房。
  public final String nameOfBuildingInEnglish;
  public final String nameOfBuildingInChinese = "";
  public final BuildingType type;
  public final int index; //在邻接矩阵中的序号
  public final Map map;
  public int floorForDestination;

  /**
   * 通过生成的图构建邻接表.
   *
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
      String strategy, Map map) {
    int nowPositinIndex = map
        .getBuildingsOrder(nowPosition.getNowBuilding().getNameOfBuildingInEnglish());
    int destinationIndex = map.getBuildingsOrder(destination.getNameOfBuildingInEnglish());
    return map.getShortestRoute(nowPositinIndex, destinationIndex);

  }

  /**
   *
   * 实际使用的最短路径
   * @param nowPositionIndex 当前建筑为序列号
   * @param destinationIndex 目标建筑为序列号
   * @param strategy  导航方式
   * @param map  在那张地图上使用
   * @return  Ver的哈希表
   */
  public HashMap<Building, Path> getShortestRoute(int nowPositionIndex, int destinationIndex,
      String strategy, Map map) {
    return map.getShortestRoute(nowPositionIndex, destinationIndex);

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
//todo: 获取出口的方法。
}