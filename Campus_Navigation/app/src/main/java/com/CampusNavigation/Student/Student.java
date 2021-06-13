package com.CampusNavigation.Student;

import android.util.Log;
import android.util.Pair;

import com.CampusNavigation.Gui.StudentView;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Path;
import com.CampusNavigation.Map.Room;
import com.CampusNavigation.Map.TableEntry;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import static com.shopgun.android.utils.Tag.TAG;

/**
 * @see Student 学生类的基础架子.
 * @see Student#Student(Position)  传入一个初始位置构造一位学生
 * @see Student#setTargetBuilding(Building, String, boolean) 设置目标地点为Building所代表的值，同时通过String传入了导航策略，并判断是否选择了班车
 * @see Student#setTargetBuilding(Queue) 获取途径最短导航方式的结果
 * @see Student#getCostToTarget(Building, String, boolean)  获取当前位置到目的地的时间或长度
 * @see Student#getTargetBuildingCost(Position, Building, String, String) 获取当前位置到目的地的时间或长度
 * @see Student#getTargetBuildingCost(Queue) 获取当前位置途径最短的时间或长度
 * @see Student#getTargetBuildingCost(Building, String, String) 获取当前位置到目的地时间或路程
 * @see Student#getShortestRouteToTarget(Building, String) 得到最短距离
 * @see Student#getShortestRouteToTarget(int, String) 得到最短距离
 * @see Student#getShortestRouteToTarget(Building, Building, String) 得到最短距离
 * @see Student#dealStopInPath(Building, String, String) 处理当学生停在路中间的情况
 * @see Student#getAround(Building, int) 获取周边一定距离内的建筑物
 * @see Student#position 学生所在位置
 * @see Student#pathsToGo 学生即将走过的道路
 * @see Student#view 在图形化界面的学生图标
 * @see Student#goByBike 学生是否处于使用自行车的状态
 */
public class Student {

    private Position position;
    private double walkSpeed;
    public Queue<Path> pathsToGo = new LinkedList<>();
    public StudentView view;
    private Building targetBuilding;
    private boolean goByBike;

    /**
     * @param position   学生位置
     */
    public Student(Position position) {
        this.position = position;
        this.walkSpeed = 60;//初始速度60米每分钟
    }
    /**
     * @param targetBuilding 目标地点建筑物
     * @param strategy 使用的导航策略
     * @param ByBus 学生是否乘坐巴士
     * */
    //唯一供前端调用接口
    public void setTargetBuilding(Building targetBuilding, String strategy, boolean ByBus) {
        String carType=ByBus?"bus":"car";
        this.targetBuilding = targetBuilding;
        if (!position.isOnBuilding()) {
            dealStopInPath(targetBuilding,strategy,carType);
        }
            switch (strategy) {
                case "a":
                case "b":
                case "d":
                    getShortestRouteToTarget(targetBuilding,strategy);
                    break;
                default:
                    break;
            }
            if (strategy.equals("d")) goByBike = true;
        }
    public void setTargetBuilding(Queue<Building> targetBuilding,  boolean ByBus) {
        String carType=ByBus?"bus":"car";
        if (!position.isOnBuilding()) {
            dealStopInPath(targetBuilding.peek(), "a", carType);
        }
        if (targetBuilding.size()==0)
        {
            return;
        }
        setTargetBuilding(targetBuilding);

    }


    /**
     * @param targetBuilding 目标地点建筑物
     * @param strategy 使用的导航策略
     * @param ByBus 学生是否乘坐巴士
     * */
    //唯二共前端调用接口
    public String getCostToTarget(Building targetBuilding,String strategy,boolean ByBus) throws CloneNotSupportedException {
        TableEntry.totalCost=0;
        double ans=0;
        String carType=ByBus?"bus":"car";
        if(!position.isOnBuilding()) {
            if (ByBus) dealStopInPath(targetBuilding, strategy, "bus");
            else dealStopInPath(targetBuilding, strategy, "car");
        }
        switch (strategy){
            case "a": case "b": case "d":
                 ans=getTargetBuildingCost((Position) position.clone(),targetBuilding,strategy,carType);break;
            default:  break;
        }
        if(strategy.equals("d"))goByBike=true;
        String postFix=" m";
        if(strategy.equals("b")||strategy.equals("d"))postFix=" s";
        return new DecimalFormat("0.000").format(ans)+postFix;
    }
    public String getCostToTarget(Queue<Building>targetBuilding,boolean ByBus) throws CloneNotSupportedException {
        double ans=0;
        String carType=ByBus?"bus":"car";
        if(!position.isOnBuilding()) {
            dealStopInPath(targetBuilding.peek(), "a", carType);
        }

        ans=getTargetBuildingCost(targetBuilding);
        String postFix=" m";
        return new DecimalFormat("0.000").format(ans)+postFix;
    }

