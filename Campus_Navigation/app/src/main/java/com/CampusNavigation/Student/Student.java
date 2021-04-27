package com.CampusNavigation.Student;

/**

 * 学生类的基础架子.
 */

import com.CampusNavigation.Gui.StudentView;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Map;
import com.CampusNavigation.Map.Path;
import java.io.IOException;

public class Student {
  public Route route;
  public Position position; //todo 凑数的位置类
  private double walkSpeed;
  private Map currentMap;
  Path[] pathsToGo;
  int numberOfPath;
  StudentView view;

  /**
   *
   * @param position   学生位置
   * @param currentMap 所处地图
   * @param pathsToGo  即将走过的路径
   */
  public Student(Position position, Map currentMap, Path[] pathsToGo) {
    this.position = position;
    this.walkSpeed = 60;//初始速度60米每分钟
    this.currentMap = currentMap;
    this.pathsToGo = pathsToGo.clone();
  }

  public Student() {
    this.position = new Position();
    this.walkSpeed = 60;//初始速度60米每分钟
    this.currentMap = null;
    this.pathsToGo = null;
  }

  public void setCurrentMap(Map currentMap) {
    this.currentMap = currentMap;
  }

  public Map getCurrentMap() {
    return currentMap;
  }

  public void changeSpeed(int newSpeed) {
    this.walkSpeed = newSpeed;
  }

  public void setNumberOfPath(int numberOfPath) {
    this.numberOfPath = numberOfPath;
  }

  public void rePosition(Position currentPosition) {
    position = currentPosition;
  }

  public void bindView(StudentView view){ this.view=view;}

  public void move(){
    Building nowTarget=route.getStart();
    for(Building now=route.next(nowTarget);now!=route.getEnd();now=route.next(now)){
      view.moveTo((int)now.mathX,(int)now.mathY);
      //position bianhua
    }

  }
  public void setRoute(Route route){
    this.route=route;
  }
  public void stop(){

  }

}
