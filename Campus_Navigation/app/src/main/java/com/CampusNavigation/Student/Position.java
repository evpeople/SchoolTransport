package com.CampusNavigation.Student;

import com.CampusNavigation.Map.*;


import java.io.IOException;

/**
 * 学生当前位置.
 */
public class Position {
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


  public Position(Map map) {
    x=32;
    y=32;
    this.currentMap=map;
  }
}