    /**
     * @param targetBuilding 即将途径的建筑物队列
     * */
    public void setTargetBuilding(Queue<Building> targetBuilding) {
        TableEntry.setStrategy("a");
        if (position.getNowBuilding() == targetBuilding.peek()) {
            return;
        }
        while (!targetBuilding.isEmpty()) {
            Position temp = new Position(targetBuilding.peek());
            Student temp2 = new Student(temp);
            temp2.getShortestRouteToTarget(targetBuilding.poll(), "a");
            pathsToGo.addAll(temp2.pathsToGo);
        }
    }

    /**
     * @param targetBuilding 即将途径的建筑物队列
     * */
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

    /**
     * @param position 用户所在位置
     * @param destination 目标建筑物
     * @param strategy 使用的导航策略
     * @param carType 使用的交通工具
     * */
    static public  double getTargetBuildingCost(Position position,Building destination, String strategy, String carType) {
        return new Student(position).getTargetBuildingCost( destination, strategy, carType);
    }

    /**
     * @param destination 目标建筑物
     * @param strategy 使用的导航策略
     * @param carType 使用的交通工具
     * */
    /* 得到各种形式的花费*/
    public double getTargetBuildingCost(Building destination, String strategy, String carType) {
        TableEntry.setStrategy(strategy);
        TableEntry.totalCost=0;

        getShortestRouteToTarget(destination,strategy);
        pathsToGo.clear();
        double totalTime=TableEntry.totalCost;
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
        return totalTime;
    }

