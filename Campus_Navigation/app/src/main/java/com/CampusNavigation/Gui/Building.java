package com.CampusNavigation.Gui;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.CampusNavigation.GraphImport.Graph.Dot;
import com.example.campus_navigation1.R;


public class Building extends View {
    private RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    private Dot dot;

    public Building(Context context, Dot dot) {
        super(context);
        params.leftMargin = (int) (dot.xg - dot.rg);
        params.topMargin = (int) (dot.yg - dot.rg);
        params.width = (int) dot.rg * 2;
        params.height = (int) dot.rg * 2;
        this.dot = dot;
        switch (dot.getType()) {
            case canteen:
                setBackgroundResource(R.drawable.label2);
                break;
            case crossing:
                break;
        }

    }

    public RelativeLayout.LayoutParams getParams() {
        return params;

    }

    public float getR() {
        return (float) dot.rg;
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(e.getAction()==MotionEvent.ACTION_DOWN){
            if((e.getX()-dot.rg)*(e.getX()-dot.rg)+(e.getY()-dot.rg)*(e.getY()-dot.rg)<dot.rg*dot.rg)
           OnClick(e);
        }
        return super.onTouchEvent(e);
    }
    private void OnClick(MotionEvent event) {
        Toast.makeText(getContext(), event.getX() + " " + event.getY(), Toast.LENGTH_SHORT).show();
    }


}
