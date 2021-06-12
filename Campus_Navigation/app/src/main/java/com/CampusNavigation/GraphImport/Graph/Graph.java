package com.CampusNavigation.GraphImport.Graph;

import com.CampusNavigation.Map.Map;

import java.io.Serializable;

public class Graph implements Serializable {
  public final String filePath;
  public static final int MaxNumOfDots = Map.MaxNumOfDots;
  private final int NumOfDots;
  private Dot[] dots = new Dot[MaxNumOfDots];
  private Edge[][] edges = new Edge[MaxNumOfDots][MaxNumOfDots];


  public Graph(String filePath, int NumOfDots, Dot[] dots, Edge[][] edges) {
    this.filePath = filePath;
    this.dots = dots;
    this.edges = edges;
    this.NumOfDots = NumOfDots;
  }

  public Dot[] getDots() {
    return dots;
  }

  public Edge[][] getEdges() {

    return edges;
  }

  public int NumOfDots() {
    return NumOfDots;
  }

}
