package com.CampusNavigation.Gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Path;
import com.example.campus_navigation1.R;

import java.text.DecimalFormat;


@SuppressLint("AppCompatCustomView")
public class PosView extends TextView {
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    public PosView(Context context, View target) {
        super(context);
        setText("PosView");
        setTextColor(R.color.black);
        setTextSize(8);
        setBackgroundColor(R.color.design_default_color_secondary);
        new Thread(()->{
            DecimalFormat format = new DecimalFormat("0000.00");
            for (; ; ) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String text="("+format.format(target.getX())+","+format.format(target.getY())+")";
            if(target instanceof StudentView){
                Path path=((StudentView)target).rightNowPosition().getPath();
                Building building=((StudentView)target).rightNowPosition().getNowBuilding();
                if(path!=null&&path.getStart()!=null&&path.getStart().nameOfBuildingInEnglish!=null)text+=" path start:"+path.getStart().nameOfBuildingInEnglish+" ";
                if(path!=null&&path.getEnd()!=null&&path.getEnd().nameOfBuildingInEnglish!=null)text+=" path end :"+path.getEnd().nameOfBuildingInEnglish+" ";
                if(building!=null&&building.nameOfBuildingInEnglish!=null)text+=" now building: "+building.nameOfBuildingInEnglish+" ";
            }
           setText(text);
        }}).start();
    }
}
