package com.CampusNavigation.Gui;

import android.content.Context;
import android.view.ViewGroup;

import com.CampusNavigation.GraphImport.Graph.Graph;
import com.CampusNavigation.Student.Student;
import com.otaliastudios.zoom.ZoomLayout;

import org.jetbrains.annotations.NotNull;

public    class MapLayout extends ZoomLayout {
   private AllBuildings allBuildings;
   private StudentView student;
    public MapLayout(@NotNull Context context, Graph graph, Student student) {
        super(context);
        this.setHasClickableChildren(true);
        allBuildings =new AllBuildings(context,graph);
        this.student=new StudentView(context,student);
        addView(allBuildings, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        allBuildings.addView(this.student,this.student.getStuParams());
    }

    public StudentView studentView() {
        return student;
    }
}
