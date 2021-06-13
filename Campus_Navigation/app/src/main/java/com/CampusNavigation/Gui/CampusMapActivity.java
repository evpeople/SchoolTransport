package com.CampusNavigation.Gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.CampusNavigation.Log.LOG;
import com.CampusNavigation.Map.Building;
import com.example.campus_navigation1.R;

import java.io.IOException;


public class CampusMapActivity extends AppCompatActivity {
    private MainLayout mainLayout;
    private InformationLayout informationlayout=null;
    private RelativeLayout layout_back;
    private CoolLinearLayout showingQueue=null;
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
                if(informationlayout==null){
                    informationlayout=new InformationLayout(CampusMapActivity.this,mainLayout);
                     layout_back.removeView(mainLayout);
                     layout_back.addView(informationlayout);
                     //informationlayout.show();
                }else {
                    layout_back.removeView(informationlayout);
                    layout_back.addView(mainLayout);
                    informationlayout=null;
                }
                break;
            case R.id.strategeCItem:
                if(showingQueue==null){
                    layout_back.removeView(mainLayout);
                    showingQueue=new CoolLinearLayout(this);
                    showingQueue.setOrientation(LinearLayout.VERTICAL);
                    for(Building building:mainLayout.getQueue()){
                        TextView Text =new TextView(this);
                        Text.setText(building.nameOfBuildingInEnglish);
                        showingQueue.addView(Text);
                    }
                    layout_back.addView(showingQueue);
                }else{
                    layout_back.removeView(showingQueue);
                    layout_back.addView(mainLayout);
                    showingQueue=null;
                }

                break;
            case R.id.ByBus:
                String carType;
                if(mainLayout.switchByBus()){
                    carType="bus";
                }else carType="car";
                Toast.makeText(this,"you will travel to the other campus by"+carType,Toast.LENGTH_SHORT).show();
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