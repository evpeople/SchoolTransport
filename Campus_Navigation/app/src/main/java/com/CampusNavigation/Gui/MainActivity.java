package com.CampusNavigation.Gui;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.GraphImport.graphManage.graphManager;
import com.CampusNavigation.Student.Student;
import com.example.campus_navigation1.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;


public    class MainActivity extends AppCompatActivity {
    public static Student student=new Student();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AssetManager assetManager=getAssets();
        Graph graph=new Graph();
        try {
            graph= graphManager.manage(assetManager,null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      RelativeLayout layout_back = (RelativeLayout) findViewById(R.id.main);

        MapLayout layout_map=new MapLayout(MainActivity.this,graph);
   layout_back.addView(layout_map);
    }
}