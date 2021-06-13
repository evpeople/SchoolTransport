package com.CampusNavigation.Gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.CampusNavigation.Log.LOG;
import com.example.campus_navigation1.R;

import java.io.IOException;


public class CampusMapActivity extends AppCompatActivity {
    private MainLayout mainLayout;
    private InformationLayout informationlayout;
    private RelativeLayout layout_back;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LOG.setLOG(CampusMapActivity.this, "log.txt");
        LOG.d("great");
        setContentView(R.layout.main);
        layout_back = (RelativeLayout) findViewById(R.id.main);

        try {
            mainLayout = new MainLayout(CampusMapActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        layout_back.addView(mainLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.infoItem:
                informationlayout=new InformationLayout(CampusMapActivity.this,mainLayout);
                layout_back.removeView(mainLayout);
                layout_back.addView(informationlayout);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public MainLayout getMainLayout() {
        return mainLayout;
    }
}