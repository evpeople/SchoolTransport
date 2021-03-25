//package com.CampusNavigation.Gui;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.util.AttributeSet;
//import android.view.ScaleGestureDetector;
//import android.view.animation.Animation;
//import android.view.animation.Transformation;
//import android.widget.RelativeLayout;
//
//public class ZoomableRelativeLayout extends RelativeLayout {
//    float mScaleFactor = 1;
//    float mPivotX;
//    float mPivotY;
//
//    public ZoomableRelativeLayout(Context context) {
//        super(context);
//        // TODO Auto-generated constructor stub
//    }
//
//    public ZoomableRelativeLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        // TODO Auto-generated constructor stub
//    }
//
//    public ZoomableRelativeLayout(Context context, AttributeSet attrs,
//                                  int defStyle) {
//        super(context, attrs, defStyle);
//        // TODO Auto-generated constructor stub
//    }
//
//    protected void dispatchDraw(Canvas canvas) {
//        canvas.save(Canvas.ALL_SAVE_FLAG);
//        canvas.scale(mScaleFactor, mScaleFactor, mPivotX, mPivotY);
//        super.dispatchDraw(canvas);
//        canvas.restore();
//    }
//
//    public void scale(float scaleFactor, float pivotX, float pivotY) {
//        mScaleFactor = scaleFactor;
//        mPivotX = pivotX;
//        mPivotY = pivotY;
//        this.invalidate();
//    }
//
//    public void restore() {
//        mScaleFactor = 1;
//        this.invalidate();
//    }
//
//
//    public void relativeScale(float scaleFactor, float pivotX, float pivotY)
//    {
//        mScaleFactor *= scaleFactor;
//
//        if(scaleFactor >= 1)
//        {
//            mPivotX = mPivotX + (pivotX - mPivotX) * (1 - 1 / scaleFactor);
//            mPivotY = mPivotY + (pivotY - mPivotY) * (1 - 1 / scaleFactor);
//        }
//        else
//        {
//            pivotX = getWidth()/2;
//            pivotY = getHeight()/2;
//
//            mPivotX = mPivotX + (pivotX - mPivotX) * (1 - scaleFactor);
//            mPivotY = mPivotY + (pivotY - mPivotY) * (1 - scaleFactor);
//        }
//
//        this.invalidate();
//    }
//
//    public void release()
//    {
//        if(mScaleFactor < MIN_SCALE)
//        {
//            final float startScaleFactor = mScaleFactor;
//
//            Animation a = new Animation()
//            {
//                @Override
//                protected void applyTransformation(float interpolatedTime, Transformation t)
//                {
//                    scale(startScaleFactor + (MIN_SCALE - startScaleFactor)*interpolatedTime,mPivotX,mPivotY);
//                }
//            };
//
//            a.setDuration(300);
//            startAnimation(a);
//        }
//        else if(mScaleFactor > MAX_SCALE)
//        {
//            final float startScaleFactor = mScaleFactor;
//
//            Animation a = new Animation()
//            {
//                @Override
//                protected void applyTransformation(float interpolatedTime, Transformation t)
//                {
//                    scale(startScaleFactor + (MAX_SCALE - startScaleFactor)*interpolatedTime,mPivotX,mPivotY);
//                }
//            };
//
//            a.setDuration(300);
//            startAnimation(a);
//        }
//    }
//}
//
//
//
//
//
//private class OnPinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
//{
//    float currentSpan;
//    float startFocusX;
//    float startFocusY;
//
//    public boolean onScaleBegin(ScaleGestureDetector detector)
//    {
//        currentSpan = detector.getCurrentSpan();
//        startFocusX = detector.getFocusX();
//        startFocusY = detector.getFocusY();
//        return true;
//    }
//
//    public boolean onScale(ScaleGestureDetector detector)
//    {
//        ZoomableRelativeLayout zoomableRelativeLayout= (ZoomableRelativeLayout) ImageFullScreenActivity.this.findViewById(R.id.imageWrapper);
//
//        zoomableRelativeLayout.relativeScale(detector.getCurrentSpan() / currentSpan, startFocusX, startFocusY);
//
//        currentSpan = detector.getCurrentSpan();
//
//        return true;
//    }
//
//    public void onScaleEnd(ScaleGestureDetector detector)
//    {
//        ZoomableRelativeLayout zoomableRelativeLayout= (ZoomableRelativeLayout) ImageFullScreenActivity.this.findViewById(R.id.imageWrapper);
//
//        zoomableRelativeLayout.release();
//    }
//}