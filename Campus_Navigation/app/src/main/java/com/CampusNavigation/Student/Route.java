package com.CampusNavigation.Student;

import com.CampusNavigation.Map.*;
import java.util.HashMap;

/**
 * 路径类，不同于道路类.
 */
public class Route {

  private Building start;
  private Building end;
  private HashMap<Building, Path> RouteToDestination = new HashMap();
  public boolean isToDestination = false;

  public Route(Building start, Building end) {
    this.start = start;
    this.end = end;

  }

  public Building getEnd() {
    return end;
  }

  public Building getStart() {
    return start;
  }

  public HashMap<Building, Path> getRouteToDestination() {
    return RouteToDestination;
  }

  public void setRouteToDestination(HashMap<Building, Path> routeToDestination) {
    RouteToDestination = routeToDestination;
  }

  @Override
  public String toString() {
    return "Route{" +
        "end" + start.toString() +
        '}';
  }
}
