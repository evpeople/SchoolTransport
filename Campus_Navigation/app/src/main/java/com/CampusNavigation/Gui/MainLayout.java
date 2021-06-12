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
    private static final String strategy[]={"最短距离","最短时间","途经最短","自行车"};
    private int strategyIndex=0;
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
    private BuildingView touchedBuilding;
    private BuildingView targetBuilding;
    private EditText searchWidth;
    private EditText searchWindow;


    @SuppressLint("ResourceAsColor")
    public MainLayout(Context context) throws IOException {
        super(context);
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
        addButton("搜索此地",searchLayout2).setOnClickListener((e)->{
            //...
        });
        searchWindow=addEdit("地址搜索",searchLayout2);
        //按键
        LinearLayout ButtonLayout1=newLinearLayout(Color.WHITE);
        startButton = addButton("点击开始运动",ButtonLayout1);//*
        setTargetBuildingButton =addButton("设为终点",ButtonLayout1);
        setNowPosition=addButton("设为当前位置",ButtonLayout1);
        LinearLayout ButtonLayout2=newLinearLayout(Color.WHITE);
        switchCampus=addButton("切换校区",ButtonLayout2);
        switchStrategy=addButton("策略:"+strategy[0],ButtonLayout2);
        LinearLayout TextShowLayout=newLinearLayout(Color.YELLOW);
        targetBuildingText =addText("目的地        ",TextShowLayout);
        touchedBuildingText=addText("选中位置       ",TextShowLayout);
        //地图
        campus1=new Map(getGraph(Path1),context.getAssets());
        campus2=new Map(getGraph(Path2),context.getAssets());
        switchToMap(campus1);
        //学生
        this.student=new Student(null);
        if(studentView==null)studentView=new StudentView(getContext(),student);
        studentView.setCommandClickView(()->{
            if(studentView.rightNowPosition.getCurrentMap()!=map){
                switchToMap(studentView.rightNowPosition.getCurrentMap());
            }
        },startButton,()->{
            mapLayout.deleteStudentView();
            Map temp=null;
            if(map==campus1)temp=campus2;
            else if(map==campus2)temp=campus1;
            if(temp!=null) switchToMap(temp);
            setStudentPosition(studentView.rightNowPosition.getNowBuilding());
        });
        setStudentPosition(map.getBuilding(0));
        //监听终点设置
        setTargetBuildingButton.setOnClickListener((e)->{
            if(touchedBuilding ==null)return;
            targetBuilding = touchedBuilding;
            targetBuildingText.setText("[目的地]："+ targetBuilding.dot().getPosition()+"("+strategy[strategyIndex]+")");
            studentView.setTargetBuilding(touchedBuilding.building(map),""+(char)(strategyIndex+'a'));
        });
        //监听切换校区
        switchCampus.setOnClickListener((e)->{
            Map temp=null;
            if(map==campus1)temp=campus2;
            else if(map==campus2)temp=campus1;
            if(temp!=null) switchToMap(temp);
        });
        //监听导航策略
        switchStrategy.setOnClickListener((e)->{
            strategyIndex=(strategyIndex+1)%4;
            switchStrategy.setText(strategy[strategyIndex]);
        });
        //监听设置当前位置
        setNowPosition.setOnClickListener((e)->{
            setStudentPosition(touchedBuilding.building(map));
        });
        //实时展示学生位置
        stuPos=new PosView(context, studentView);
        stuPos.setRight(5);
        addView(stuPos);//*
    }//end Main

    private Button addButton(String text,LinearLayout layout) {
        Button button = new Button(getContext());
        button.setGravity(Gravity.CENTER);
        button.setText(text);
        LinearLayout.LayoutParams params_button = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //params_button.setMargins(50,0,0,0);
        layout.addView(button, params_button);
        return button;
    }
    private TextView addText(String text,LinearLayout layout){
        TextView Text =new TextView(getContext(),null);
       Text.setText(text);
       Text.setRight(6);
       layout.addView(Text);
        return  Text;
    }

    public void setTouchedBuilding(BuildingView touchedBuilding) {
        this.touchedBuilding = touchedBuilding;
        this.touchedBuildingText.setText("  [选中位置]："+ touchedBuilding.dot().getPosition());
    }

    private void switchToMap(Map map){
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
    private EditText addEdit(String hint, LinearLayout layout){
        EditText editText=new EditText(getContext());
        editText.setHint(hint);
        layout.addView(editText);
        return editText;
    }
    private LinearLayout newLinearLayout(int color){
        LinearLayout top=new LinearLayout(getContext());
        top.setOrientation(HORIZONTAL);
        top.setGravity(Gravity.CENTER_VERTICAL);
        top.setBackgroundColor(color);
        addView(top);
        return top;
    }
}
