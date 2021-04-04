package com.CampusNavigation.GraphImport.Graph;

import java.io.Serializable;
import java.nio.file.Paths;

public class Graph implements Serializable {

    public static final int MaxNumOfDots=80;
    private int NumOfDots=0;
    private Dot[] dots=new Dot[MaxNumOfDots];
    private   Edge[][] edges=new Edge[MaxNumOfDots][MaxNumOfDots];
    public Graph(){

        Dot dot2=new Dot("cool",1000,1000,100,1000,1100);
        Dot dot1= new Dot("cool",600,600,100,700,600);
        dots[0]=dot1;
        dots[1]=dot2;
        edges[0][1]=new Edge(dot1,dot2);
        NumOfDots=2;
    }
    public Graph(int NumOfDots,Dot[]dots,Edge[][]edges) {
        this.dots = dots;
        this.edges = edges;
        this.NumOfDots = NumOfDots;
    }

    public Dot[] getDots() {
        return dots;
    }

    public Edge[][] getEdges() {

        return edges;
    }

    public int NumOfDots() {
        return NumOfDots;
    }

}
