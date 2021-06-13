package com.CampusNavigation.Map;

import com.CampusNavigation.GraphImport.Graph.BuildingType;
import com.CampusNavigation.GraphImport.Graph.Edge;

import java.util.Calendar;

import static com.CampusNavigation.GraphImport.Graph.BuildingType.*;

/**
 * @see Path 道路类，不同于路径类
 * @see Path#Path(Edge, Building[])
 * @see Path#Path(int, boolean, Building, Building) 构造道路
 * @see Path#getTime() 走完道路所需时间
 * @see Path#setCrowdDegree(BuildingType) 设置道路初始拥挤度
 * @see Path#length 道路长度
 * @see Path#crowdDegree 道路拥挤度
 * @see Path#isBike 是否为自行车道
 * @see Path#start 起点建筑
 * @see Path#end 终点建筑
 * @see Path#v 道路行进速度
 */
public class Path {

    private double length;
    private double time;
    private double crowdDegree;
    private boolean isBike;
    private Building start;
    private Building end;
    private double v;

    /**
     * @param edge 构造道路的边
     * @param buildings 起点与终点的二元组
     * */
    public Path(Edge edge, Building[] buildings) {
        this.crowdDegree = edge.getDegreeOfCongestion();
        this.isBike = edge.isBikeOk();
        this.start = buildings[edge.start.getIndex()];
        this.end = buildings[edge.end.getIndex()];
        this.length = Math
                .sqrt((start.mathX - end.mathX) * (start.mathX - end.mathX) + (start.mathY
                        - end.mathY) * (start.mathY - end.mathY));

        this.v=60;
    }

    public double getTime() {
        //setCrowdDegree(start.type);
        return length / (v * crowdDegree);
    }

    public boolean isBike() {
        return isBike;
    }



    /**
     * 构造器方法
     * @param length      路径长度
     * @param isBike      是否为自行车道
     * @param start       起点建筑
     * @param end         终点建筑
     */
    public Path(int length, boolean isBike, Building start, Building end){
        this.length = length;
        this.crowdDegree = 1.0;
        this.isBike = isBike;
        this.start = start;
        this.end = end;
    }

    public void setV(double v) {
        this.v = v;
    }

    /**
     * 将路径分为几个不同的type，分别有自己不同的拥挤度函数，可以根据type抉择拥挤度函数
     * @param typeOfPath 道路的类型
     */
    public double setCrowdDegree(BuildingType typeOfPath) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        double result = 1;
        switch (typeOfPath) {
            case canteen://食堂附近道路
            case bus:
            case lab:
            case car:
                result = 0.5 + (Math.exp(-0.15 * (Math.pow((hour - 14.5), 2.0))) * (Math
                        .pow((hour - 14.5), 2.0))) + 0.25 + Math.sin(0.1 * minute);
                break;
            case lake:
            case dorm:
            case exit:
            case swim:
            case tree:
            case teach:
                result = +Math.exp(-2 * Math.pow((hour - 13), 4.0)) + Math
                        .exp(-2 * Math.pow((hour - 18), 4.0)) + 0.5 * Math.sin(0.1 * minute);
                break;
            case coffee:
            case office:
                result = 0.25 + 0.25 * Math.sin(0.1 * minute);
                break;
            case runway:
            case soccer:
            case Default:
            case service:
                result = 0.25 + 0.25 * Math.sin(0.1 * minute);
                break;
            case crossing:
            case hospital:
            case librariy:
                result = 0.75 + Math.exp(-Math.pow(0.1 * (hour - 19), 2)) + 0.5 * Math.sin(0.1 * minute);
                break;
            case studentCenter:
            case buildingCrossing:
            default:
                result = 0.75;
        }
        crowdDegree=result;
        return result;
    }

    @Override
    public String toString() {
        return "Path{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    public double getLength() {
        return length;
    }

    public Building getEnd() {
        return end;
    }

    public Building getStart() {
        return start;
    }

    public double CrowdDegree() {
        return crowdDegree;
    }
}
