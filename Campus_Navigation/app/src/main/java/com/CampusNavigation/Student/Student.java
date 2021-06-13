package com.CampusNavigation.Student;

/**
 * 学生类的基础架子.
 */

import android.util.Log;
import android.util.Pair;

import com.CampusNavigation.Gui.StudentView;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Path;
import com.CampusNavigation.Map.Room;
import com.CampusNavigation.Map.TableEntry;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import static com.shopgun.android.utils.Tag.TAG;

public class Student {

    private Position position;
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
    public void setTargetBuilding(Building targetBuilding, String strategy, boolean ByBus) {
        this.targetBuilding = targetBuilding;
        if (!position.isOnBuilding()) {
            if (ByBus) dealStopInPath(targetBuilding, strategy, "bus");
            else dealStopInPath(targetBuilding, strategy, "car");
            switch (strategy) {
                case "c":
                    setTargetBuilding(null);
                    break;
                case "a":
                case "b":
                case "d":
                    getShortestRouteToTarget(targetBuilding, strategy);
                    break;
                default:
                    break;
            }
            if (strategy.equals("d")) goByBike = true;
        }
    }
    //唯二共前端调用接口
    public String getCostToTarget(Building targetBuilding,String strategy,boolean ByBus) throws CloneNotSupportedException {
        double ans=0;
        String carType=ByBus?"bus":"car";
        if(!position.isOnBuilding()) {
            if (ByBus) dealStopInPath(targetBuilding, strategy, "bus");
            else dealStopInPath(targetBuilding, strategy, "car");
        }
        switch (strategy){
            case "c": new Student((Position) position.clone()).getTargetBuildingCost(null); break;
            case "a": case "b": case "d":
                 ans=getTargetBuildingCost((Position) position.clone(),targetBuilding,strategy,carType);break;
            default:  break;
        }
        if(strategy.equals("d"))goByBike=true;
        String postFix=" m";
        if(strategy.equals("b")||strategy.equals("d"))postFix=" s";
        return new DecimalFormat("0.000").format(ans)+postFix;
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
        double cost;
        getShortestRouteToTarget(targetBuilding.poll(), "a");
        cost=TableEntry.totalCost;
        while (!targetBuilding.isEmpty()) {
            getShortestRouteToTarget(targetBuilding.poll(), targetBuilding.poll(), "a");
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
        double totalTime;
        int nowPositinIndex = position.getCurrentMap().getBuildingsOrder(position.getNowBuilding().getNameOfBuildingInEnglish());
        if (position.getCurrentMap() == destination.map) {
            int destinationIndex = destination.index;
            pathsToGo.addAll( position.getCurrentMap().getShortestRoute(nowPositinIndex, destinationIndex));
            totalTime = TableEntry.totalCost;
        } else {
            int busStop = position.getCurrentMap().IndexOfBus();
            pathsToGo.addAll( position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop));

            totalTime = TableEntry.totalCost;

            if (strategy.equals("b")|| strategy.equals("d")) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                if (carType .equals( "bus") ){
                    totalTime += (( - minute) % 30);
                } else {
                    double temp;
                    if (minute==0&&(hour==8||hour==12||hour==18)) {
                        temp=0;
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

            Queue<Path> pathsToGo2;
            int busBegin = destination.map.IndexOfBus();
            int destinationIndex = destination.index;
            pathsToGo2 = destination.map.getShortestRoute(busBegin, destinationIndex);
            pathsToGo.addAll(pathsToGo2);

            totalTime += TableEntry.totalCost;
        }
        return totalTime;
    }


    //1代表最短路径，2代表最短时间，3代表途径最短距离，4代表交通工具最短
    private void getShortestRouteToTarget(Building destination, String strategy) {
        if (!strategy.equals("c")&&position.isOnBuilding)
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
            int busStop = 0;
            int busBegin;
            if (destination.map.isCampus())
            {
                busStop =position.getCurrentMap().IndexOfBus();
                busBegin=destination.map.IndexOfBus();
            }
            else
            {
                if (destination instanceof Room)
                {
                   busStop= ((Room)destination).getBelongToBuilding().index;

                }

                busBegin=destination.map.IndexOfExit();
            }


            pathsToGo.addAll(  position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop));

            Queue<Path> pathsToGo2;
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
            int busStop=0;
            int busBegin=0;
            if (position.getCurrentMap().isCampus())
            {
                busStop =posBuilding.map.IndexOfBus();
                busBegin=destination.map.IndexOfBus();
            }
            else
            {
                if (destination instanceof Room)
                {
                    busStop= ((Room)destination).getBelongToBuilding().index;
                    busBegin=destination.map.IndexOfExit();

                }

            }
            pathsToGo.addAll( position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop));

            Queue<Path> pathsToGo2;

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
        }
        {
            pathsToGo.add(position.getPath());
        }
    }
    private Queue<Pair<Building,Double>>getAround(Building center,int deepth)
    {
        return center.map.getAround(center.index,deepth);

    }
}
