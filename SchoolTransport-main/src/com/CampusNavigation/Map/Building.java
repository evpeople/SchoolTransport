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
      this.nameOfBuildingInEnglish=dot.getPosition();
      this.floor=0;
      //this.floor=dot.getType().getFloorNum();
      this.schoolNum=0;
      this.index=dot.getIndex();
      this.map=map;
  }
  public Route[] getShortestRoute(Position nowPosition, Building destination,
                                  String strategy) {
    return new Route[0];
  }


}