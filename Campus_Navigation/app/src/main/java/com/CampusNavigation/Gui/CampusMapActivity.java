package com.CampusNavigation.Gui;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.CampusNavigation.Gui.information.informationActivity;
import com.CampusNavigation.Log.LOG;
import com.example.campus_navigation1.R;

import java.io.IOException;


public    class CampusMapActivity extends AppCompatActivity {
    private MainLayout mainLayout;
    private informationActivity informationActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            LOG.setLOG(CampusMapActivity.this,"log.txt");
        LOG.d("great");
        setContentView(R.layout.main);
        RelativeLayout layout_back = (RelativeLayout) findViewById(R.id.main);

        try {
            mainLayout =new MainLayout(CampusMapActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        layout_back.addView(mainLayout);
    }

    public MainLayout getMainLayout() {
        return mainLayout;
    }

}