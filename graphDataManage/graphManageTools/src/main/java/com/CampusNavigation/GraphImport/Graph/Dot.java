package com.CampusNavigation.GraphImport.Graph;

import java.io.Serializable;

public class Dot implements Serializable {
    private String name="";
    public double xg;
    public double yg;
    public final double X;
    public final double Y;
    public double rg;
    private String position="";
    private int index=0;
    private BuildingType type;
    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public Dot(String position, double x, double y,double rg,double xg,double yg){
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


}

