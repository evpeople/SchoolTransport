package com.CampusNavigation.Gui;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.CampusNavigation.GraphImport.Graph.Dot;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Map;
import com.example.campus_navigation1.R;


public class BuildingView extends View {
    private RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    private Dot dot;
    private Runnable onTouch;
    public BuildingView(Context context, Dot dot,onBuildingViewTouched onTouch) {
        super(context);
        this.onTouch=onTouch;
        onTouch.buildingView =this;
        params.leftMargin = (int) (dot.xg - dot.rg);
        params.topMargin = (int) (dot.yg - dot.rg);
        params.width = (int) dot.rg * 2;
        params.height = (int) dot.rg * 2;
        this.dot = dot;
        int image=R.drawable.label2;

        switch (dot.getType()) {
            case canteen: image=R.drawable.canteen; break;
            case teach:image=R.drawable.teach_building;break;
            case soccer:image=R.drawable.soccer_field;break;
            case runway:image=R.drawable.gym_runway;break;
            case lake:image=R.drawable.lake;break;
            case tree:image=R.drawable.forest;break;
            case bus:image=R.drawable.bus_station;break;
            case car:image=R.drawable.scheduled_station;break;
            case dorm:image=R.drawable.dorm;break;
            case librariy:image=R.drawable.library;break;
            case office:image=R.drawable.office_building;break;
            case service:image=R.drawable.service;break;
            case exit:break;
            case crossing:image=R.drawable.crossing;
                break;
            default:
        }
        setBackgroundResource(image);
    }

    public RelativeLayout.LayoutParams getParams() {
        return params;

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(e.getAction()==MotionEvent.ACTION_DOWN){
            if((e.getX()-dot.rg)*(e.getX()-dot.rg)+(e.getY()-dot.rg)*(e.getY()-dot.rg)<dot.rg*dot.rg){
                if(getContext() instanceof CampusMapActivity){
                    onTouch.run();
                }
            }
        }
        return super.onTouchEvent(e);
    }


    public Dot dot() {
        return dot;
    }

    public Building building(Map map){
        return  map.getBuilding(dot.getIndex());
    }
}

class onBuildingViewTouched implements Runnable{
    BuildingView buildingView;
    @Override
    public void run() {

    }
}
