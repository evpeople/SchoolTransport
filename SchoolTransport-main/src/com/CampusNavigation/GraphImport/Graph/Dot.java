package com.CampusNavigation.GraphImport.Graph;

import java.io.Serializable;

public class Dot implements Serializable {
    public double xg;
    public double yg;
    public final double X;
    public final double Y;
    public double rg;
    private String position="";//建筑名字（英文）
    private int index=0;
    private BuildingType type=BuildingType.Default;
    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Dot(String position, double x, double y,double rg,double xg,double yg){//坐标名字(x,y)
        this.position=position;
        this.xg=xg;
        this.yg=yg;
        this.rg =rg;
        this.X=x;
        this.Y=y;
        this.type= BuildingType.canteen;
    }
    public Dot(String position, double x, double y){
        this.position=position;
        this.X=x;
        this.Y=y;
        this.xg=x;
        this.yg=y;
        this.rg=3;
        this.type= BuildingType.Default;
    }

    public BuildingType getType() {
        return type;
    }
    public void setGui(double x,double y){
        this.xg=x;
        this.yg=y;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public void setRg(){
        rg=Math.sqrt((X-xg)*(X-xg)+(Y-yg)*(Y-yg));
    }
    public void setRg(double r){
        this.rg=r;
    }

    public String getPosition() {
        return position;
    }
}

