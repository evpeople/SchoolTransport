package com.CampusNavigation.GraphImport.graphManage;

import android.content.res.AssetManager;

import com.CampusNavigation.GraphImport.Graph.*;
import com.CampusNavigation.GraphImport.myTools.MyMatcher;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

public abstract class graphManager {
    private static final String  toMatchDotName="(?<=dot\\()\\([0-9.-]+,[0-9.-]+\\)(?=,dotstyle\\);)";//ok
    private static final String toMatchDotX="(?<=dot\\(\\()[0-9.-]+(?=,[0-9.-]+\\),dotstyle\\);)";//ok
    private static final String toMatchDotY="(?<=dot\\(\\([0-9.-]{1,50},)[0-9.-]+(?=\\),dotstyle\\);)";//"(?<=dot\\(\\([0-9.-]+,)[0-9.-]+(?=\\),dotstyle\\);)";//ok
    private static final String toMatchLineDot1="(?<=draw\\()\\([0-9.-]+,[0-9.-]+\\)(?=--\\([0-9.-]{1,50},[0-9.-]{1,50}\\), linewidth\\([0-9.-]+\\))";//ok
    private static final String toMatchLineDot2="(?<=--)\\([0-9.-]+,[0-9.-]+\\)(?=, linewidth)";//"(?<=draw\\(\\([0-9.-]+,[0-9.-]+\\)--)\\([0-9.-]+,[0-9.-]+\\)(?=, linewidth\\([0-9.-]+\\))";//ok
    private static final String toMatchWidth="(?<=linewidth\\()[0-9.]+(?=\\))";//ok
    private static final String path="src//main//resources//data.txt";
    private static final String outPutPath="src//main//resources//graph.ser";
    private static final String toMatchBuildingName="(?<=label\\(\\\"\\$)[\\w]*(?=\\$\\\")";//ok
    private static final String toMatchArrowDot1="(?<=draw\\()\\([0-9.-]+,[0-9.-]+\\)(?=--\\([0-9.-]+,[0-9.-]+\\), linewidth\\([0-9.-]+\\)[\\s\\w+]*,EndArrow\\([0-9.]+\\))";
    private static final String toMatchArrowDot2="(?<=--)\\([0-9.-]+,[0-9.-]+\\)(?=, linewidth\\([0-9.-]+\\)[\\s\\w+]*,EndArrow\\([0-9.]+\\))";
    private static final String toMatchCirclePoint="(?<=draw\\(circle\\()\\([0-9.-]+,[0-9.-]+\\)(?=, [0-9.-]+\\), line)";
    private static final String toMatchLength="(?<=,\\s)[0-9.-]+(?=\\),)";//"(?<=circle\\(\\([0-9.-]+,[0-9.-]+\\),\\s)[0-9.-]+(?=\\),)";
    private static final String toMatchBuildingType1="(?<=linetype\\(\\\"[\\s0-9.]{0,50}\\\"\\) \\+ )[\\w]+(?=\\))";
    private static final String toMatchBuildingType2="(?<=linewidth\\([\\s0-9.]{0,50}\\) \\+ )[\\w]+(?=\\))";

