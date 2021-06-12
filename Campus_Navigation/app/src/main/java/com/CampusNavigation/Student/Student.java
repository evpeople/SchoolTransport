package com.CampusNavigation.Student;

/**

 * 学生类的基础架子.
 */

import com.CampusNavigation.Gui.StudentView;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Map;
import com.CampusNavigation.Map.Path;
import com.CampusNavigation.Map.TableEntry;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Student {
  public Position position; //todo 凑数的位置类
  private double walkSpeed;
  public Queue<Path> pathsToGo=new LinkedList<>();
  public StudentView view;
  private Building targetBuilding;

  /**
   *
   * @param position   学生位置
   */
  public Student(Position position) {
    this.position = position;
    this.walkSpeed = 60;//初始速度60米每分钟
  }


  public void setSpeed(int newSpeed) {
    this.walkSpeed = newSpeed;
  }

  public void setPosition(Position currentPosition) {
    position = currentPosition;
  }

  public void setTargetBuilding(Building targetBuilding) {
    this.targetBuilding = targetBuilding;
    getShortestRouteToTarget(targetBuilding,"a");
    //if(position.getNowBuilding()==null);
  }
  public void setTargetBuilding(Queue<Building> targetBuilding) {
    getShortestRouteToTarget(targetBuilding.poll(),"a");
    while (!targetBuilding.isEmpty())
    {
     pathsToGo.addAll(getShortestRouteToTarget(targetBuilding.poll(),targetBuilding.poll(),"a"));
    }
      //if(position.getNowBuilding()==null);
  }

  public void setTargetBuilding(int targetBuilding) {
    getShortestRouteToTarget(targetBuilding,"a");
    //if(position.getNowBuilding()==null);
  }

  //1代表最短路径，2代表最短时间，3代表途径最短距离，4代表交通工具最短
  private void getShortestRouteToTarget(Building destination,  String strategy) {
    TableEntry.setStrategy(strategy);
    int nowPositinIndex=position.getCurrentMap().getBuildingsOrder(position.getNowBuilding().getNameOfBuildingInEnglish());
    if (position.getCurrentMap()==destination.map)
    {
      int destinationIndex=destination.index;
      pathsToGo= position.getCurrentMap().getShortestRoute(nowPositinIndex,destinationIndex);
    }
    else
    {
      int busStop=3;//todo: //确认车站的下标Index
      pathsToGo= position.getCurrentMap().getShortestRoute(nowPositinIndex,busStop);
      Queue<Path> pathsToGo2=new LinkedList<>();
      int busBegin=1;//todo: 确认车站下标。
      int destinationIndex=destination.index;
      pathsToGo2= destination.map.getShortestRoute(busBegin,destinationIndex);
      pathsToGo.addAll(pathsToGo2);
    }
  }



  private  Queue<Path>getShortestRouteToTarget(Building destination, Building posBuilding,  String strategy) {
    TableEntry.setStrategy(strategy);
    int nowPositinIndex=posBuilding.index;
    if (posBuilding.map==destination.map)
    {
      int destinationIndex=destination.index;
      return posBuilding.map.getShortestRoute(nowPositinIndex,destinationIndex);
    }
    else
    {
      int busStop=3;//todo: //确认车站的下标Index
      pathsToGo= position.getCurrentMap().getShortestRoute(nowPositinIndex,busStop);
      Queue<Path> pathsToGo2=new LinkedList<>();
      int busBegin=1;//todo: 确认车站下标。
      int destinationIndex=destination.map.getBuildingsOrder(destination.getNameOfBuildingInEnglish());
      pathsToGo2= destination.map.getShortestRoute(busBegin,destinationIndex);
      pathsToGo.addAll(pathsToGo2);
      return posBuilding.map.getShortestRoute(nowPositinIndex,destinationIndex);
    }

  }
  private void getShortestRouteToTarget(int destinationIndex,  String strategy) {
    Building destination=position.getCurrentMap().getBuilding(destinationIndex);
    getShortestRouteToTarget(destination,strategy);
  }
}
