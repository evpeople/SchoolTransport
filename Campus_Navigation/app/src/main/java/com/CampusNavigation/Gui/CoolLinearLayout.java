package com.CampusNavigation.Gui;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public   class CoolLinearLayout extends LinearLayout {
    public CoolLinearLayout(Context context) {
        super(context);
    }

    protected EditText addEdit(String hint, LinearLayout layout){
        EditText editText=new EditText(getContext());
        editText.setHint(hint);
        layout.addView(editText);
        return editText;
    }
    protected LinearLayout newLinearLayout(int color){
        LinearLayout top=new LinearLayout(getContext());
        top.setOrientation(HORIZONTAL);
        top.setGravity(Gravity.CENTER_VERTICAL);
        top.setBackgroundColor(color);
        addView(top);
        return top;
    }
    protected Button addButton(String text, LinearLayout layout) {
        Button button = new Button(getContext());
        button.setGravity(Gravity.CENTER);
        button.setText(text);
        LinearLayout.LayoutParams params_button = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //params_button.setMargins(50,0,0,0);
        layout.addView(button, params_button);
        return button;
    }
    protected TextView addText(String text, LinearLayout layout){
        TextView Text =new TextView(getContext(),null);
        Text.setText(text);
        Text.setRight(4);
        layout.addView(Text);
        return  Text;
    }

}
