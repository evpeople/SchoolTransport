package com.CampusNavigation.Student;

/**
 * 学生类的基础架子.
 */

import com.CampusNavigation.Gui.StudentView;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Map;
import com.CampusNavigation.Map.Path;
import com.CampusNavigation.Map.TableEntry;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public class Student {

    private Position position; //todo 凑数的位置类
    private double walkSpeed;
    public Queue<Path> pathsToGo = new LinkedList<>();
    public StudentView view;
    private Building targetBuilding;
    private boolean goByBike;

    /**
     *
     * @param position   学生位置
     */
    public Student(Position position) {
        this.position = position;
        this.walkSpeed = 60;//初始速度60米每分钟
    }

    //唯一供前端调用接口
    public void setTargetBuilding(Building targetBuilding,String strategy) {
        this.targetBuilding = targetBuilding;
        switch (strategy){
            case "c": setTargetBuilding(null); break;
            case "a": case "b": case "d":
                getShortestRouteToTarget(targetBuilding,strategy);break;
            default:  break;
        }
        if(strategy=="d")goByBike=true;
    }

    public void setTargetBuilding(Queue<Building> targetBuilding) {
        TableEntry.setStrategy("a");
        getShortestRouteToTarget(targetBuilding.poll(), "a");
        while (!targetBuilding.isEmpty()) {
            pathsToGo.addAll(getShortestRouteToTarget(targetBuilding.poll(), targetBuilding.poll(), "a"));
        }
        //if(position.getNowBuilding()==null);
    }
    public double getTargetBuildingCost(Queue<Building> targetBuilding) {
        TableEntry.setStrategy("a");
        double cost=0;
        getShortestRouteToTarget(targetBuilding.poll(), "a");
        cost=TableEntry.totalCost;
        while (!targetBuilding.isEmpty()) {
            pathsToGo.addAll(getShortestRouteToTarget(targetBuilding.poll(), targetBuilding.poll(), "a"));
            cost+=TableEntry.totalCost;
        }
        return cost;
        //if(position.getNowBuilding()==null);
    }
    /* 得到各种形式的花费*/
    public double getTargetBuildingCost(Building destination, String strategy, String carType) {
        TableEntry.setStrategy(strategy);
        double totalTime = 0;
        int nowPositinIndex = position.getCurrentMap().getBuildingsOrder(position.getNowBuilding().getNameOfBuildingInEnglish());
        if (position.getCurrentMap() == destination.map) {
            int destinationIndex = destination.index;
            pathsToGo = position.getCurrentMap().getShortestRoute(nowPositinIndex, destinationIndex);
            totalTime = TableEntry.totalCost;
        } else {
            int busStop = position.getCurrentMap().IndexOfBus();//todo: //确认车站的下标Index
            pathsToGo = position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop);
            totalTime = TableEntry.totalCost;

            if (strategy == "b" || strategy == "d") {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                if (carType == "bus") {
                    totalTime += ((0 - minute) % 30);
                } else {
                    double temp=0;
                    if (minute==0&&(hour==8||hour==12||hour==18)) {

                    } else if (hour < 8) {
                        temp = 480 - (hour * 60 + minute);
                    } else if (hour >= 8&&hour<12)
                    {
                        temp=720-(hour*60+minute);
                    }
                    else if (hour>12&&hour<18)
                    {
                       temp=1080-(hour*60+minute);
                    }
                    else
                    {
                        temp=1440-(minute+hour*60)+480;
                    }
                    totalTime += temp;
                }
            }

            Queue<Path> pathsToGo2 = new LinkedList<>();
            int busBegin = destination.map.IndexOfBus();//todo: 确认车站下标。
            int destinationIndex = destination.index;
            pathsToGo2 = destination.map.getShortestRoute(busBegin, destinationIndex);
            pathsToGo.addAll(pathsToGo2);

            totalTime += TableEntry.totalCost;
        }
        return totalTime;
    }


    //1代表最短路径，2代表最短时间，3代表途径最短距离，4代表交通工具最短
    private void getShortestRouteToTarget(Building destination, String strategy) {
        TableEntry.setStrategy(strategy);
        int nowPositinIndex = position.getCurrentMap().getBuildingsOrder(position.getNowBuilding().getNameOfBuildingInEnglish());
        if (position.getCurrentMap() == destination.map) {
            int destinationIndex = destination.index;
            pathsToGo = position.getCurrentMap().getShortestRoute(nowPositinIndex, destinationIndex);
        } else {
            int busStop =position.getCurrentMap().IndexOfBus();// destination.map.IndexOfBus();//todo: //确认车站的下标Index
            pathsToGo = position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop);
            Queue<Path> pathsToGo2 = new LinkedList<>();
            int busBegin = destination.map.IndexOfBus();//todo: 确认车站下标。
            int destinationIndex = destination.index;
            pathsToGo2 = destination.map.getShortestRoute(busBegin, destinationIndex);
            pathsToGo.addAll(pathsToGo2);
        }
    }


    private Queue<Path> getShortestRouteToTarget(Building destination, Building posBuilding, String strategy) {
        TableEntry.setStrategy(strategy);
        int nowPositinIndex = posBuilding.index;
        if (destination==posBuilding) {
            return new LinkedList<>();
        }
        if (posBuilding.map == destination.map) {
            int destinationIndex = destination.index;
            return posBuilding.map.getShortestRoute(nowPositinIndex, destinationIndex);
        } else {
            int busStop = posBuilding.map.IndexOfBus();//todo: //确认车站的下标Index
            pathsToGo = position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop);
            Queue<Path> pathsToGo2 = new LinkedList<>();
            int busBegin = destination.map.IndexOfBus();//todo: 确认车站下标。
            int destinationIndex = destination.map.getBuildingsOrder(destination.getNameOfBuildingInEnglish());
            pathsToGo2 = destination.map.getShortestRoute(busBegin, destinationIndex);
            pathsToGo.addAll(pathsToGo2);
            return posBuilding.map.getShortestRoute(nowPositinIndex, destinationIndex);
        }

    }

    private void getShortestRouteToTarget(int destinationIndex, String strategy) {
        Building destination = position.getCurrentMap().getBuilding(destinationIndex);
        getShortestRouteToTarget(destination, strategy);
    }

    public boolean isGoByBike() {
        return goByBike;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
