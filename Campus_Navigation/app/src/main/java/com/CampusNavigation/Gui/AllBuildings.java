package com.CampusNavigation.Gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;
import com.CampusNavigation.GraphImport.Graph.*;
import com.example.campus_navigation1.R;

import java.util.ArrayList;


public class AllBuildings extends RelativeLayout {
    private Graph graph;
    private ArrayList<BuildingView> buildings = new ArrayList<>();

    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    public AllBuildings(Context context,Graph graph) {
        super(context);
        this.graph=graph;
        setAllBuildings(graph);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setAllRoads(graph,canvas);
    }

    private void setAllBuildings(Graph graph) {
        for (int i=0;i<graph.NumOfDots();i++) {
            Dot dot=graph.getDots()[i];
            BuildingView building = new BuildingView(getContext(), dot);
            buildings.add(building);
            addView(building, building.getParams());
        }
        ;
    }

    private void setAllRoads(Graph graph,Canvas canvas) {
        int i = 0, j = 0;

        for (i = 0; i < graph.NumOfDots(); i++)
            for (j = 0; j < graph.NumOfDots(); j++) {
                Edge edge = graph.getEdges()[i][j];
                if(edge!=null)  drawRoad(edge, false,canvas);
            }
    }

    @SuppressLint("ResourceAsColor")
    private void drawRoad(Edge edge, boolean isPath,Canvas canvas) {
        float width = 0;
        float Width = 0;
        int Color = 0;
        Paint paintW = new Paint();
        Paint paintw = new Paint();

        //消除锯齿
        paintW.setAntiAlias(true);
        paintW.setStyle(Paint.Style.STROKE);
       paintw.setStyle(Paint.Style.STROKE);
        paintW.setAlpha(200);

        if (edge.isBikeOk()) {
            width = 11;
            Width = 19;
            Color = R.color.green;
        } else {
            width = 7;
            Width = 13;
            Color = R.color.blue;
        }
        if (isPath) {
            Color = R.color.purple;
        }

        paintW.setStrokeWidth(Width);
        paintw.setStrokeWidth(width);
        paintw.setColor(ContextCompat.getColor(getContext(), Color));
        paintW.setARGB(255, ((Float) ((1 - edge.getDegreeOfCongestion()) * 255)).intValue(), 0, 0);
        Path path = new Path();
        path.moveTo((float) edge.start.X, (float) edge.start.Y);
        path.lineTo((float) edge.end.X, (float) edge.end.Y);

        canvas.drawPath(path, paintW);
        canvas.drawPath(path, paintw);
    }

}
