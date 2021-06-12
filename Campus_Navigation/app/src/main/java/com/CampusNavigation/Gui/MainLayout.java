package com.CampusNavigation.Gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.GraphImport.graphManage.graphManager;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Map;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Student;

import java.io.IOException;

public class MainLayout extends LinearLayout {
    private static final String Path1="campus1.txt";
    private static final String Path2="campus2.txt";
    private String strategy;
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
    private Button setNowPosition;
    private BuildingView touchedBuilding;
    private BuildingView targetBuilding;
    private EditText searchWidth;

    @SuppressLint("ResourceAsColor")
    public MainLayout(Context context) throws IOException {
        super(context);
        //editable.
        //按键
        setOrientation(HORIZONTAL);
        startButton = addButton("点击开始运动");//*
        setTargetBuildingButton =addButton("设为终点");
        switchCampus=addButton("切换校区");
        setNowPosition=addButton("设为当前位置");
        targetBuildingText =addText("target");
        touchedBuildingText=addText("touched");
        setOrientation(VERTICAL);
        //地图
        campus1=new Map(getGraph(Path1));
        campus2=new Map(getGraph(Path2));
        switchToMap(campus1);
        //学生
        this.student=new Student(null);
        if(studentView==null)studentView=new StudentView(getContext(),student);
        studentView.setCommandClickView(()->{
            if(studentView.rightNowPosition.getCurrentMap()!=map){
                try {
                    switchToMap(studentView.rightNowPosition.getCurrentMap());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },startButton,()->{
            mapLayout.deleteStudentView();
            Map temp=null;
            if(map==campus1)temp=campus2;
            else if(map==campus2)temp=campus1;
            try {
                if(temp!=null) switchToMap(temp);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            setStudentPosition(studentView.rightNowPosition.getNowBuilding());
        });
        setStudentPosition(map.getBuilding(0));
        //监听点击的building设置
        setTargetBuildingButton.setOnClickListener((e)->{
            if(touchedBuilding ==null)return;
            targetBuilding = touchedBuilding;
            targetBuildingText.setText(targetBuilding.dot().getPosition());
            studentView.setTargetBuilding(touchedBuilding.building(map));
        });
        //监听切换校区
        switchCampus.setOnClickListener((e)->{
            Map temp=null;
            if(map==campus1)temp=campus2;
            else if(map==campus2)temp=campus1;
            try {
               if(temp!=null) switchToMap(temp);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //监听设置位置
        setNowPosition.setOnClickListener((e)->{
            setStudentPosition(touchedBuilding.building(map));
        });
        //实时展示学生位置
        stuPos=new PosView(context, studentView);
        stuPos.setRight(5);
        addView(stuPos);//*
    }//end Main

    private Button addButton(String text) {
        Button button = new Button(getContext());
        LinearLayout.LayoutParams params_button1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setRight(5);
        button.setText(text);
        addView(button, params_button1);
        return button;
    }
    private TextView addText(String text){
        TextView Text =new TextView(getContext(),null);
       Text.setText(text);
       Text.setRight(6);
        addView(Text);
        return  Text;
    }

    public void setTouchedBuilding(BuildingView touchedBuilding) {
        this.touchedBuilding = touchedBuilding;
        this.touchedBuildingText.setText(touchedBuilding.dot().getPosition());
    }

    private void switchToMap(Map map) throws IOException {
        if(studentView!=null&& studentView.rightNowPosition.getCurrentMap()==this.map)mapLayout.deleteStudentView();
        if(mapLayout!=null)removeView(mapLayout);
        Graph graph=getGraph(map.filePath);
        mapLayout = new MapLayout(getContext(),graph);
        LinearLayout.LayoutParams params_map = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mapLayout.setRight(150);//参数含义？？？
        if(studentView!=null&&studentView.rightNowPosition.getCurrentMap()==map)mapLayout.SetStudentView(studentView);
        addView(mapLayout, params_map);//*
        this.map=map;
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
        this.student.position=new Position(building);
        studentView.rightNowPosition=this.student.position;
        if(studentView.rightNowPosition.getCurrentMap()==map){
            mapLayout.SetStudentViewPosition(studentView,building);
        }
    }
}
