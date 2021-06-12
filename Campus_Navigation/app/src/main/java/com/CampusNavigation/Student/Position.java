package com.CampusNavigation.Student;

import com.CampusNavigation.Map.*;


import java.io.IOException;

/**
 * 学生当前位置.
 */
public class Position {
  boolean isOnBuilding;
  private Building nowBuilding;
  private Map currentMap;
  private int nowFloor;
  private Path path;
  private double x;
  private double y;

  public double getY() {
    return y;
  }

  public double getX() {
    return x;
  }

  public Map getCurrentMap() {
    return currentMap;
  }

  public void setCurrentMap(Map currentMap) {
    this.currentMap = currentMap;
  }

  public Path getPath() {
    return path;
  }

  public void setPath(Path path) {
    this.path = path;
  }

  public int getNowFloor() {
    return nowFloor;
  }

  public void setNowFloor(int nowFloor) {
    this.nowFloor = nowFloor;
  }

  public Building getNowBuilding() {
    return nowBuilding;
  }

  public void setNowBuilding(Building nowBuilding) {
    this.nowBuilding = nowBuilding;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  //
//  public Position(Map map) {
//    this.currentMap=map;
//    this.nowBuilding=map.getBuilding(0);
//    this.x=nowBuilding.mathX;
//    this.y=nowBuilding.mathY;
//    this.path=null;
//    this.nowFloor=0;
//  }
  public Position(Building building) {
    this.currentMap=building.map;
    this.nowBuilding=building;
    this.x=nowBuilding.mathX;
    this.y=nowBuilding.mathY;
    this.path=null;
    this.nowFloor=building.floor;
  }

  public void setOnBuilding(boolean onBuilding) {
    isOnBuilding = onBuilding;
  }

  public boolean isOnBuilding() {
    return isOnBuilding;
  }
}
