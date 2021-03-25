package com.CampusNavigation.Gui.waste;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.example.campus_navigation1.R;

public class CustomView3 extends View {

    private int lastX;
    private int lastY;
    private Scroller mScroller;

    public CustomView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        setBackgroundResource(R.drawable.round);
    }

    public boolean onTouchEvent(MotionEvent event) {
        //获取到手指处的横坐标和纵坐标
        View viewGroup = (View) getParent();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动的距离
                int offX = x - lastX;
                int offY = y - lastY;

                ((View) getParent()).scrollBy(-offX,- offY);
                break;
            case MotionEvent.ACTION_UP:

                //开启滑动,让其回到原点
                mScroller.startScroll(viewGroup.getScrollX(),
                        viewGroup.getScrollY(),
                        -viewGroup.getScrollX() ,-viewGroup.getScrollY());
                break;
        }
        return true;
    }
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()) {
            ((View)getParent()).scrollTo(mScroller.getCurrX(),
                    mScroller.getCurrY());
        }
        invalidate();//必须要调用
    }
}