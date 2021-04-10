package com.CampusNavigation.Map;

import com.CampusNavigation.GraphImport.Graph.Dot;
import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.GraphImport.graphManage.graphManager;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Route;
import java.io.IOException;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 地图类.
 */
public class Map {

  static final Logger logger = LoggerFactory.getLogger(Map.class);

  public static final int MaxNumOfDots = 80;
  private final int numOfBuildings;
  private Building[] buildings = new Building[MaxNumOfDots];
  private Path[][] paths = new Path[MaxNumOfDots][MaxNumOfDots];
  //todo:这个是Ver的，他写注释
  /**
   * 构建邻接表用于DJ算法.

   * @param path 地图文件所在路径
   * @throws IOException 读取文件出错
   */
  public Map(String path) throws IOException {
    Graph graph = graphManager.manage(null, path);
    this.numOfBuildings = graph.NumOfDots();

    int now = 0;
    for (Dot dot : graph.getDots()) {
      if (dot == null) {
        break;
      }
      switch (dot.getType()) {
        case exit:
          buildings[now] = new Exit(dot, this);
          break;
        default:
          buildings[now] = new SpecificBuild(dot, this);
          break;
      }
      now++;


    }
    logger.debug(String.valueOf(this.numOfBuildings));
    for (int i = 0; i < numOfBuildings; i++) {
      for (int j = 0; j < numOfBuildings; j++) {
        if (graph.getEdges()[i][j] == null) {
          paths[i][j] = null;
        } else {
          paths[i][j] = new Path(graph.getEdges()[i][j], buildings);
        }
      }
    }

  }


  public Route[] getShortestRoute(Building start, Building end, String strategy) {

    return new Route[0];
  }

  public String[] Inquire(Position nowPosition) {
    return new String[0];
    //返回的字符串要有距离，详细信息，
  }

  public Path[][] getPaths() {
    return paths;
  }

  public Building[] getBuildings() {
    return buildings;
  }

  private void initTable(Map graph, TableEntry[] t) {
    for (int i = 0; i < graph.numOfBuildings; i++) {
      t[i] = new TableEntry(i, graph.buildings[i], false, Double.POSITIVE_INFINITY, null);
    }
    t[0].setDist(0);


  }

  /**
   * 最短路径所用的DJ算法.
   * //todo 不同的导航策略
   * //todo 对最后结果表格的解读（递归求出路径）
   */
  public void dijkstra() {
    TableEntry[] tableEntries = new TableEntry[this.numOfBuildings + 1];
    initTable(this, tableEntries);
    int i = 0;
    int t = 0;
    while (true) {
      tableEntries[i].setKnown(true);
      i = UpdateTableEntry(tableEntries, tableEntries[i]);
      if (i == -1) {
        break;
      }
      for (int ie = 0; ie < this.numOfBuildings; ie++) {
        logger.debug(tableEntries[ie].toString());
      }
    }

  }

  private int UpdateTableEntry(TableEntry[] tableEntries, TableEntry known) {
    Path[] temp = this.paths[known.getNumOfBuilding()];
    double dv = Double.POSITIVE_INFINITY;
    int minRoute = -1;
    for (int i = 0; i < this.numOfBuildings; i++) {
      if (tableEntries[i].isNotKnown() && temp[i] != null) {
        logger.debug("当前点 {} 能到达的一个点是 {} ",
            this.buildings[known.getNumOfBuilding()].nameOfBuildingInEnglish,
            this.buildings[tableEntries[i].getNumOfBuilding()].nameOfBuildingInEnglish);
        logger.debug("本点更改前的距离是 {}", tableEntries[i].getDist());
        double oldDist = tableEntries[i].getDist();
        tableEntries[i].setDist(temp[i].getLength() + known.getDist());
        double newDist = tableEntries[i].getDist();
        if (oldDist == newDist) {
          continue;
        }

        logger.debug("本点更改后的距离是 {}", tableEntries[i].getDist());

        Route tempP = new Route(this.buildings[known.getNumOfBuilding()],
            this.buildings[i]);
        logger.debug(tempP.toString());

        HashMap<Building, Path> routeToDestination = new HashMap<>();
        routeToDestination.put(this.buildings[known.getNumOfBuilding()], temp[i]);
        tempP.setRouteToDestination(routeToDestination);
        tableEntries[i].setPathToBuilding(tempP);
      }
    }
    for (int i = 0; i < this.numOfBuildings; i++) {
      if ((tableEntries[i].isNotKnown()) && tableEntries[i].getDist() < dv) {
        minRoute = i;
        dv = tableEntries[i].getDist();
      }

    }
    if (minRoute != -1) {
      logger.debug("本次选取的点是" + this.buildings[minRoute].nameOfBuildingInEnglish);
    }
    return minRoute;
  }
}

class TableEntry {

  private int numOfBuilding;
  private Building header;
  private boolean known;
  private double dist;
  Route pathToBuilding;

  public TableEntry(int numOfBuilding, Building header, boolean known, double dist,
      Route pathToBuilding) {
    this.numOfBuilding = numOfBuilding;
    this.header = header;
    this.known = known;
    this.dist = dist;
    this.pathToBuilding = pathToBuilding;
  }

  public void setDist(double dist) {
    if (dist < this.dist) {
      this.dist = dist;
    }
  }

  public void setHeader(Building header) {
    this.header = header;
  }

  public void setKnown(boolean known) {
    this.known = known;
  }

  public void setPathToBuilding(Route pathToBuilding) {
    this.pathToBuilding = pathToBuilding;
  }

  public int getNumOfBuilding() {
    return numOfBuilding;
  }

  public boolean isNotKnown() {
    return !known;
  }

  public double getDist() {
    return dist;
  }

  public Route getPathToBuilding() {
    return pathToBuilding;
  }

  @Override
  public String toString() {
    return "TableEntry{"
        +
        "numOfBuilding=" + numOfBuilding
        +
        ", Header=" + header.toString()
        +
        ", Known=" + known
        +
        ", Dist=" + dist
        +
        ", PathToBuilding=" + pathToBuilding
        +
        '}';
  }
}