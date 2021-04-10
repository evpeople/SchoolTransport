package com.CampusNavigation.Map;

import com.CampusNavigation.GraphImport.Graph.Dot;
import java.io.IOException;

/**
 * 出口类，实际上是建筑物，之所以新写一个类，是为了避免类的成员变量是自己，同时避开静态的限制.
 */
public class Exit extends Building {

  double[] mathCoordinate;
  int[] guiCorrdinate;
  int schoolNum;
  int floor;
  String nameOfExit;

  public Exit(Dot dot, Map map) throws IOException {
    super(dot, map);

  }



}
