package com.CampusNavigation.Map;

import com.CampusNavigation.Student.Route;

public class TableEntry {

    private int numOfBuilding;
    private Building header;
    private boolean known;
    private double dist;
    static public int stra;
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
        if (stratery=="a")
        {
            stra=1;
        }
        else if (stratery=="b")
        {
            stra=2;
        }
        else if (stratery=="c")
        {
            stra=3;
        }
        else
        {
            stra=4;
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
