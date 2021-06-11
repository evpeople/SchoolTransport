package com.CampusNavigation.Gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.Map.Map;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Student;

import java.io.IOException;

public class MainLayout extends LinearLayout {
    private  Map map;
    private Student student;
    private StudentView studentView;
    private Button startButton;
    private PosView stuPos;
    private MapLayout mapLayout;
    private TextView touchedBuildingText;
    private TextView targetBuildingText;
    private Button setTargetBuildingButton;

    private BuildingView touchedBuilding;
    private BuildingView targetBuilding;


    @SuppressLint("ResourceAsColor")
    public MainLayout(Context context, Graph graph) throws IOException {
        super(context);
        this.map=new Map(graph);
        this.student=new Student(new Position(map));
        //按键
        setOrientation(HORIZONTAL);
        startButton = addButton("点击开始运动");//*
        setTargetBuildingButton =addButton("设为终点");
        targetBuildingText =addText("target");
        touchedBuildingText=addText("touched");
        //地图
        setOrientation(VERTICAL);
        mapLayout = new MapLayout(context, graph,student);
        LinearLayout.LayoutParams params_map = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mapLayout.setRight(150);//参数含义？？？
        //学生
        studentView = mapLayout.studentView();
        studentView.setCommandClickView(startButton);
        //监听重点设置
        setTargetBuildingButton.setOnClickListener((e)->{
            if(touchedBuilding ==null)return;
            targetBuilding = touchedBuilding;
            targetBuildingText.setText(targetBuilding.dot().getPosition());
            studentView.setTargetBuilding(touchedBuilding.building(map));
        });
        //实时展示学生位置
       stuPos=new PosView(context, studentView);
        stuPos.setRight(5);

        addView(stuPos);//*
        addView(mapLayout, params_map);//*

    }

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
}
