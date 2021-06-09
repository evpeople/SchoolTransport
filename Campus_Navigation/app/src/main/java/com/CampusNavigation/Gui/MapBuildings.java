package com.CampusNavigation.Gui;

import android.content.Context;
import android.view.ViewGroup;

import com.CampusNavigation.GraphImport.Graph.Graph;
import com.otaliastudios.zoom.ZoomLayout;

import org.jetbrains.annotations.NotNull;

public    class MapBuildings extends ZoomLayout {
   private AllBuildings all;
   private StudentView stu;
    public MapBuildings(@NotNull Context context, Graph graph) {
        super(context);
        this.setHasClickableChildren(true);
        all=new AllBuildings(context,graph);
        stu=new StudentView(context,((MainActivity)context).student);
        //((MainActivity)context).student.prepareToGoTo();

        addView(all, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        all.addView(stu,stu.getStuParams());

    }

    public StudentView getStu() {
        return stu;
    }
}
