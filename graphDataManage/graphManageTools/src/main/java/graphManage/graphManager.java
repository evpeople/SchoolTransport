package graphManage;

import Graph.*;
import myTools.MyMatcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class graphManager {
    private static final String  toMatchDotName="(?<=dot\\()\\([0-9.-]+,[0-9.-]+\\)(?=,dotstyle\\);)";//ok
    private static final String toMatchDotX="(?<=dot\\(\\()[0-9.-]+(?=,[0-9.-]+\\),dotstyle\\);)";//ok
    private static final String toMatchDotY="(?<=dot\\(\\([0-9.-]+,)[0-9.-]+(?=\\),dotstyle\\);)";//ok
    private static final String toMatchLineDot1="(?<=draw\\()\\([0-9.-]+,[0-9.-]+\\)(?=--\\([0-9.-]+,[0-9.-]+\\), linewidth\\([0-9.-]+\\))";//ok
    private static final String toMatchLineDot2="(?<=--)\\([0-9.-]+,[0-9.-]+\\)(?=, linewidth)";//"(?<=draw\\(\\([0-9.-]+,[0-9.-]+\\)--)\\([0-9.-]+,[0-9.-]+\\)(?=, linewidth\\([0-9.-]+\\))";//ok
    private static final String toMatchWidth="(?<=linewidth\\()[0-9.]+(?=\\))";//ok
    private static final String path="src//main//resources//data.txt";
    private static final String toMatchBuildingName="(?<=label\\(\\\"\\$)[\\w]*(?=\\$\\\")";//ok
    private static final String toMatchArrowDot1="(?<=draw\\()\\([0-9.-]+,[0-9.-]+\\)(?=--\\([0-9.-]+,[0-9.-]+\\), linewidth\\([0-9.-]+\\)[\\s\\w+]*,EndArrow\\([0-9.]+\\))";
    private static final String toMatchArrowDot2="(?<=--)\\([0-9.-]+,[0-9.-]+\\)(?=, linewidth\\([0-9.-]+\\)[\\s\\w+]*,EndArrow\\([0-9.]+\\))";
    private static final String toMatchCirclePoint="(?<=draw\\(circle\\()\\([0-9.-]+,[0-9.-]+\\)(?=, [0-9.-]+\\), linewidth\\([0-9.-]+\\)[\\w\\s+]+)";
    private static final String toMatchLength="(?<=,\\s)[0-9.-]+(?=\\),)";//"(?<=circle\\(\\([0-9.-]+,[0-9.-]+\\),\\s)[0-9.-]+(?=\\),)";
    public static Graph manage() throws FileNotFoundException {

    Scanner scanner=new Scanner(new File(path));
    Graph graph=new Graph();
    HashMap<String, Dot> allDots=new HashMap<String, Dot>();
    Dot[] dotsList=new Dot[Graph.MaxNumOfDots];
    Edge[][] edges=new Edge[Graph.MaxNumOfDots][Graph.MaxNumOfDots];
        ArrayList<String> GuiPoints=new ArrayList<String>();

    String now=scanner.nextLine();
    Dot nowDot=null;
    boolean justGetOne=false;
    //String dotPosition="";
    int num=0;

    while (scanner.hasNextLine()&& !now.contains("/* draw figures */")){now=scanner.nextLine();};

    for(; scanner.hasNextLine()&&!now.contains( "/* dots and labels */");now=scanner.nextLine()){
        if(now.contains("EndArrow")) {
            MyMatcher matcher = new MyMatcher(now);
            GuiPoints.add(matcher.theFirst(toMatchArrowDot2));

        }
    }

    for(; scanner.hasNextLine()&&!now.contains( "/* end of picture */");now=scanner.nextLine()) {


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
            if(GuiPoints.contains(dotPosition))continue;

            nowDot=new Dot(dotPosition, Double.parseDouble(dotX), Double.parseDouble(dotY));
            allDots.put(dotPosition,nowDot);
            justGetOne=true;
        }
    }
    scanner.close();
    scanner=new Scanner(new File(path));
    now=scanner.nextLine();

    while (scanner.hasNextLine()&& !now.contains("/* draw figures */")){now=scanner.nextLine();};
    for(;scanner.hasNextLine()&&!now.contains( "/* dots and labels */");now=scanner.nextLine()){

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
            allDots.get(dot1).yg=Double.parseDouble(matcher.theFirst("(?<=\\([0-9.-]+,)[0-9.-]+(?=\\))"));
            allDots.get(dot1).setRg();
        }
        else if(now.contains("draw")&&now.contains("linetype")&&now.contains("circle")){//路口
            MyMatcher matcher=new MyMatcher(now);
            Dot theDot=allDots.get(matcher.theFirst(toMatchCirclePoint));
            String type=matcher.theFirst("(?<=linetype\\(\\\"[\\s0-9.]*\\\"\\) \\+ )[\\w]+(?=\\))");
            switch (type){
                case "ffccww":theDot.setType(BuildingType.buildingCrossing); break;
                case "ttffcc":theDot.setType(BuildingType.crossing);break;
                case "wwffqq":theDot.setType(BuildingType.exit);break;
                default: theDot.setType(BuildingType.Default);break;
            }
            theDot.setRg( Double.parseDouble(matcher.theFirst(toMatchLength)));

        }
    }
;
    return graph;
    }

}
