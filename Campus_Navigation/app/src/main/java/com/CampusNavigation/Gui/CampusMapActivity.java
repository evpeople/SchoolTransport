package com.CampusNavigation.Gui;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.GraphImport.graphManage.graphManager;

import com.example.campus_navigation1.R;

import java.io.IOException;


public    class CampusMapActivity extends AppCompatActivity {
    private MainLayout mainLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AssetManager assetManager=getAssets();
        Graph graph=new Graph();
        try {
            graph= graphManager.manage(assetManager,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.main);
        RelativeLayout layout_back = (RelativeLayout) findViewById(R.id.main);

        try {
            mainLayout =new MainLayout(CampusMapActivity.this,graph);
        } catch (IOException e) {
            e.printStackTrace();
        }
        layout_back.addView(mainLayout);
    }

    public MainLayout getMainLayout() {
        return mainLayout;
    }

}