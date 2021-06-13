package com.CampusNavigation.Gui;

import androidx.appcompat.app.AppCompatActivity;

import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.GraphImport.graphManage.graphManager;
import com.CampusNavigation.Gui.CoolLinearLayout;
import com.CampusNavigation.Gui.MainLayout;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Map;
import com.example.campus_navigation1.R;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.IOException;

public class informationActivity extends AppCompatActivity {
    private MainLayout mainLayout;
    private CoolLinearLayout infoLayout;
    private MapLayout mapLayout;
    private EditText BuildingName;
    private Map map;
    private int floor;

    informationActivity(MainLayout mainLayout){
    this.mainLayout=mainLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        infoLayout=new CoolLinearLayout(this);
        setMap(0);
    }
    private Building nowSpecificBuilding(){
        return mainLayout.choosedBuilding();
    }
    private void setMap(int floor){
        Graph graph=getGraph(nowSpecificBuilding().map.filePath);
        mapLayout = new MapLayout(this,graph);
        LinearLayout.LayoutParams params_map = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //if(studentView!=null&&studentView.rightNowPosition().getCurrentMap()==map)mapLayout.SetStudentView(studentView);
        infoLayout.addView(mapLayout, params_map);//*
        this.map=map;
       // informationlayout.;
    }
    private Graph getGraph(String path){
        AssetManager assetManager=getAssets();
        Graph graph=null;
        try {
            graph= graphManager.manage(assetManager,path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

}