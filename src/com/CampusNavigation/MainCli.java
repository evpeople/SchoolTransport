package com.CampusNavigation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import com.CampusNavigation.Map.*;
import com.CampusNavigation.Student.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MainCli {

  static Map map;
  static final Logger logger = LoggerFactory.getLogger(MainCli.class);


  public static void main(String[] args) throws IOException {
    map = new Map(null);
    Path[] temp = map.getPaths()[1];
    //map.dijkstra();
    Student JackYang = new Student();
    JackYang.setCurrentMap(map);
    System.out.println("欢迎来到导航系统1.0版\n你打算干什么呢\n1.导航\n2.查询目前的位置");
    Scanner scanner = new Scanner(System.in);
    String answer = scanner.next();
    switch (answer) {
      case "1":
        navigation(JackYang);
        break;
      case "2":
        break;
    }


  }

  private static void navigation(Student JackYang)  {

    System.out.println("请输入起点，终点,起点输入0，则以你当前的位置为起点哦");
    HashMap<String, String> UsrData = new HashMap<>();
    String[] a = new String[3];
    int i = 0;
    Scanner scanner = new Scanner(System.in);
    while (i != 2) {
      a[i] = scanner.next();
      if (i==1)
      {
        if(toBuilding(a[i], JackYang.getCurrentMap()).floor!=0)
        {
          System.out.println("这栋楼不止一层哦，请输入你要去第几层");
          a[2]=scanner.next();
          toBuilding(a[i], JackYang.getCurrentMap()).floorForDestination= Integer.parseInt(a[2]);
        }
      }
      logger.debug(a[i]);
      i++;
    }
    UsrData.put("start", a[0]);
    UsrData.put("destination", a[1]);
    System.out.println("请输入导航策略，1代表最短路径，2代表最短时间，3代表途径最短距离，4代表交通工具最短");

    UsrData.put("strategy", scanner.next());
    logger.debug(UsrData.get("strategy"));


    if (UsrData.get("destination").equals("0")) {
      HashMap<Building, Path> myPath = JackYang.position.getNowBuilding()
          .getShortestRoute(JackYang.position, toBuilding(UsrData.get("destination"),
              JackYang.getCurrentMap()),
              UsrData.get("strategy"), JackYang.getCurrentMap());
    } else {
      HashMap<Building, Path> myPath = toPosition(UsrData.get("start"), JackYang.getCurrentMap()).getNowBuilding()
          .getShortestRoute(toPosition(UsrData.get("start"), JackYang.getCurrentMap()),
              toBuilding(UsrData.get("destination"), JackYang.getCurrentMap()), UsrData.get("strategy"),
              JackYang.getCurrentMap());
    }
  }

  private static void walk(Route[] routes) {

  }

  private static void Inquire(Student JackYang) {

    String[] answer = JackYang.getCurrentMap().Inquire(JackYang.position);
    //输出完字符串后要给用户一个机会选择干啥，就是这些最短距离。
  }

  private static Building toBuilding(String name,Map map) {
    int a = map.getBuildingsOrder(name);
    return map.getBuildings()[a];
//    Exit ex= new Exit();
    //Building test= new Building("我是测试",1,a,b,ex);
  }

  private static Position toPosition(String name ,Map map) {
    Position temp= new Position();
    temp.setNowBuilding(map.getBuildings()[map.getBuildingsOrder(name)]);
    return  temp;
  }
}
