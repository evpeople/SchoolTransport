package com.CampusNavigation.Gui;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.GraphImport.graphManage.graphManager;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Map;
import com.CampusNavigation.Map.Room;
import com.CampusNavigation.Map.SpecificBuild;

import java.io.IOException;

public    class InformationLayout extends CoolLinearLayout {
    private MainLayout mainLayout;
    private CoolLinearLayout infoLayout;
    private MapLayout mapLayout;
    private Map map;
    private int floor=1;

    public InformationLayout(Context context,MainLayout main) {
        super(context);
        mainLayout=main;
        //LinearLayout buildingInfo=newLinearLayout(Color.YELLOW);
        setOrientation(VERTICAL);
        String word[]={"cool","nice","good","great","beautiful","interesting"};
        String Information="The "+TheBuilding().nameOfBuildingInEnglish+" is a "+word[TheBuilding().index%6] +" "+TheBuilding().type+".";
        addText(Information,this);
        if(specificBuild()!=null&&specificBuild().floor>0) {
            String FloorInfo = "It has " + TheBuilding().floor + "floors. Following is the Map Of Floor";
            addText(FloorInfo,this);
        }
        if(TheBuilding()instanceof Room){
            String StairInfo="It is a room of "+((Room)TheBuilding()).getBelongToBuilding().nameOfBuildingInEnglish+" in the "+((Room)TheBuilding()).getFloorNum()+" Floor.";
            addText(StairInfo,this);
        }

        if(specificBuild()!=null&&specificBuild().floor>0){
            switchToMap(specificBuild().getMapOfFloor(1));
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
        if(mapLayout!=null)removeView(mapLayout);
        Graph graph=getGraph(map.filePath);
        mapLayout = new MapLayout(getContext(),graph,new onBuildingViewTouched());
        LinearLayout.LayoutParams params_map = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        addView(mapLayout, params_map);//*
        this.map=map;

    }
}
