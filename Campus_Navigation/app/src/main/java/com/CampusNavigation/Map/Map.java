package com.CampusNavigation.Map;

import android.util.Log;
import android.util.Pair;

import com.CampusNavigation.GraphImport.Graph.Dot;
import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Route;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static com.shopgun.android.utils.log.LogUtil.TAG;

/**
 * 地图类.
 */
public class Map {
    public static final int MaxNumOfDots = 150;
    public final String filePath;
    private int numOfBuildings=0;
    private Building[] buildings = new Building[MaxNumOfDots];
    private Path[][] paths = new Path[MaxNumOfDots][MaxNumOfDots];
    //todo:这个是Ver的，他写注释

    /**
     * 构建邻接表用于DJ算法.
     *
     * @param graph 地图
     * @throws IOException 读取文件出错
     */
    public Map(Graph graph) throws IOException {
        this.numOfBuildings = graph.NumOfDots();
        this.filePath =graph.filePath;
        int now = 0;
        for (Dot dot : graph.getDots()) {
            if (dot == null) {
                break;
            }
            switch (dot.getType()) {
                case exit:
                    buildings[now] = new Exit(dot, this);
                    break;
                default:
                    buildings[now] = new SpecificBuild(dot, this);
                    break;
            }
            now++;
        }
        Log.d("Map 初始化", String.valueOf(this.numOfBuildings));
        for (int i = 0; i < numOfBuildings; i++) {
            for (int j = 0; j < numOfBuildings; j++) {
                if (graph.getEdges()[i][j] == null) {
                    paths[i][j] = null;
                } else {
                    paths[i][j] = new Path(graph.getEdges()[i][j], buildings);
                }
            }
        }
    }

    public Map(int a ){
        this.filePath ="default";
    }


    public Route[] getShortestRoute(Building start, Building end, String strategy) {

        return new Route[0];
    }

    public String[] Inquire(Position nowPosition) {
        return new String[0];
        //返回的字符串要有距离，详细信息，
    }

    public Path[][] getPaths() {
        return paths;
    }

    public Building getBuilding(int index) {
        return buildings[index];
    }

    public int getBuildingsOrder(String nameOfBuilding) {
        int order, judgeOrder = 0;
        for (order = 0; order < MaxNumOfDots; order++) {
            if (this.buildings[order].nameOfBuildingInEnglish.equals(nameOfBuilding)) {
                judgeOrder = 1;
                break;
            }
        }
        if (judgeOrder != 1) {
            order = -1;
        }
        return order;
    }

    private void initTable(Map graph, TableEntry[] t) {
        for (int i = 0; i < graph.numOfBuildings; i++) {
            t[i] = new TableEntry(i, graph.buildings[i], false, Double.POSITIVE_INFINITY, null);
        }
        //t[0].setDist(0);
    }

    /**
     * 最短路径所用的DJ算法. //todo 不同的导航策略
     *
     * @return 一个DJ算法所用表
     */
    protected LinkedList<TableEntry> getAroundTable(int vertex,int deepth)
    {
        TableEntry.totalCost=0;
        TableEntry[] tableEntries = dijkstra(vertex);//dj 没有问题
        LinkedList<TableEntry>ans = new LinkedList<>();
        for (int i=0;i<=tableEntries.length;i++)
        {
            if (tableEntries[i].getDist()<deepth)
            {
                ans.add(tableEntries[i]);
            }

        }
        return ans;
    }

    protected TableEntry[] dijkstra(int vertex) {
        int orginalVertex=vertex;
        TableEntry[] tableEntries = new TableEntry[this.numOfBuildings ];
        initTable(this, tableEntries);
        while (true) {
            tableEntries[vertex].setKnown(true);
            if (tableEntries[vertex].getDist()==Double.POSITIVE_INFINITY)
            {
                tableEntries[vertex].setDist(0);
            }
            vertex = updateTableEntry(tableEntries, tableEntries[vertex]);
            if (vertex == -1) {
                Route tempP = new Route(this.buildings[orginalVertex],
                        this.buildings[orginalVertex]);
                tableEntries[orginalVertex].setPathToBuilding( tempP );
                break;
            }
            for (int ie = 0; ie < this.numOfBuildings; ie++) {
                Log.d("Map 求最短路径",tableEntries[ie].toString());
            }
        }
        return tableEntries;
    }

