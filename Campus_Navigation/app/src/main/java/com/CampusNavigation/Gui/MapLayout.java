package com.CampusNavigation.Gui;

import android.content.Context;
import android.view.ViewGroup;

import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.Map.Building;
import com.CampusNavigation.Student.Student;
import com.otaliastudios.zoom.ZoomLayout;

import org.jetbrains.annotations.NotNull;

public    class MapLayout extends ZoomLayout {
   private AllBuildings allBuildings;
   private StudentView student;
    public MapLayout(@NotNull Context context, Graph graph,onBuildingViewTouched onTouch) {
        super(context);
        this.setHasClickableChildren(true);
        allBuildings =new AllBuildings(context,graph,onTouch);
        addView(allBuildings, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public StudentView studentView() {
        return student;
    }

    public void SetStudentView(StudentView studentView){
        if(this.student!=null)deleteStudentView();
        //this.student=new StudentView(getContext(),student);
        this.student=studentView;
        if(studentView.rightNowPosition()!=null){
            studentView.setX((int)studentView.rightNowPosition().getX());
            studentView.setY(((int)studentView.rightNowPosition().getY()));
        }
        allBuildings.addView(this.student,this.student.getStuParams());
    }
    public void SetStudentViewPosition(StudentView studentView){
        if(this.student!=null)deleteStudentView();
        this.student=studentView;
        studentView.reSetPosition();
        allBuildings.addView(this.student,this.student.getStuParams());
    }
    public void deleteStudentView(){
        if(student!=null)allBuildings.removeView(student);
    }
}