    /**
     * @param destination 目标建筑物
     * @param strategy 使用的导航策略
     * */
    //1代表最短路径，2代表最短时间，3代表途径最短距离，4代表交通工具最短
    private void getShortestRouteToTarget(Building destination, String strategy) {
        if (!strategy.equals("c")&&position.isOnBuilding)//todo:not understand
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
            int destinationIndex = destination.index;// 同图之间动起来
            pathsToGo.addAll( position.getCurrentMap().getShortestRoute(nowPositinIndex, destinationIndex));
        } else {
            int busStop;
            int busBegin = 0;
            if (destination.map.isCampus()&&position.getCurrentMap().isCampus())
            {
                //跨校区
                TableEntry.totalCost=200;
                busStop =position.getCurrentMap().IndexOfBus();
                pathsToGo.addAll(  position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop));
                busBegin=destination.map.IndexOfBus();
            }
            else
            {
                if ((destination instanceof Room)&&!(position.getNowBuilding() instanceof Room)) // 目的地在室内，人在室外
                {
                    if (((Room) destination).getBelongToBuilding().map==position.getCurrentMap())
                    {
                        busStop= ((Room)destination).getBelongToBuilding().index;
                        pathsToGo.addAll(  position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop));
                        busBegin=destination.map.IndexOfExit();
                    }else
                    {
                        TableEntry.totalCost=200;
                        busStop =position.getCurrentMap().IndexOfBus();
                        //从当前位置导航到车站
                        pathsToGo.addAll(  position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop));
                        //从新图的车站导航到建筑物
                        pathsToGo.addAll(((Room) destination).getBelongToBuilding().map.getShortestRoute(((Room) destination).getBelongToBuilding().map.IndexOfBus(),((Room) destination).getBelongToBuilding().index));
                        //从新建筑物导航的上去
                        busBegin=destination.map.IndexOfExit();
                    }

                }

                else if ((destination instanceof Room)&&(position.getNowBuilding() instanceof Room))// 都在室内    （判断是不是一个建筑物的室内）
                {
                    if (position.getNowBuilding()==((Room) destination).getBelongToBuilding()) {//统一建筑物的的室内
                        busStop = position.getCurrentMap().IndexOfExit();
                        pathsToGo.addAll(  position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop));
                        busBegin = destination.map.IndexOfExit();
                        TableEntry.totalCost=20;
                    }
                    else { //不同建筑为室内
                        if (((Room) position.getNowBuilding()).getBelongToBuilding().map==((Room) destination).getBelongToBuilding().map)
                        {//同一校区
                            TableEntry.totalCost=40;
                            busStop=position.getCurrentMap().IndexOfExit();
                            pathsToGo.addAll(  position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop));
                            pathsToGo.addAll(((Room) position.getNowBuilding()).getBelongToBuilding().map.getShortestRoute(((Room) position.getNowBuilding()).getBelongToBuilding().index,((Room) destination).getBelongToBuilding().index));
                            busBegin=destination.map.IndexOfExit();

                        }
                        else
                        {//不同校区
                            TableEntry.totalCost=200;
                            //todo:  set拥挤度,途径,时间倒流,查找
                            busStop=position.getCurrentMap().IndexOfExit();
                            pathsToGo.addAll(  position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop));
                            int busStation=((Room) position.getNowBuilding()).getBelongToBuilding().map.IndexOfBus();
                            int indexOfBuild=((Room) position.getNowBuilding()).getBelongToBuilding().index;
                            pathsToGo.addAll(((Room) position.getNowBuilding()).getBelongToBuilding().map.getShortestRoute(indexOfBuild,busStation));
                            busStation=((Room) destination).getBelongToBuilding().map.IndexOfBus();
                            indexOfBuild=((Room) destination).getBelongToBuilding().index;
                            pathsToGo.addAll(((Room) destination).getBelongToBuilding().map.getShortestRoute(busStation,indexOfBuild));
                            busBegin=destination.map.IndexOfExit();
                        }
                    }
                }
                else if (!(destination instanceof Room)&&(position.getNowBuilding()) instanceof  Room)//人zd room  要进去楼
                {
                    if (((Room)position.getNowBuilding()).getBelongToBuilding().map==destination.map)
                    {
                        //直接出楼
                        TableEntry.totalCost=20;
                        busStop=position.getCurrentMap().IndexOfExit();
                        pathsToGo.addAll(  position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop));

                        busBegin=((Room) position.getNowBuilding()).getBelongToBuilding().index;
                    }
                    else
                    {
                        TableEntry.totalCost=200;
                        busStop=position.getCurrentMap().IndexOfExit();
                        pathsToGo.addAll(  position.getCurrentMap().getShortestRoute(nowPositinIndex, busStop));
                        pathsToGo.addAll(((Room) position.getNowBuilding()).getBelongToBuilding().map.getShortestRoute(((Room) position.getNowBuilding()).getBelongToBuilding().index,((Room) position.getNowBuilding()).getBelongToBuilding().map.IndexOfBus()));

                        busBegin=destination.map.IndexOfBus();
                    }
                }
            }
            Queue<Path> pathsToGo2;
            int destinationIndex = destination.index;
            pathsToGo2 = destination.map.getShortestRoute(busBegin, destinationIndex);
            pathsToGo.addAll(pathsToGo2);
        }
    }

    /**
     * @param destination 目标建筑物
     * @param posBuilding 当前所在建筑物
     * @param strategy 使用的导航策略
     * */
    private void getShortestRouteToTarget(Building destination, Building posBuilding, String strategy) {
        TableEntry.setStrategy(strategy);
        int nowPositinIndex = posBuilding.index;
        if (destination==posBuilding) {
           return;
        }
        Position temp =new Position(posBuilding);
        Student temp2=new Student(temp);
        //todo: 此处new了 一个Student
        temp2.getShortestRouteToTarget(destination,strategy);
        pathsToGo.addAll(temp2.pathsToGo);
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

    /**
     * @param destination 目标建筑物
     * @param strategy 使用的导航策略
     * @param carType 使用的交通工具
     * */
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

    /**
     * @param center 指定其周边的目标建筑物
     * @param deepth 指定附近的最大距离
     * */
    private Queue<Pair<Building,Double>>getAround(Building center,int deepth)
    {
        return center.map.getAround(center.index,deepth);

    }
}
