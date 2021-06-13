package com.CampusNavigation.Map;

import android.util.Log;

import com.CampusNavigation.Student.Route;

import static com.shopgun.android.utils.log.LogUtil.TAG;

public class TableEntry {

    private int numOfBuilding;
    private Building header;
    private boolean known;
    private double dist;
    static public int stra;
    static public double totalCost;
    Route pathToBuilding;

    public TableEntry(int numOfBuilding, Building header, boolean known, double dist,
                      Route pathToBuilding) {
        this.numOfBuilding = numOfBuilding;
        this.header = header;
        this.known = known;
        this.dist = dist;
        this.pathToBuilding = pathToBuilding;
    }

    public void setDist(double dist) {
        if (dist < this.dist) {
            this.dist = dist;
        }
    }
    public static void setStrategy(String stratery)
    {
        switch (stratery)
        {
            case "a":
                stra=1;
                break;
            case "b":
                stra=2;
                break;
            case "c":
                stra=3;
                break;
            case "d":
                stra=4;
                break;
            case "e":
                stra=5;
                break;
            default:
                stra=1;
                break;
        }
    }
    public void setHeader(Building header) {
        this.header = header;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public void setPathToBuilding(Route pathToBuilding) {
        this.pathToBuilding = pathToBuilding;
    }

    public int getNumOfBuilding() {
        return numOfBuilding;
    }

    public boolean isNotKnown() {
        return !known;
    }

    public double getDist() {
        return dist;
    }

    public Route getPathToBuilding() {
        return pathToBuilding;
    }

    @Override
    public String toString() {
        return "TableEntry{"
                +
                "numOfBuilding=" + numOfBuilding
                +
                ", Header=" + header.toString()
                +
                ", Known=" + known
                +
                ", Dist=" + dist
                +
                ", PathToBuilding=" + pathToBuilding
                +
                '}';
    }
}
