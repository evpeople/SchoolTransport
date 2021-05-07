package com.CampusNavigation.Gui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.CampusNavigation.Student.Student;
import com.example.campus_navigation1.R;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public   class StudentView extends View {
    private RelativeLayout.LayoutParams StuParams;
    private double vWalk=100.0;
    private double vBike=200.0;
    private ObjectAnimator animatorX;
    private ObjectAnimator animatorY;
    private Student student;
    Queue<Pair<Integer,Integer>>  target=new LinkedList<>();
    Pair<Integer,Integer> toReach;
    Pair<Integer,Integer>Reached;

    public StudentView(Context context) {
        super(context);
        setBackgroundResource(R.drawable.student1);
        StuParams = new  RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT,  RelativeLayout.LayoutParams.MATCH_PARENT);
        StuParams.height=32;
        StuParams.width=32;
        Reached=new Pair<Integer, Integer>((int)getX(),(int)getY());
        toReach=Reached;
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
        if(toReach!=null){
            setAnimateMoveTo(toReach.first,toReach.second);
            animatorX.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    Reached=toReach;
                    if(target.isEmpty())toReach=null;
                     else toReach=target.poll();
                    try {
                        startMove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
    }
    public RelativeLayout.LayoutParams getStuParams() {
            return StuParams;
    }

    public void setStart(View v){
        v.setOnClickListener((e)->{
            try {
                student.move();//todo：解决未完成中途停止在启动问题
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
    }
    public void setPause(View v){
        v.setOnClickListener((e)->{
            stopMove();student.stop();
        });
    }
    public void bindStudent(Student student){
        this.student=student;
        student.view=this;
    }


    public void addTarget(int mathX, int mathY) {
        target.add(new Pair<>(mathX,mathY));
    }

}

