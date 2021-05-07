package com.CampusNavigation.Student;

/**

 * 学生类的基础架子.
 */

import com.CampusNavigation.Gui.StudentView;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Map;
import com.CampusNavigation.Map.Path;
import java.io.IOException;
import java.util.HashMap;

public class Student {
  public Route route;
  public Position position; //todo 凑数的位置类
  private double walkSpeed;
  private Map currentMap;
 Path[] pathsToGo=null;
  int numberOfPath;
  public StudentView view;

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
if(pathsToGo!=null)this.pathsToGo = pathsToGo.clone();
    this.route=currentMap.toGetShortestRoute(0,12);
//    HashMap<Building,Path> way=currentMap.getShortestRoute(0,3);
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

  public void bindView(StudentView view){ this.view=view;view.bindStudent(this);}

  public void move() throws InterruptedException {//fei zhong tu chong qi zou dong
    for(Building now=route.getStart();now!=route.getEnd();){
      now=route.next(now);
      view.addTarget((int )now.mathX,(int)now.mathY);
    }
    view.startMove();
  }
  public void setRoute(Route route){
    this.route=route;
    //for()
  }
  public void stop(){

  }

}
