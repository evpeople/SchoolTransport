package com.CampusNavigation.Gui;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.GraphImport.graphManage.graphManager;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Map;
import com.CampusNavigation.Map.SpecificBuild;

import java.io.IOException;

public    class InformationLayout extends CoolLinearLayout {
    private MainLayout mainLayout;
    private CoolLinearLayout infoLayout;
    private MapLayout mapLayout;
    private EditText BuildingName;
    private Map map;
    private int floor=1;

    public InformationLayout(Context context,MainLayout main) {
        super(context);
        mainLayout=main;
        if(specificBuild()!=null){
            this.map=specificBuild().getMapOfFloor(floor);
            switchToMap(map);
        }
    }
    private Building TheBuilding(){
        return mainLayout.choosedBuilding();
    }
    private SpecificBuild specificBuild(){
        if(TheBuilding() instanceof SpecificBuild){
            return (SpecificBuild)TheBuilding();
        }else return null;
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

    private void switchToMap(Map map){
       // if(studentView!=null&& studentView.rightNowPosition().getCurrentMap()==this.map)mapLayout.deleteStudentView();
        if(mapLayout!=null)removeView(mapLayout);
        Graph graph=getGraph(map.filePath);
        mapLayout = new MapLayout(getContext(),graph);
        LinearLayout.LayoutParams params_map = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

       // if(studentView!=null&&studentView.rightNowPosition().getCurrentMap()==map)mapLayout.SetStudentView(studentView);
        addView(mapLayout, params_map);//*
        this.map=map;
    }
}
