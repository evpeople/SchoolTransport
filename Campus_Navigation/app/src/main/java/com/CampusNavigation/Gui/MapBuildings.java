package com.CampusNavigation.Gui;

import android.content.Context;
import android.view.ViewGroup;
import com.otaliastudios.zoom.ZoomLayout;

import org.jetbrains.annotations.NotNull;

public    class MapBuildings extends ZoomLayout {
   private AllBuildings all;
   private StudentView stu;
    public MapBuildings(@NotNull Context context) {
        super(context);
        this.setHasClickableChildren(true);
        all=new AllBuildings(context);
        stu=new StudentView(context);

        addView(all, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        all.addView(stu,stu.getStuParams());

    }

    public StudentView getStu() {
        return stu;
    }
}
