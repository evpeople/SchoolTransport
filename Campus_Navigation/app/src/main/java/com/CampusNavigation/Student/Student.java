package com.CampusNavigation.Student;

/**
 * 学生类的基础架子.
 */

import android.util.Log;
import android.util.Pair;

import com.CampusNavigation.Gui.StudentView;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Map;
import com.CampusNavigation.Map.Path;
import com.CampusNavigation.Map.Room;
import com.CampusNavigation.Map.TableEntry;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import static com.shopgun.android.utils.Tag.TAG;

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
    public void setTargetBuilding(Building targetBuilding,String strategy,boolean ByBus) {
        this.targetBuilding = targetBuilding;
        //todo:busType 的输入
        if (!position.isOnBuilding())
        {
            if(ByBus)dealStopInPath(targetBuilding,strategy,"bus");
            else dealStopInPath(targetBuilding,strategy,"bus");
        }
        switch (strategy){
            case "c": setTargetBuilding(null); break;
            case "a": case "b": case "d":
                getShortestRouteToTarget(targetBuilding,strategy);break;
            default:  break;
        }
        if(strategy.equals("d"))goByBike=true;
    }
    //唯二共前端调用接口
    public String getCostToTarget(Building targetBuilding,String strategy,boolean ByBus) throws CloneNotSupportedException {
        //todo:busType 的输入
        double ans=0;
        String carType=ByBus?"bus":"car";
        dealStopInPath(targetBuilding,strategy,carType);
        switch (strategy){
            case "c": new Student((Position) position.clone()).getTargetBuildingCost(null); break;
            case "a": case "b": case "d":
                 ans=getTargetBuildingCost((Position) position.clone(),targetBuilding,strategy,carType);break;
            default:  break;
        }
        if(strategy.equals("d"))goByBike=true;
        String postFix=" m";
        if(strategy.equals("b")||strategy.equals("d"))postFix=" s";
        return ans+postFix;
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

    static public  double getTargetBuildingCost(Position position,Building destination, String strategy, String carType) {
        return new Student(position).getTargetBuildingCost( destination, strategy, carType);
    }
    /* 得到各种形式的花费*/
    public double getTargetBuildingCost(Building destination, String strategy, String carType) {
        TableEntry.setStrategy(strategy);
        double totalTime = 0;
        int nowPositinIndex = position.getCurrentMap().getBuildingsOrder(position.getNowBuilding().getNameOfBuildingInEnglish());
        if (position.getCurrentMap() == destination.map) {
            int destinationIndex = destination.index;
            pathsToGo.addAll( position.getCurrentMap().getShortestRoute(nowPositinIndex, destinationIndex));
            totalTime = TableEntry.totalCost;
        } else {
            int busStop = position.getCurrentMap().IndexOfBus();//todo: //确认车站的下标Index
            pathsToGo.addAll( position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop));

            totalTime = TableEntry.totalCost;

            if (strategy.equals("b")|| strategy.equals("d")) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                if (carType .equals( "bus") ){
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
        if (strategy!="c"&&position.isOnBuilding)
        {
            pathsToGo.clear();
        }
        TableEntry.setStrategy(strategy);
        if (position.isOnBuilding&& destination==position.getNowBuilding()) {
            Log.e(TAG, "getShortestRouteToTarget: "+position.isOnBuilding );
            pathsToGo=new LinkedList<>();
            return;
        }
        int nowPositinIndex = position.getCurrentMap().getBuildingsOrder(position.getNowBuilding().getNameOfBuildingInEnglish());
        if (position.getCurrentMap() == destination.map) {
            int destinationIndex = destination.index;
            pathsToGo.addAll( position.getCurrentMap().getShortestRoute(nowPositinIndex, destinationIndex));
        } else {
            int busStop;
            int busBegin;
            if (destination.map.isCampus())
            {
                busStop =position.getCurrentMap().IndexOfBus();// destination.map.IndexOfBus();//todo: //确认车站的下标Index
                busBegin=destination.map.IndexOfBus();
            }
            else
            {
                if (destination instanceof Room)
                {
                //todo:新的busStop
                }

                busStop=destination.index;
                busBegin=destination.map.IndexOfExit();
            }


            pathsToGo.addAll(  position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop));

            Queue<Path> pathsToGo2 = new LinkedList<>();

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
            int busStop;
            if (position.getCurrentMap().isCampus())
            {
                busStop =position.getCurrentMap().IndexOfBus();// destination.map.IndexOfBus();//todo: //确认车站的下标Index
            }
            else
            {
                busStop=destination.index;
            }
            pathsToGo.addAll( position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop));

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
    private void dealStopInPath(Building destination, String strategy,String carType)
    {
        pathsToGo.clear();
        Position endPostion=new Position(position.getPath().getEnd());//通过path更新buiding
        double endCost=getTargetBuildingCost(endPostion,destination,strategy,carType);
        Position startPostion=new Position(position.getPath().getStart());//通过path更新buiding
        double startCost=getTargetBuildingCost(startPostion,destination,strategy,carType);
        if (endCost>=startCost)
        {
           pathsToGo.add(position.getPath().getStart().map.getPaths()[position.getPath().getEnd().index][position.getPath().getStart().index]);
            Log.e(TAG, "dealStopInPath: "+"dsdasdasdasdsa" );
        }else
        {
            pathsToGo.add(position.getPath());
            Log.e(TAG, "dealStopInPath: "+"sadasdasdasdasdsadasdasdasdasdasdsadsadsadawsWWWWWWWWWWWWWWWWWWWWWWWWWWWW" );
        }
    }
    private Queue<Pair<Building,Double>>getAround(Building center,int deepth)
    {
        Queue<Pair<Building,Double>> around = new LinkedList<>();

        return around;

    }
}
