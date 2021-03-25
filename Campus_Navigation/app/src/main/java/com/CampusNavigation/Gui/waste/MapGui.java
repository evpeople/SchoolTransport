package com.CampusNavigation.Gui.waste;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.example.campus_navigation1.R;



public   class MapGui extends ZoomLayout0 {
    private static final String path="src//main//res//pic//back1.jpg";
    private Context context;

    @SuppressLint("ResourceType")
    public MapGui(Context context,AttributeSet attr) {
        super(context,attr);

    }
    @SuppressLint("ResourceType")
    public MapGui(Context context) {
        super(context,null);
       // setBackgroundResource(R.id.gui_map);
    //this.setOnClickListener(new Touch());
        setBackgroundResource(R.drawable.back1);
    //    Building building=new Building(context,new Dot("ok",200,0,200));
     //   Building building2=new Building(context,new Dot("lok",0,200,200));
      //  this.addView(building,building.getParams());
      // this.addView(building2,building.getParams());
        int i=0;

    }


}
