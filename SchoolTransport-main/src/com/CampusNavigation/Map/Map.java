package com.CampusNavigation.Map;

import com.CampusNavigation.GraphImport.Graph.*;
import com.CampusNavigation.GraphImport.graphManage.graphManager;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Route;

import java.io.IOException;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 地图类.
 */
public class Map {
    final static Logger logger = LoggerFactory.getLogger(Map.class);
    public static final int MaxNumOfDots = 80;
    private final int NumOfBuildings;
    private Building[] Buildings = new Building[MaxNumOfDots];
    private Path[][] Paths = new Path[MaxNumOfDots][MaxNumOfDots];

    public Map() throws IOException {
        Graph graph = graphManager.manage(null);
        this.NumOfBuildings = graph.NumOfDots();
//        logger.debug(String.valueOf(this.NumOfBuildings));
        int now = 0;
        for (Dot dot : graph.getDots()) {
            if (dot == null) break;
            switch (dot.getType()){
                case exit:Buildings[now] = new Exit(dot,this);break;
                default:Buildings[now] = new SpecificBuild(dot,this);break;
            }
            now++;
//            logger.debug(String.valueOf(this.NumOfBuildings));

        }
        logger.debug(String.valueOf(this.NumOfBuildings));
        for (int i = 0; i < NumOfBuildings; i++)
            for (int j = 0; j < NumOfBuildings; j++) {
            if(graph.getEdges()[i][j]==null)Paths[i][j]=null;
            else {
            Paths[i][j]=new Path(graph.getEdges()[i][j],Buildings);
            }
        }

    }


    public Route[] getShortestRoute(Building start,Building end  ,String strategy) {

        return new Route[0];
    }

    public String[] Inquire(Position nowPosition) {
        return new String[0];
        //返回的字符串要有距离，详细信息，
    }

    public Path[][] getPaths() {
        return Paths;
    }

    public Building[] getBuildings() {
        return Buildings;
    }

    private void InitTable(Map G,TableEntry[] T){
        for (int i=0;i<G.NumOfBuildings;i++)
        {
            T[i]=new TableEntry(i,G.Buildings[i],false,Double.POSITIVE_INFINITY,null);
        }
        T[0].setDist(0);

    }
    private void Dijkstra(TableEntry[] T){
        InitTable(this,T);
        int i=0;
        int t=0;
        while (t!=this.NumOfBuildings) {
            T[i].setKnown(true);
            i=UpdateTableEntry(T, T[i]);
            t++;
        }

    }
    private int UpdateTableEntry(TableEntry []T,TableEntry known){
        Path[] temp=this.Paths[known.getNumOfBuilding()];
        double dv=Double.POSITIVE_INFINITY;
        int minRoute = -1;
        for (int i=0;i<this.NumOfBuildings;i++)
        {
                if (!T[i].isKnown()&&temp[i]!=null) {
                    T[i].setDist(temp[i].getLength()+known.getDist());
                    Route temp_p = new Route(this.Buildings[known.getNumOfBuilding()],
                        this.Buildings[i]);
                    HashMap<Building, Path> RouteToDestination = new HashMap<>();
                    RouteToDestination.put(this.Buildings[known.getNumOfBuilding()], temp[i]);
                    temp_p.setRouteToDestination(RouteToDestination);
                    T[i].setPathToBuilding(temp_p);
                }
        }
        for (int i=0;i<this.NumOfBuildings;i++) {
            if ((!T[i].isKnown())&&T[i].getDist()<dv){
                minRoute=i;
                dv=T[i].getDist();
        }

        }
        return minRoute;
    }
}

class TableEntry{
    private int numOfBuilding;
    private Building Header;
    private boolean Known;
    private double Dist;
    Route PathToBuilding;
    public TableEntry(int numOfBuilding,Building header,boolean known,double dist,Route PathToBuilding){
        this.numOfBuilding=numOfBuilding;
        this.Header=header;
        this.Known=known;
        this.Dist=dist;
        this.PathToBuilding=PathToBuilding;
    }

    public void setDist(double dist) {
        if (dist<Dist)
        {
            Dist = dist;
        }
    }

    public void setHeader(Building header) {
        Header = header;
    }

    public void setKnown(boolean known) {
        Known = known;
    }

    public void setPathToBuilding(Route pathToBuilding) {
        PathToBuilding = pathToBuilding;

    }

    public int getNumOfBuilding() {
        return numOfBuilding;
    }

    public boolean isKnown() {
        return Known;
    }

    public double getDist() {
        return Dist;
    }
}