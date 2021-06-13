package com.CampusNavigation.Student;

import com.CampusNavigation.Map.*;


import java.io.IOException;

/**
 * @see Position 学生当前位置
 * @see Position#Position(Building) 指定一个建筑物的坐标构造当前位置
 * @see Position#isOnBuilding 学生是否处于建筑物相同位置，若false则代表在路上
 * @see Position#nowBuilding 现在处于相同位置的建筑物
 * @see Position#currentMap 学生处于的地图
 * @see Position#nowFloor 学生处于的楼层
 * @see Position#path 学生处于的道路
 * @see Position#x 学生处于物理位置的X坐标
 * @see Position#y 学生处于的物理位置的Y坐标
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

/**
 * @param building 指定为位置的建筑物
 * */
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
