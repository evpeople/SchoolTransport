package com.CampusNavigation.Gui;

import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
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
import java.text.DecimalFormat;


public class CampusMapActivity extends AppCompatActivity {
    private MainLayout mainLayout;
    private InformationLayout informationlayout=null;
    private RelativeLayout layout_back;
    private CoolLinearLayout showingQueue=null;
    private CoolLinearLayout answerLayout=null;
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
            case R.id.answer:
                if(answerLayout==null){
                    layout_back.removeView(mainLayout);
                    answerLayout=new CoolLinearLayout(this);
                    answerLayout.setOrientation(LinearLayout.VERTICAL);
                    answerLayout.addText("附近搜索结果：",answerLayout);
                    for(Pair<Building, Double> pair:mainLayout.getARound()){
                         Button button=answerLayout.addButton(pair.first.nameOfBuildingInEnglish+" is "+new DecimalFormat("0.000").format(pair.second)+" m away.",answerLayout);
                         button.setOnClickListener((e)->{
                             mainLayout.setTouchedBuilding(pair.first);
                         });
                    }
                    answerLayout.addText("文字搜索结果：",answerLayout);
                    for(Building ans:mainLayout.getSearchAnswer()){
                        Button button=answerLayout.addButton(ans.nameOfBuildingInEnglish,answerLayout);
                        button.setOnClickListener((e)->{
                            mainLayout.setTouchedBuilding(ans);
                        });
                    }
                    layout_back.addView(answerLayout);
                }else{
                    layout_back.removeView(answerLayout);
                    layout_back.addView(mainLayout);
                    answerLayout=null;
                }
                break;
            case R.id.ByBus:
                String carType;
                if(mainLayout.switchByBus()){
                    carType="bus";
                }else carType="car";
                Toast.makeText(this,"you will travel to the other campus by "+carType,Toast.LENGTH_SHORT).show();
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