package com.CampusNavigation.Gui.waste;
        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Paint;
        import android.graphics.Rect;
        import android.graphics.RectF;
        import android.graphics.Paint.Style;
        import android.util.AttributeSet;
        import android.view.View;
        import android.widget.FrameLayout;
        import android.widget.RelativeLayout;

/**
 * @作者： jiatao
 * @修改时间：2016-3-12 上午11:31:51
 * @包名：com.cctvjiatao.customview.v2
 * @文件名：CustomView2.java
 * @版权声明：www.cctvjiatao.com
 * @功能： 自定义View，画线、矩形、圆角矩形、圆形
 */
public class CustomView2 extends View{
    FrameLayout.LayoutParams params=new  FrameLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    public CustomView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView2(Context context) {
        super(context);
        params.leftMargin=500;
        params.rightMargin=700;
        params.height=1000;
        params.width=1000;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(10);
        paint.setColor(0xffff0000);
        // 画直线
        canvas.drawLine(0, 10, 200, 10, paint);
        // 画斜线
        canvas.drawLine(0, 10, 200, 60, paint);

        // 画矩形(Rect)
        Rect rect = new Rect(0, 80, 100, 160);
        canvas.drawRect(rect, paint);
        // 画矩形(RectF)
        RectF rectf = new RectF(150, 80, 250, 160);
        canvas.drawRect(rectf, paint);
        // 画矩形(坐标)
        canvas.drawRect(300, 80, 400, 160, paint);

        // 画圆角矩形(RectF)
        RectF rectrf = new RectF(10, 180, 110, 250);
        canvas.drawRoundRect(rectrf, 10, 10, paint);
        // 画圆角矩形(RectF)
        canvas.drawRoundRect(120, 180, 220, 250, 10, 10, paint);

        // 画圆形
        canvas.drawCircle(100, 350, 50, paint);
        paint.setStyle(Style.STROKE);
        canvas.drawCircle(210, 350, 50, paint);
        paint.setStyle(Style.FILL_AND_STROKE);
        canvas.drawCircle(320, 350, 50, paint);
        paint.setStyle(Style.FILL);
        canvas.drawCircle(430, 350, 50, paint);

    }

    public FrameLayout.LayoutParams getParams() {
        return params;
    }
}