    public static Graph manage(AssetManager assetManager) throws IOException {
        Scanner scanner;
   if(assetManager==null)scanner=new Scanner(new File(path));
   else scanner=new Scanner(new BufferedInputStream(assetManager.open("data.txt")));
    HashMap<String, Dot> allDots=new HashMap<String, Dot>();
    Dot[] dotsList=new Dot[Graph.MaxNumOfDots];
    Edge[][] edges=new Edge[Graph.MaxNumOfDots][Graph.MaxNumOfDots];
    HashMap<String,String> GuiPoints=new HashMap<>();

    /*第一次读取*/
    String now=scanner.nextLine();
    Dot nowDot=null;
    boolean justGetOne=false;
    //String dotPosition="";
    int num=0;

    while (scanner.hasNextLine()&& !now.contains("/* draw figures */")){now=scanner.nextLine();};

    for(; scanner.hasNextLine()&&!now.contains( "/* dots and labels */");now=scanner.nextLine()){
        if(now.contains("EndArrow")) {
            MyMatcher matcher = new MyMatcher(now);
            GuiPoints.put(matcher.theFirst(toMatchArrowDot2),matcher.theFirst(toMatchArrowDot1));

        }
    }

    for(; scanner.hasNextLine()&&!now.contains( "/* end of picture */");now=scanner.nextLine()) {
            if(now.contains("/*")&&now.contains("*/"))continue;

        if(justGetOne&&now.contains("label")){
            MyMatcher matcher=new MyMatcher(now);
            nowDot.setPosition(matcher.theFirst(toMatchBuildingName));
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
            if(GuiPoints.containsKey(dotPosition))continue;

            nowDot=new Dot(dotPosition, Double.parseDouble(dotX), Double.parseDouble(dotY));
            allDots.put(dotPosition,nowDot);
            justGetOne=true;
        }
    }
    scanner.close();

    /*第二次读取*/
        if(assetManager==null)scanner=new Scanner(new File(path));
        else scanner=new Scanner(new BufferedInputStream(assetManager.open("data.txt")));
    now=scanner.nextLine();

    while (scanner.hasNextLine()&& !now.contains("/* draw figures */")){now=scanner.nextLine();};
    for(;scanner.hasNextLine()&&!now.contains( "/* dots and labels */");now=scanner.nextLine()){
        if(now.contains("/*")&&now.contains("*/"))continue;

        if(now.contains("draw")&&!now.contains("circle")&&!now.contains("EndArrow")){//道路
            MyMatcher matcher=new MyMatcher(now);
           String dotPosition1=matcher.theFirst(toMatchLineDot1);
           String dotPosition2=matcher.theFirst(toMatchLineDot2);
           String widthStr=matcher.theFirst(toMatchWidth);
           if(dotPosition2==null||dotPosition1==null||widthStr==null)continue;
           Double width=Double.parseDouble(widthStr);

           if(!allDots.containsKey(dotPosition1)||!allDots.containsKey(dotPosition2)){System.out.println("Wrong");continue;}
           int index1=allDots.get(dotPosition1).getIndex();
           int index2=allDots.get(dotPosition2).getIndex();

            edges[index1][index2]=new Edge(allDots.get(dotPosition1),allDots.get(dotPosition2));
            edges[index2][index1]=new Edge(allDots.get(dotPosition2),allDots.get(dotPosition1));
           if(now.contains("qqccqq")){edges[index1][index2].setBikeOk(true);edges[index2][index1].setBikeOk(true);}
           else if(now.contains("qqzzff")){edges[index1][index2].setBikeOk(false);edges[index2][index1].setBikeOk(false);}

           edges[index1][index2].setDegreeOfCongestion((float) (width/5.2));
           edges[index2][index1].setDegreeOfCongestion((float) (width/5.2));
        }
        else if(now.contains("draw")&&now.contains("EndArrow")){//建筑
            MyMatcher matcher=new MyMatcher(now);
            String dot1=matcher.theFirst(toMatchArrowDot1);
            String dot2=matcher.theFirst(toMatchArrowDot2);
            matcher=new MyMatcher(dot2);
            allDots.get(dot1).xg=Double.parseDouble(matcher.theFirst("(?<=\\()[0-9.-]+(?=,[0-9.-]+\\))"));
            allDots.get(dot1).yg=Double.parseDouble(matcher.theFirst("(?<=\\([0-9.-]{1,50},)[0-9.-]+(?=\\))"));
            allDots.get(dot1).setRg();
        }
        else if(now.contains("draw")&&(now.contains("linetype")||now.contains("linewidth"))&&now.contains("circle")){//路口
            MyMatcher matcher=new MyMatcher(now);
            Dot theDot;
            String type="";
            if(now.contains("linetype")){
                theDot=allDots.get(matcher.theFirst(toMatchCirclePoint));
                if(theDot==null)continue;
                type=matcher.theFirst(toMatchBuildingType1);
                if(type!=null)setDotType1(theDot,type);
                theDot.setRg( Double.parseDouble(matcher.theFirst(toMatchLength)));
            }else if(now.contains("linewidth")){
                theDot=allDots.get(GuiPoints.get(matcher.theFirst(toMatchCirclePoint)));
                if(theDot==null)continue;
                type=matcher.theFirst(toMatchBuildingType2);
                if(type!=null)setDotType2(theDot,type);
                theDot.setRg( Double.parseDouble(matcher.theFirst(toMatchLength)));
            }
        }

    }
        Graph graph=new Graph(num,dotsList,edges);
//        toPutOutGraph(graph,outPutPath);
        return graph;
    }

   private static void setDotType1(Dot theDot, String type){

        switch (type){
            case "ffccww":theDot.setType(BuildingType.buildingCrossing); break;
            case "aqaqaq":theDot.setType(BuildingType.car);break;
            case "ffwwqq":theDot.setType(BuildingType.bus);break;
            case "ffwwzz":theDot.setType(BuildingType.runway);break;
            case "zzwwff":theDot.setType(BuildingType.soccer);break;
            /////
            case "ttffcc":theDot.setType(BuildingType.crossing);break;
            /////
            case "wwffqq":theDot.setType(BuildingType.exit);break;
            /////
            default: theDot.setType(BuildingType.buildingCrossing);break;
        }
    }

    private static void setDotType2(Dot theDot, String type){

        switch (type){
            case "blue":theDot.setType(BuildingType.lake);break;
            case "cczzff":theDot.setType(BuildingType.studentCenter);break;
            case "ffwwzz":theDot.setType(BuildingType.canteen);break;
            case "ffwwqq":theDot.setType(BuildingType.tree);break;
            case "ttttff":theDot.setType(BuildingType.swim);break;
            case "ttffcc":theDot.setType(BuildingType.service);break;
            case "wwffqq":theDot.setType(BuildingType.hospital);break;
            case "ffcctt":theDot.setType(BuildingType.office); break;
            case "red":theDot.setType(BuildingType.teach);break;
            case "qqzzff":theDot.setType(BuildingType.dorm);break;
            case "cqcqcq":theDot.setType(BuildingType.lab);break;
            case "ffefdv":theDot.setType(BuildingType.coffee);break;
            case "ffcqcb":theDot.setType(BuildingType.librariy);break;
            default:theDot.setType(BuildingType.Default);break;
        }
    }

    private static void toPutOutGraph(Graph graph,String path) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(graph);
        out.close();
        fileOut.close();
    }
}
