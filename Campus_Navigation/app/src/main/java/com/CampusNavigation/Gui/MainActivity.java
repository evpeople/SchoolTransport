package com.CampusNavigation.Gui;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campus_navigation1.R;


public    class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      RelativeLayout layout_back = (RelativeLayout) findViewById(R.id.main);

   MapLayout layout_map=new MapLayout(MainActivity.this);
   layout_back.addView(layout_map);
    }
}