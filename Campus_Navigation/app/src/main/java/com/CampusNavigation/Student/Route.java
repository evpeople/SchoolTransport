package com.CampusNavigation.Student;

import android.util.Log;

import com.CampusNavigation.Map.*;
import java.util.HashMap;

import static com.shopgun.android.utils.log.LogUtil.TAG;

/**
 * 路径类，不同于道路类.
 */
public class Route {

  private Building start;
  private Building end;
  private HashMap<Building, Path> RouteToDestination = new HashMap();
  public boolean isToDestination = false;

  public Route(Building start, Building end) {
    Log.e(TAG, "Route: "+start.toString() );
    this.start = start;
    this.end = end;

  }
  public Route(Building start, Building end,HashMap<Building,Path> routeToDestination) {
    this.start = start;
    this.end = end;
    this.RouteToDestination=routeToDestination;
  }
//todo: baseketball spExit
  public Building getEnd() {
    return end;
  }

  public Building getStart() {
    return start;
  }

  public Building next(Building now){
    if(!RouteToDestination.containsKey(now))return null;
    return RouteToDestination.get(now).getEnd();
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
