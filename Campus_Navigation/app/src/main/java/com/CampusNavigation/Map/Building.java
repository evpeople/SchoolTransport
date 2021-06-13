package com.CampusNavigation.Map;

import com.CampusNavigation.GraphImport.Graph.BuildingType;
import com.CampusNavigation.GraphImport.Graph.Dot;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Route;
import java.util.HashMap;
import java.util.Queue;

/**
 * 建筑物类,一个抽象类.
 */
public class Building {

  public final double mathX;
  public final double mathY;
  public final int schoolNum=0;
  public int floor; //默认是0，代表地面上的平房。
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
    this.floor = dot.getType().getFloorNum();
    this.index = dot.getIndex();
    this.map = map;
   // this.schoolNum = 0;
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