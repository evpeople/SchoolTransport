package com.CampusNavigation.GraphImport.Graph;

public class Edge {
    public final Dot start;
    public final Dot end;
    private boolean bikeOk=false;
    private float degreeOfCongestion=0.5f;
    public Edge(Dot start, Dot end){
        this.start=start;
        this.end=end;
    }
    public void setBikeOk(boolean bikeOk) {
        this.bikeOk = bikeOk;
    }

    public boolean isBikeOk() {
        return bikeOk;
    }

    public float getDegreeOfCongestion() {
        return degreeOfCongestion;
    }
}