    private int updateTableEntry(TableEntry[] tableEntries, TableEntry known) {
        Path[] temp = this.paths[known.getNumOfBuilding()];
        double dv = Double.POSITIVE_INFINITY;
        int minRoute = -1;
//更新完之后，当前表中最低的点
       switch (TableEntry.stra)
       {
           case 5: {
               for (int i = 0; i<this.numOfBuildings;i++)
               {
                   if (temp[i] != null) {
                       tableEntries[i].setDist(temp[i].getLength()+known.getDist());
                   }
               }
           }
                break;
           case 1:
           {
               for (int i = 0; i < this.numOfBuildings; i++) {
                   if (tableEntries[i].isNotKnown() && temp[i] != null)
                   //后一个temp[i]为能从起点到这个位置
                   {
                       Log.d("Map 求最短路径","当前点 {} 能到达的一个点是 {} "+
                               this.buildings[known.getNumOfBuilding()].nameOfBuildingInEnglish+
                               this.buildings[tableEntries[i].getNumOfBuilding()].nameOfBuildingInEnglish);
                       Log.e("Map 求最短路径","本点更改前的距离是 {}"+ tableEntries[i].getDist());
                       double oldDist = tableEntries[i].getDist();//设置早了此处
                       if (oldDist>=known.getDist()+temp[i].getLength()) {
                           tableEntries[i].setDist(temp[i].getLength() + known.getDist());
                           Log.i("Map 求最短路径","本点更改后的距离是 {}"+ tableEntries[i].getDist()+"经过的是"+temp[i].toString());
                       }
                       double newDist = tableEntries[i].getDist();
                       if (oldDist == newDist) {
                           continue;
                       }

                       Log.d("Map 求最短路径","本点更改后的距离是 {}"+tableEntries[i].getDist());

                       Route tempP = new Route(this.buildings[known.getNumOfBuilding()],
                               this.buildings[i]);
                       Log.d("Map 求最短路径",tempP.toString());

                       HashMap<Building, Path> routeToDestination = new HashMap<>();
                       routeToDestination.put(this.buildings[known.getNumOfBuilding()], temp[i]);
                       tempP.setRouteToDestination(routeToDestination);
                       tableEntries[i].setPathToBuilding(tempP);
                   }
               }
           }
           break;
           case 2:
           {
               for (int i = 0; i < this.numOfBuildings; i++) {
                   if (tableEntries[i].isNotKnown() && temp[i] != null)
                   //后一个temp[i]为能从起点到这个位置
                   {
                       Log.d("Map 求最短路径","当前点 {} 能到达的一个点是 {} "+
                               this.buildings[known.getNumOfBuilding()].nameOfBuildingInEnglish+
                               this.buildings[tableEntries[i].getNumOfBuilding()].nameOfBuildingInEnglish);
                       Log.d("Map 求最短路径","本点更改前的距离是 {}"+ tableEntries[i].getDist());
                       double oldDist = tableEntries[i].getDist();//设置早了此处
                       if (oldDist>=known.getDist()+temp[i].getTime()) {
                           tableEntries[i].setDist(temp[i].getTime() + known.getDist());
                       }
                       double newDist = tableEntries[i].getDist();
                       if (oldDist == newDist) {
                           continue;
                       }

                       Log.d("Map 求最短路径","本点更改后的距离是 {}"+tableEntries[i].getDist());

                       Route tempP = new Route(this.buildings[known.getNumOfBuilding()],
                               this.buildings[i]);
                       Log.d("Map 求最短路径",tempP.toString());

                       HashMap<Building, Path> routeToDestination = new HashMap<>();
                       routeToDestination.put(this.buildings[known.getNumOfBuilding()], temp[i]);
                       tempP.setRouteToDestination(routeToDestination);
                       tableEntries[i].setPathToBuilding(tempP);
                   }
               }
           }
           break;
           case 3:
           {

           }
           case 4:
           {
               for (int i = 0; i < this.numOfBuildings; i++) {
                   if (tableEntries[i].isNotKnown() && temp[i] != null)
                   //后一个temp[i]为能从起点到这个位置
                   {
                       if (temp[i].isBike())
                       {
                           temp[i].setV(200);
                       }
                       Log.d("Map 求最短路径","当前点 {} 能到达的一个点是 {} "+
                               this.buildings[known.getNumOfBuilding()].nameOfBuildingInEnglish+
                               this.buildings[tableEntries[i].getNumOfBuilding()].nameOfBuildingInEnglish);
                       Log.d("Map 求最短路径","本点更改前的距离是 {}"+ tableEntries[i].getDist());
                       double oldDist = tableEntries[i].getDist();//设置早了此处
                       if (oldDist>=known.getDist()+temp[i].getTime()) {
                           tableEntries[i].setDist(temp[i].getTime() + known.getDist());
                       }
                       double newDist = tableEntries[i].getDist();
                       if (oldDist == newDist) {
                           continue;
                       }

                       Log.d("Map 求最短路径","本点更改后的距离是 {}"+tableEntries[i].getDist());

                       Route tempP = new Route(this.buildings[known.getNumOfBuilding()],
                               this.buildings[i]);
                       Log.d("Map 求最短路径",tempP.toString());

                       HashMap<Building, Path> routeToDestination = new HashMap<>();
                       routeToDestination.put(this.buildings[known.getNumOfBuilding()], temp[i]);
                       tempP.setRouteToDestination(routeToDestination);
                       tableEntries[i].setPathToBuilding(tempP);
                   }
               }
           }
           break;
       }













        for (int i = 0; i < this.numOfBuildings; i++) {
            if ((tableEntries[i].isNotKnown()) && tableEntries[i].getDist() < dv) {
                minRoute = i;
                dv = tableEntries[i].getDist();
                //选取最短的
            }

        }
        if (minRoute != -1) {
            Log.d("Map 求最短路径","本次选取的点是" + this.buildings[minRoute].nameOfBuildingInEnglish);
        }
        return minRoute;
    }

