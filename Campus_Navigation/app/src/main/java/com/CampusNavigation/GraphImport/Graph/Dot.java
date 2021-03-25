package com.CampusNavigation.GraphImport.Graph;

public class Dot {
    private String name="";
    public final double xg;
    public final double yg;
    public final double X;
    public final double Y;
    public final double rg;
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
        this.type=BuildingType.canteen;
    }

    public BuildingType getType() {
        return type;
    }
}

