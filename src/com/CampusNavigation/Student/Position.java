package com.CampusNavigation.Student;

import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Exit;
import java.io.IOException;

/**
 * 学生当前位置.
 */
public class Position {

  private int nowFloor=0;

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

  public int getNowPositionOnPath() {
    return nowPositionOnPath;
  }

  public void setNowPositionOnPath(int nowPositionOnPath) {
    this.nowPositionOnPath = nowPositionOnPath;
  }

  private Building nowBuilding;
  /**
   * 当前具体层数.
   */
  private int nowPositionOnPath;

  public Position()  {
    int[] a = {1, 2};
    double[] b = {2.0, 3.0};
//    Exit ex= new Exit();

  }
}
