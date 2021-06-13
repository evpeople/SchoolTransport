package com.CampusNavigation.Gui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.CampusNavigation.Log.LOG;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Path;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Student;
import com.example.campus_navigation1.R;

import java.util.Queue;

public   class StudentView extends View {
    private int r=32;
    private double vWalk=200.0;
    private double vBike=500.0;
    private ObjectAnimator animatorX;
    private ObjectAnimator animatorY;
    private Student student;
    private Building  toReach;
    public Position rightNowPosition(){
        return student.getPosition();
    };

    public StudentView(Context context,Student student) {
        super(context);
        setBackgroundResource(R.drawable.student1);
        toReach=null;
        this.student=student;
        student.view=this;
    }
    private void setAnimateMoveTo(int x, int y,double v) {
        int t = (int) (1000 * Math.sqrt((x - getX()) * (x - getX()) + (y - getY()) * (y - getY())) / v);//haomiao
        animatorX = ObjectAnimator.ofFloat(this, "translationX", x-r/2).setDuration(t);
        animatorY = ObjectAnimator.ofFloat(this, "translationY", y-r).setDuration(t);

    }

    public void startMove(Runnable AfterMove,Runnable WhenSwitchMap) {
       if(toReach==null&&!target().isEmpty()){
           rightNowPosition().setPath(target().peek());
           toReach=rightNowPosition().getPath().getEnd();
       }//一旦停止运动toReach为null，一旦开始运动非null，开始运动后会调用一次这里

        if(toReach!=null){//运动
            double v=vWalk;
            if(student.isGoByBike()&&rightNowPosition().getPath().isBike())v=vBike;
            v*=rightNowPosition().getPath().CrowdDegree();
            if(v<0)v=12.0;
            setAnimateMoveTo((int)toReach.mathX,(int)toReach.mathY,v);
            animatorX.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    rightNowPosition().setOnBuilding(false);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    rightNowPosition().setOnBuilding(true);
                    target().poll();
                    rightNowPosition().setNowBuilding(toReach);
                    if(target().isEmpty()){
                        toReach=null;
                        AfterMove.run();
                        rightNowPosition().setX(getX());
                        rightNowPosition().setY(getY());
                        LOG.u("Suceecefully move to "+rightNowPosition().getNowBuilding().nameOfBuildingInEnglish);
                       // Toast.makeText(getContext(),rightNowPosition().getNowBuilding().mathX+" "+getX(),Toast.LENGTH_SHORT).show();
                    }
                     else{
                        LOG.u(" moved to "+rightNowPosition().getNowBuilding().nameOfBuildingInEnglish);
                         if(target().peek().getStart().map!=rightNowPosition().getCurrentMap()){
                             rightNowPosition().setNowBuilding(target().peek().getStart());
                             WhenSwitchMap.run();
                         }
                         rightNowPosition().setPath(target().peek());
                         toReach=rightNowPosition().getPath().getEnd();

                     }
                    startMove(AfterMove,WhenSwitchMap);
                }
            });
            animatorX.start();animatorY.start();
        }
    }

    private void stopMove(){
        animatorY.removeAllListeners();
        animatorX.removeAllListeners();
        animatorX.cancel();
        animatorY.cancel();
        toReach=null;
        rightNowPosition().setX(getX());
        rightNowPosition().setY(getY());
        LOG.u("stopped at half way between "+rightNowPosition().getPath().getStart()+" and "+rightNowPosition().getPath().getEnd());
    }
    public RelativeLayout.LayoutParams getStuParams(int size) {
        this.r=size;
        RelativeLayout.LayoutParams StuParams;
        StuParams = new  RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT,  RelativeLayout.LayoutParams.MATCH_PARENT);
        StuParams.height=r;
        StuParams.width=r;
            return StuParams;
    }


    public void setCommandClickView(Runnable beforeMove,View v,Runnable WhenSwitchMap){
        v.setOnClickListener((e)->{
            if(animatorX!=null&& animatorX.isRunning()){
                stopMove();
                if(v instanceof Button)((Button)v).setText("已停止运动");
            }
            else {
                beforeMove.run();
                startMove(()->{if(v instanceof Button)((Button)v).setText("点击开始运动");},WhenSwitchMap);
                if(v instanceof Button)((Button)v).setText("正在运动");
            }
        });
    }

    public void setTargetBuilding(Building targetBuilding,String strategy,boolean byBus) {
        student.setTargetBuilding(targetBuilding,strategy,byBus);
    }
    private Queue<Path> target(){return student.pathsToGo;}

    public void reSetPosition(){
        setX((float) (rightNowPosition().getX()-r/2));
        setY((float) (rightNowPosition().getY()-r));
    }
}

