package com.CampusNavigation.Gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.text.Layout;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.GraphImport.graphManage.graphManager;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Logic;
import com.CampusNavigation.Map.Map;
import com.CampusNavigation.Map.SpecificBuild;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Student;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class MainLayout extends CoolLinearLayout {
    private static final String Path1="campus1.txt";
    private static final String Path2="campus2.txt";
    private static final String strategyInfo[]={"最短距离","最短时间","途经最短(待确认)","自行车"};
    private static final String strategy[]={"a","b","c","d"};
    private int strategyIndex=0;
    private SpecificBuild specificBuild=null;
    private int floor=-1;//-1表示在校区地图
    private Map map;
    private Map campus1;
    private Map campus2;
    private Student student;
    private StudentView studentView=null;
    private Button startButton;
    private PosView stuPos;
    private MapLayout mapLayout=null;
    private TextView touchedBuildingText;
    private TextView targetBuildingText;
    private Button setTargetBuildingButton;
    private Button switchCampus;
    private Button switchStrategy;
    private Button setNowPosition;
    private Button getCost;
    private Button upStairButton;
    private Button downStairButton;
    private BuildingView touchedBuilding;
    private BuildingView targetBuilding;
    private EditText searchWidth;
    private EditText searchWindow;
    private Logic logic;
    private TextView stairInfo;
    private Queue<Building> queue=new LinkedList<>();
    private boolean byBus=true;

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    public MainLayout(Context context) throws IOException {
        super(context);
        campus1=new Map(getGraph(Path1),context.getAssets());
        campus2=new Map(getGraph(Path2),context.getAssets());
        //布局背景
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setBackgroundColor(Color.WHITE);
        //editable.
        LinearLayout searchLayout1=newLinearLayout(Color.YELLOW);
        addButton("搜索附近",searchLayout1).setOnClickListener((e)->{
            //...
        });
        searchWidth=addEdit("搜索范围",searchLayout1);
        LinearLayout searchLayout2=newLinearLayout(Color.YELLOW);
        logic=new Logic(campus1,campus2,context);
        addButton("搜索此地",searchLayout2).setOnClickListener((e)->{
            HashSet<Building>hashSet=logic.findPhyAddr(searchWindow.getText().toString());
            if(hashSet!=null)for(Building building:hashSet){
                searchWindow.setText(building.nameOfBuildingInEnglish);
            }
        });
        searchWindow=addEdit("地址搜索",searchLayout2);
        //按键
        LinearLayout ButtonLayout1=newLinearLayout(Color.WHITE);
        startButton = addButton("点击开始运动",ButtonLayout1);//*
        setTargetBuildingButton =addButton("设为终点",ButtonLayout1);
        setNowPosition=addButton("设为当前位置",ButtonLayout1);
        LinearLayout ButtonLayout2=newLinearLayout(Color.WHITE);
        switchCampus=addButton("切换校区",ButtonLayout2);
        switchStrategy=addButton("策略:"+strategyInfo[0],ButtonLayout2);
        getCost=addButton("耗费",ButtonLayout2);
        LinearLayout ButtonLayout3=newLinearLayout(Color.WHITE);
        upStairButton=addButton("上楼/进楼",ButtonLayout3);
        downStairButton=addButton("下楼",ButtonLayout3);
        stairInfo=addText("楼层 ",ButtonLayout3);
        LinearLayout TextShowLayout1=newLinearLayout(Color.YELLOW);
        targetBuildingText =addText("目的地        ",TextShowLayout1);
        LinearLayout TextShowLayout2=newLinearLayout((Color.WHITE));
        touchedBuildingText=addText("选中位置       ",TextShowLayout2);
        //地图
        switchToMap(campus1);
        //学生
        this.student=new Student(null);
        if(studentView==null)studentView=new StudentView(getContext(),student);
        studentView.setCommandClickView(()->{//运动前
            if(studentView.rightNowPosition().getCurrentMap()!=map){
                switchToMap(studentView.rightNowPosition().getCurrentMap());
            }
        },startButton,()->{//运动中切换地图时
            if(studentView.rightNowPosition().getCurrentMap().getParent()==null&&map.getParent()==null)
                Toast.makeText(context,"travel to the other campus!",Toast.LENGTH_SHORT).show();
            mapLayout.deleteStudentView();
            floor=studentView.rightNowPosition().getNowFloor();
            switchToMap(studentView.rightNowPosition().getCurrentMap());
            setStudentPosition(studentView.rightNowPosition().getNowBuilding());
        });
        setStudentPosition(map.getBuilding(0));
        //监听终点设置
        setTargetBuildingButton.setOnClickListener((e)->{
            if(touchedBuilding ==null)return;
            targetBuilding = touchedBuilding;
            if(!strategy[strategyIndex].equals("c")){studentView.setTargetBuilding(touchedBuilding.building(map),strategy[strategyIndex],byBus);}
            else {queue.add(targetBuilding.building(map));}
            targetBuildingText.setText("[目的地]："+ targetBuilding.dot().getPosition()+"("+strategyInfo[strategyIndex]+")");

        });
        //监听切换校区
        switchCampus.setOnClickListener((e)->{
            Map temp=null;
            if(map==campus1)temp=campus2;
            else if(map==campus2)temp=campus1;
            else temp=map.getParent();
            if(temp!=null) switchToMap(temp);
        });
        //监听导航策略
        switchStrategy.setOnClickListener((e)->{
            if(strategy[strategyIndex].equals("c")&&switchStrategy.getText().equals(strategyInfo[strategyIndex])){
                switchStrategy.setText("途经最短已确认");
                student.setTargetBuilding(queue,byBus);
            }
            if( strategy[strategyIndex].equals("c")){queue.clear();}
            strategyIndex=(strategyIndex+1)%4;
            switchStrategy.setText(strategyInfo[strategyIndex]);
        });
        //监听耗费获取
        getCost.setOnClickListener((e)->{
            try {
                String ans="52.31";
                if(strategy[strategyIndex].equals("c")){
                   ans=student.getCostToTarget(queue,byBus);
                }else  ans=student.getCostToTarget(touchedBuilding.building(map),strategy[strategyIndex],byBus);
                 getCost.setText("到"+touchedBuilding.dot().getPosition()+"需"+ans);
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                cloneNotSupportedException.printStackTrace();
            }
        });
        //监听设置当前位置
        setNowPosition.setOnClickListener((e)->{
            setStudentPosition(touchedBuilding.building(map));
        });
        //监听上下楼
        upStairButton.setOnClickListener((e)->{
                if(floor>=0&&floor<specificBuild.floor){
                    floor++;
                    switchToMap(specificBuild.getMapOfFloor(floor));
                }else Toast.makeText(context,"the building ["+specificBuild.nameOfBuildingInEnglish+"] has no floor "+(floor+1),Toast.LENGTH_SHORT).show();
        });
        downStairButton.setOnClickListener((e)->{
            if(floor>=2){
                floor--;
                switchToMap(specificBuild.getMapOfFloor(floor));
            }else Toast.makeText(context,"the building ["+specificBuild.nameOfBuildingInEnglish+"] has no floor "+(floor-1),Toast.LENGTH_SHORT).show();
        });
        //实时展示学生位置
        stuPos=new PosView(context, studentView);
        stuPos.setRight(5);
        //addView(stuPos);//*
    }//end Main


    private void switchToMap(Map map){
        if(studentView!=null&& studentView.rightNowPosition().getCurrentMap()==this.map)mapLayout.deleteStudentView();
        if(mapLayout!=null)removeView(mapLayout);
        Graph graph=getGraph(map.filePath);
        mapLayout = new MapLayout(getContext(),graph,new onBuildingViewTouched(){
            @Override
            public void run() {
                super.run();
                touchedBuilding = this.buildingView;
                touchedBuildingText.setText("  [选中位置]："+ touchedBuilding.dot().getPosition()+"("+touchedBuilding.dot().getType().toString()+")");
                if(touchedBuilding.building(map) instanceof SpecificBuild){
                    floor=0;
                    specificBuild=(SpecificBuild) touchedBuilding.building(map);
                }
            }
        });
        LinearLayout.LayoutParams params_map = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if(studentView!=null&&studentView.rightNowPosition().getCurrentMap()==map)mapLayout.SetStudentView(studentView);
        addView(mapLayout, params_map);//*
        this.map=map;
        if(map.getParent()==null){floor=-1;stairInfo.setText("在校园");}
        else {
            stairInfo.setText("在["+specificBuild.nameOfBuildingInEnglish+"]第"+floor+"楼");
        }
    }

    private Graph getGraph(String path){
        AssetManager assetManager=getContext().getAssets();
        Graph graph=null;
        try {
            graph= graphManager.manage(assetManager,path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public void setStudentPosition(Building building) {
        this.student.setPosition(new Position(building));
        if(studentView.rightNowPosition().getCurrentMap()==map){
            mapLayout.SetStudentViewPosition(studentView);
        }
    }
    public Building choosedBuilding(){
        return touchedBuilding.building(map);
    }
    public boolean switchByBus(){
        byBus=!byBus;
        return byBus;
    }
    public Queue<Building> getQueue(){
        return queue;
    }

}
