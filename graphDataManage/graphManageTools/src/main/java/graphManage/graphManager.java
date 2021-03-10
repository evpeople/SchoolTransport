package graphManage;

import Graph.*;
import myTools.MyMatcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public abstract class graphManager {//(?<=@alreadyCardToday:)[a-z]{4,5}(?=;@) dot((-2.22,2.45),dotstyle);
    private static final String  toMatchDotName="(?<=dot\\()\\([0-9.-]+,[0-9.-]+\\)(?=,dotstyle\\);)";
    private static final String toMatchDotX="(?<=dot\\(\\()[0-9.-]+(?=,[0-9.-]+\\),dotstyle\\);)";
    private static final String toMatchDotY="(?<=dot\\(\\([0-9.-]+,)[0-9.-]+(?=\\),dotstyle\\);)";
    private static final String toMatchLineDot1="(?<=draw\\()\\([0-9.-]+,[0-9.-]+\\)(?=--\\([0-9.-]+,[0-9.-]+\\), linewidth\\([0-9.-]+\\))";
    private static final String toMatchLineDot2="(?<=--)\\([0-9.-]+,[0-9.-]+\\)(?=, linewidth)";//"(?<=draw\\(\\([0-9.-]+,[0-9.-]+\\)--)\\([0-9.-]+,[0-9.-]+\\)(?=, linewidth\\([0-9.-]+\\))";
    private static final String toMatchWidth="(?<=linewidth\\()[0-9.]+(?=\\))";
    private static final String path="src//main//resources//data.txt";
    private static final String toMatchBuildingName="(?<=label\\(\\\"\\$)[\\w]*(?=\\$\\\")";


    public static Graph manage() throws FileNotFoundException {

    Scanner scanner=new Scanner(new File(path));
    Graph graph=new Graph();
    HashMap<String, dot> allDots=new HashMap<String, dot>();
    dot[] dotsList=new dot[Graph.MaxNumOfDots];
    edge[][] edges=new edge[Graph.MaxNumOfDots][Graph.MaxNumOfDots];

    String now=scanner.nextLine();
    dot nowDot=null;
    boolean justGetOne=false;
    //String dotPosition="";
    int num=0;

    while (scanner.hasNextLine()&& !now.contains("/* dots and labels */")){now=scanner.nextLine();};
    for(; scanner.hasNextLine()&&!now.contains( "/* end of picture */");now=scanner.nextLine()) {


        if(justGetOne&&now.contains("label")){
            MyMatcher matcher=new MyMatcher(now);
            nowDot.setName(matcher.theFirst(toMatchBuildingName));
            justGetOne=false;
            nowDot.setIndex(num);
            dotsList[num]=nowDot;
            num++;
        }
        else if(now.contains("dot")){
            MyMatcher matcher=new MyMatcher(now);
            String dotPosition= matcher.theFirst(toMatchDotName);
            String dotX=matcher.theFirst(toMatchDotX);
            String dotY=matcher.theFirst(toMatchDotY);

            if(dotPosition==null||dotX==null||dotY==null)continue;

            nowDot=new dot(dotPosition, Double.parseDouble(dotX), Double.parseDouble(dotY));
            allDots.put(dotPosition,nowDot);
            justGetOne=true;
        }
    }
    scanner.close();
    scanner=new Scanner(new File(path));
    now=scanner.nextLine();

    while (scanner.hasNextLine()&& !now.contains("/* draw figures */")){now=scanner.nextLine();};
    for(;scanner.hasNextLine()&&!now.contains( "/* dots and labels */");now=scanner.nextLine()){

        if(now.contains("draw")){
            MyMatcher matcher=new MyMatcher(now);
           String dotPosition1=matcher.theFirst(toMatchLineDot1);
           String dotPosition2=matcher.theFirst(toMatchLineDot2);
           String widthStr=matcher.theFirst(toMatchWidth);
           if(dotPosition2==null||dotPosition1==null||widthStr==null)continue;
           Double width=Double.parseDouble(widthStr);

           if(!allDots.containsKey(dotPosition1)||!allDots.containsKey(dotPosition2)){System.out.println("Wrong");continue;}
           int index1=allDots.get(dotPosition1).getIndex();
           int index2=allDots.get(dotPosition2).getIndex();

            edges[index1][index2]=new edge(allDots.get(dotPosition1),allDots.get(dotPosition2));
            edges[index2][index1]=new edge(allDots.get(dotPosition2),allDots.get(dotPosition1));
           if(width<5&&width>3){edges[index1][index2].setBikeOk(true);edges[index2][index1].setBikeOk(true);}
        }
    }
;
    return graph;
    }

}
