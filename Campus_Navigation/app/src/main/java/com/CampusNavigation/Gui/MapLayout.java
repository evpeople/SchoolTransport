package com.CampusNavigation.Gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.Map.Map;

public class MapLayout extends LinearLayout {
    private  Map map;
    private Button startButton;
    private Button pauseButton;
    private PosView stuPos;
    private MapBuildings mapBuildings;
    private StudentView stu=null;
    private TextView touchedBuildingText;
   // private TextView startBuildingText;
    private TextView endBuildingText;
    //private Button setStartButton;
    private Button setEndButton;

    private  Building touchedBuilding;
    private Building startBuilding;
    private  Building endBuilding;


    @SuppressLint("ResourceAsColor")
    public MapLayout(Context context, Graph graph, Map map) {
        super(context);
        this.map=map;
        setClickable(false);
        setOrientation(VERTICAL);
        startButton = addButton("开始");//*
        pauseButton = addButton("暂停");//*
        //setStartButton=addButton("设为起点");
        setEndButton =addButton("设为终点");
        //startBuildingText=addText("start");
        endBuildingText=addText("end");
        touchedBuildingText=addText("text");
        mapBuildings = new MapBuildings(context, graph);



        LinearLayout.LayoutParams params_map = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mapBuildings.setRight(100);

        stu = mapBuildings.getStu();
        stu.setStart(startButton);
        stu.setPause(pauseButton);
        //setStartButton.setOnClickListener((e)->{if(touchedBuilding==null)return;startBuilding=touchedBuilding;startBuildingText.setText(startBuilding.getDot().getPosition());});
        setEndButton.setOnClickListener((e)->{
            if(touchedBuilding==null)return;
            endBuilding=touchedBuilding;endBuildingText.setText(endBuilding.getDot().getPosition());
            stu.setTargetBuilding(touchedBuilding.getMapBuilding(map));
        });

        stuPos=new PosView(context,stu);
        stuPos.setRight(5);

        addView(stuPos);//*
        addView(mapBuildings, params_map);//*

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

    public void setTouchedBuilding(Building touchedBuilding) {
        this.touchedBuilding = touchedBuilding;
        this.touchedBuildingText.setText(touchedBuilding.getDot().getPosition());
    }
}
