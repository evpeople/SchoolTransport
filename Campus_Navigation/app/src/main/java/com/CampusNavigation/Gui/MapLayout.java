package com.CampusNavigation.Gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;

import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.Gui.PosView;

public class MapLayout extends LinearLayout {
    private Button startButton;
    private Button pauseButton;
    private PosView stuPos;
    private MapBuildings mapBuildings;
    private StudentView stu=null;

    @SuppressLint("ResourceAsColor")
    public MapLayout(Context context, Graph graph) {
        super(context);
        setClickable(false);
        setOrientation(VERTICAL);
        startButton = addButton("开始");//*
        pauseButton = addButton("暂停");//*
        mapBuildings = new MapBuildings(context, graph);

        LinearLayout.LayoutParams params_map = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mapBuildings.setRight(100);
        stu = mapBuildings.getStu();
        stu.setStart(startButton);
        stu.setPause(pauseButton);

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


}
