package com.CampusNavigation.Map;

import com.CampusNavigation.GraphImport.Graph.BuildingType;
import com.CampusNavigation.GraphImport.Graph.Dot;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Route;
import java.util.HashMap;
import java.util.Queue;

/**
 * @see Building 建筑物类,起抽象类作用
 * @see Building#Building(Dot, Map) 通过生成的图构建邻接表，构造建筑物对象，其类为建筑楼与房间共同的父类
 * @see Building#getNameOfBuildingInEnglish() 获取当前建筑物的英文名
 * @see Building#mathX 实际物理地址X坐标
 * @see Building#mathY 实际物理地址Y坐标
 * @see Building#floor 建筑物内部楼层数，默认为0
 * @see Building#nameOfBuildingInEnglish 建筑物的英文名
 * @see Building#type 枚举，指明建筑物的类型
 * @see Building#index 建筑物在邻接矩阵中的序号
 */
public class Building {

  public final double mathX;
  public final double mathY;
  public final int schoolNum=0;
  public int floor;
  public final String nameOfBuildingInEnglish;
  public final String nameOfBuildingInChinese = "";
  public final BuildingType type;
  public final int index;
  public final Map map;

  /**
   * @param dot 地图传入的顶点，为建筑物的前身
   * @param map 传入的地图
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

  /**
   * @return 该建筑物对象英文名
   * */
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