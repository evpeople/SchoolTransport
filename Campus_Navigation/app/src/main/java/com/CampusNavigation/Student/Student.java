package com.CampusNavigation.Student;

/**

 * 学生类的基础架子.
 */

import com.CampusNavigation.Gui.StudentView;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Map;
import com.CampusNavigation.Map.Path;
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
    getShortestRouteToTarget(targetBuilding,"c");
    //if(position.getNowBuilding()==null);
  }

  //1代表最短路径，2代表最短时间，3代表途径最短距离，4代表交通工具最短
  private void getShortestRouteToTarget(Building destination,  String strategy) {
    int nowPositinIndex=position.getCurrentMap().getBuildingsOrder(position.getNowBuilding().getNameOfBuildingInEnglish());
    int destinationIndex=position.getCurrentMap().getBuildingsOrder(destination.getNameOfBuildingInEnglish());
    pathsToGo= position.getCurrentMap().getShortestRoute(nowPositinIndex,destinationIndex);

  }
}