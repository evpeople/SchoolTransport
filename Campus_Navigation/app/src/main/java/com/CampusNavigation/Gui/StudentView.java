package com.CampusNavigation.Gui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.campus_navigation1.R;

public   class StudentView extends View {
    private RelativeLayout.LayoutParams StuParams;
    private double vWalk=100.0;
    private double vBike=200.0;
    private ObjectAnimator animatorX;
    private ObjectAnimator animatorY;

    public StudentView(Context context) {
        super(context);
        setBackgroundResource(R.drawable.student1);
        StuParams = new  RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT,  RelativeLayout.LayoutParams.MATCH_PARENT);
        StuParams.height=100;
        StuParams.width=100;

       // setOnClickListener((e)->{
            //
     //   });

    }
    public void moveTo(int x, int y){
        //if buzai tongyiceng


        int t=(int)(1000*Math.sqrt((x-getX())*(x-getX())+(y-getY())*(y-getY()))/vWalk);
        animatorX = ObjectAnimator.ofFloat(this,"translationX",x).setDuration(t);
        animatorY=ObjectAnimator.ofFloat(this, "translationY",y) .setDuration(t);
        animatorX.start();
        animatorY.start();
    }
    private void stopMove(){
        animatorX.cancel();
        animatorY.cancel();
    }
    public RelativeLayout.LayoutParams getStuParams() {
            return StuParams;
    }

    public void setStart(View v){
        v.setOnClickListener((e)->{
            moveTo(250,250);
        });
    }
    public void setPause(View v){
        v.setOnClickListener((e)->{
            stopMove();
        });
    }

}