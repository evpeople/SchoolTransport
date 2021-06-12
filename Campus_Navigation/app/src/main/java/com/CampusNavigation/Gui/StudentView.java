package com.CampusNavigation.Gui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Path;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Student;
import com.example.campus_navigation1.R;

import java.util.Queue;

public   class StudentView extends View {
    private double vWalk=100.0;
    private double vBike=200.0;
    private ObjectAnimator animatorX;
    private ObjectAnimator animatorY;
    private Student student;
    private Building  toReach;
    Position rightNowPosition;

    public StudentView(Context context,Student student) {
        super(context);
        setBackgroundResource(R.drawable.student1);

        toReach=null;
        this.student=student;
        student.view=this;
        this.rightNowPosition=student.position;
    }
    private void setAnimateMoveTo(int x, int y) {
        int t = (int) (1000 * Math.sqrt((x - getX()) * (x - getX()) + (y - getY()) * (y - getY())) / vWalk);//haomiao
        animatorX = ObjectAnimator.ofFloat(this, "translationX", x).setDuration(t);
        animatorY = ObjectAnimator.ofFloat(this, "translationY", y).setDuration(t);

    }

    public void startMove(Runnable AfterMove,Runnable WhenSwitchMap) {
       if(toReach==null&&!target().isEmpty()){
           rightNowPosition.setPath(target().peek());
           toReach=rightNowPosition.getPath().getEnd();
       }//一旦停止运动toReach为null，一旦开始运动非null，开始运动后会调用一次这里

        if(toReach!=null){//运动
            setAnimateMoveTo((int)toReach.mathX,(int)toReach.mathY);
            animatorX.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    rightNowPosition.setOnBuilding(false);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    rightNowPosition.setOnBuilding(true);
                    target().poll();
                    rightNowPosition.setNowBuilding(toReach);
                    if(target().isEmpty()){
                        toReach=null;
                        AfterMove.run();
                        rightNowPosition.setX(getX());
                        rightNowPosition.setY(getY());
                       // Toast.makeText(getContext(),rightNowPosition.getNowBuilding().mathX+" "+getX(),Toast.LENGTH_SHORT).show();
                    }
                     else{
                         if(target().peek().getStart().map!=rightNowPosition.getCurrentMap()){
                             rightNowPosition.setNowBuilding(target().peek().getStart());
                             WhenSwitchMap.run();
                         }
                         rightNowPosition.setPath(target().peek());
                         toReach=rightNowPosition.getPath().getEnd();
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
        rightNowPosition.setX(getX());
        rightNowPosition.setY(getY());
    }
    public RelativeLayout.LayoutParams getStuParams() {
        RelativeLayout.LayoutParams StuParams;
        StuParams = new  RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT,  RelativeLayout.LayoutParams.MATCH_PARENT);
        StuParams.height=32;
        StuParams.width=32;
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

    public void setTargetBuilding(Building targetBuilding,String strategy) {
        student.setTargetBuilding(targetBuilding,strategy);
    }
    private Queue<Path> target(){return student.pathsToGo;}

}