    public Queue<Path> getShortestRoute(int start, int end){
        Queue<Path> shortestRoute = new LinkedList<>();
        HashMap<Building,Path> hashMap=getTheShortestRoute(start,end);
        Building now=buildings[start];
        if (hashMap!=null) {
            while (hashMap.containsKey(now)) {
                shortestRoute.add(hashMap.get(now));
                now = hashMap.get(now).getEnd();
            }
        }
        return (shortestRoute);
    }
    public Queue<Pair<Building,Double>>getAround(int center,int deepth)
    {
        Queue<Pair<Building,Double>> ans= new LinkedList<>();
        LinkedList<TableEntry>tempAns = new LinkedList<>();
        tempAns=getAroundTable(center,deepth);

       for (int size=tempAns.size(),i=0;i<size;i++)
       {

           Pair<Building,Double>temp= new Pair<Building,Double>(this.getBuilding(tempAns.get(i).getNumOfBuilding()),tempAns.get(i).getDist());
           ans.add(temp);
       }
        return ans;
    }
    public HashMap<Building, Path> getTheShortestRoute(int start, int end) {
        HashMap<Building, Path> shortestRoute = new HashMap<>();
        TableEntry.totalCost=0;
        TableEntry[] tableEntries = dijkstra(start);//dj 没有问题
        TableEntry.totalCost=tableEntries[end].getDist();
        Log.i(TAG, "getTheShortestRoute: start is "+start);
        int currentVertex = end;
        Log.i(TAG, "getTheShortestRoute: end is"+end);
        if (currentVertex==start)
        {
            return null;
        }
        do {
            Log.i(TAG, "getTheShortestRoute: currentVertex is"+currentVertex);
            shortestRoute.put(tableEntries[currentVertex].pathToBuilding.getStart(),
                    tableEntries[currentVertex].pathToBuilding.getRouteToDestination()
                            .get(tableEntries[currentVertex].pathToBuilding.getStart()));
            currentVertex = getBuildingsOrder(
                    tableEntries[currentVertex].pathToBuilding.getStart().nameOfBuildingInEnglish);
        } while (currentVertex != start);
        return shortestRoute;
    }

    private ArrayList<Building> breadthFirstSearch(int numOfBuilding, double range) {
        ArrayList<Building> searchResult = new ArrayList<>();
        LinkedList<Integer> toView = new LinkedList<>();
        int[] viewed = new int[MaxNumOfDots];
        int current = 0;
        int nowNum = numOfBuilding;
        double[] currentRange = new double[MaxNumOfDots];
        while ((this.buildings[nowNum] != null) && (currentRange[current] < range)) {
            current = 0;
            viewed[nowNum] = 2;
            if (nowNum != numOfBuilding) {
                searchResult.add(this.buildings[nowNum]);
            }
            while (this.buildings[current] != null) {
                if (this.paths[nowNum][current] != null) {
                    if(viewed[current] == 0) {
                        viewed[current] = 1;
                        toView.offer(current);
                    }
                }
                current++;
            }
            currentRange[toView.peek()] += this.paths[nowNum][toView.peek()].getLength();
            nowNum = toView.poll();
        }
        return searchResult;
    }

    public ArrayList<Building> getSurroundings(int numOfBuilding) {
        return breadthFirstSearch(numOfBuilding, 100.0);
    }
}

