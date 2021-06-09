package com.CampusNavigation.Gui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;

import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Map.Path;
import com.CampusNavigation.Student.Position;
import com.CampusNavigation.Student.Student;
import com.example.campus_navigation1.R;

import java.util.LinkedList;
import java.util.Queue;

public   class StudentView extends View {
    private RelativeLayout.LayoutParams StuParams;
    private double vWalk=100.0;
    private double vBike=200.0;
    private ObjectAnimator animatorX;
    private ObjectAnimator animatorY;
    private Student student;
    private Queue<Path> target;
    private Building  toReach;
    Position rightNowPosition;
    //private Building  Reached;

    //private Queue<Pair<Integer,Integer>>  target=new LinkedList<>();
    //Pair<Integer,Integer> toReach;
    //Pair<Integer,Integer>Reached;

    public StudentView(Context context,Student student) {
        super(context);
        setBackgroundResource(R.drawable.student1);
        StuParams = new  RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT,  RelativeLayout.LayoutParams.MATCH_PARENT);
        StuParams.height=32;
        StuParams.width=32;
        toReach=null;
        this.student=student;
        student.view=this;
        this.target=student.pathsToGo;
        this.rightNowPosition=student.position;
    }
    private void setAnimateMoveTo(int x, int y) throws InterruptedException {
        //if buzai tongyiceng
        int t = (int) (1000 * Math.sqrt((x - getX()) * (x - getX()) + (y - getY()) * (y - getY())) / vWalk);//haomiao
        animatorX = ObjectAnimator.ofFloat(this, "translationX", x).setDuration(t);
        animatorY = ObjectAnimator.ofFloat(this, "translationY", y).setDuration(t);
       // animatorX.start();
        //animatorY.start();
    }

    public void startMove() throws InterruptedException {
       if(toReach==null&&!target.isEmpty()){
           rightNowPosition.setPath(target.peek());
           toReach=rightNowPosition.getPath().getEnd();
       }//一旦停止运动toReach为null，一旦开始运动非null，开始运动后会调用一次这里

        if(toReach!=null){//zouzhi
            setAnimateMoveTo((int)toReach.mathX,(int)toReach.mathY);
            animatorX.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    target.poll();
                    rightNowPosition.setNowBuilding(toReach);
                    if(target.isEmpty()){toReach=null;}
                     else{
                         rightNowPosition.setPath(target.peek());
                         toReach=rightNowPosition.getPath().getEnd();
                     }
                    try {
                        startMove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            animatorX.start();animatorY.start();
        }//if(toReach!=null)
    }

    private void stopMove(){
        animatorY.removeAllListeners();
        animatorX.removeAllListeners();
        animatorX.cancel();
        animatorY.cancel();
        toReach=null;
    }
    public RelativeLayout.LayoutParams getStuParams() {
            return StuParams;
    }

    public void setStart(View v){
        v.setOnClickListener((e)->{
            try {
                if(!animatorX.isRunning())startMove();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
    }
    public void setPause(View v){
        v.setOnClickListener((e)->{
            if(animatorX.isRunning())stopMove();
        });
    }

    public void setTargetBuilding(Building targetBuilding) {
        student.setTargetBuilding(targetBuilding);
    }

}

