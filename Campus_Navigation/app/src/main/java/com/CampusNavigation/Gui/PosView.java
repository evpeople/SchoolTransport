package com.CampusNavigation.Gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.campus_navigation1.R;

import java.text.DecimalFormat;


@SuppressLint("AppCompatCustomView")
public class PosView extends TextView {
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    public PosView(Context context, View target) {
        super(context);
        setText("PosView");
        setTextColor(R.color.black);
        setTextSize(25);
        setBackgroundColor(R.color.design_default_color_secondary);
        new Thread(()->{
            DecimalFormat format = new DecimalFormat("00000.00");
            for (; ; ) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(target!=null)setText(format.format(target.getX())+","+format.format(target.getY()));
        }}).start();
    }
}
