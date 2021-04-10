package com.CampusNavigation.Map;

import com.CampusNavigation.GraphImport.Graph.Dot;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Route;
import java.io.IOException;

public class Room extends Building {
  /**
   * 默认是属于校区，值是School1，or  School2
   * 层数默认是0，在Building类中设定的初始值
   * */
  SpecificBuild belongToBuilding;

  public Room(Dot dot,Map map) throws IOException {
    super(dot,map);
  }

  public Route[] getShortestRoute(Building destination) throws IOException {
    //注意：返回值告诉你是否到了终点，这个返回还是要原封不动送还算法策略,由于校区也抽象成楼了，所以不必判断是否在楼内？
    Route[] a= this.belongToBuilding.getShortestRoute(this.toPosition(),destination, nameOfBuildingInEnglish);//注意：返回值告诉你是否到了终点，这个返回还是要原封不动送还算法策略

    return new Route[0];
  }
  private Position toPosition() throws IOException {
    return new Position();//todo 需要详细设计
  }
}
