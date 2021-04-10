package com.CampusNavigation.Student;

import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Path;
import java.util.HashMap;

/**
 * 路径类，不同于道路类.
 */
public class Route {
  private Building start;
  private Building end;
  private HashMap<Building, Path> RouteToDestination=new HashMap();
  public boolean isToDestination = false;

  public  Route(Building start,Building end){

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

  public void setRouteToDestination(
      HashMap<Building, Path> routeToDestination) {
    RouteToDestination = routeToDestination;
  }
}
