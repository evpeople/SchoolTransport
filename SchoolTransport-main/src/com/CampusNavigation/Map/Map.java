package com.CampusNavigation.Map;

import com.CampusNavigation.GraphImport.Graph.*;
import com.CampusNavigation.GraphImport.graphManage.graphManager;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Route;

import java.io.IOException;

/**
 * 地图类.
 */
public class Map {
    public static final int MaxNumOfDots = 80;
    private final int NumOfBuildings;
    private Building[] Buildings = new Building[MaxNumOfDots];
    private Path[][] Paths = new Path[MaxNumOfDots][MaxNumOfDots];

    public Map() throws IOException {
        Graph graph = graphManager.manage(null);
        this.NumOfBuildings = graph.NumOfDots();
        int now = 0;
        for (Dot dot : graph.getDots()) {
            if (dot == null) break;
            switch (dot.getType()){
                case exit:Buildings[now] = new Exit(dot);break;
                default:Buildings[now] = new SpecificBuild(dot);break;
            }
            now++;
        }
        for (int i = 0; i < NumOfBuildings; i++)
            for (int j = 0; j < NumOfBuildings; j++) {
            if(graph.getEdges()[i][j]==null)Paths[i][j]=null;
            else {
            Paths[i][j]=new Path(graph.getEdges()[i][j],Buildings);
            }
        }

    }


    public Route[] getShortestRoute() {

        return new Route[0];
    }

    public String[] Inquire(Position nowPosition) {
        return new String[0];
        //返回的字符串要有距离，详细信息，
    }


}
