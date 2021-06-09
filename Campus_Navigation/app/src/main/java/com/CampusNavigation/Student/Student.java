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
  //public Route route;
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

  public Student(Map map) {
    this.position = new Position(map);
    this.walkSpeed = 60;//初始速度60米每分钟
    this.pathsToGo = null;
  }

  public void setSpeed(int newSpeed) {
    this.walkSpeed = newSpeed;
  }

  public void setPosition(Position currentPosition) {
    position = currentPosition;
  }



  public void setTargetBuilding(Building targetBuilding) {
    this.targetBuilding = targetBuilding;
    if(position.getNowBuilding()==null);
  }
}
