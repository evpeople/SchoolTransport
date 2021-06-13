package com.CampusNavigation.Student;

import com.CampusNavigation.Map.*;


import java.io.IOException;

/**
 * 学生当前位置.
 */
public class Position implements Cloneable {
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
    this.currentMap=nowBuilding.map;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }


  public Position(Building building) {
    this.currentMap=building.map;
    this.nowBuilding=building;
    this.x=nowBuilding.mathX;
    this.y=nowBuilding.mathY;
    this.path=null;
    this.nowFloor=building.floor;
    this.isOnBuilding=true;
  }

  public void setOnBuilding(boolean onBuilding) {
    isOnBuilding = onBuilding;
  }

  public boolean isOnBuilding() {
    return isOnBuilding;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
