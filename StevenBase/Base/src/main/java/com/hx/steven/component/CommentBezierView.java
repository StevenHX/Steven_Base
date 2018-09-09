package com.hx.steven.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by JiaLe on 2018/8/22.
 * 广场评论曲线
 */

public class CommentBezierView extends View {
    private static final String TAG = "CommentBezierView";

    //画笔
    private Paint mPaint;
    //起始点，终止点,控制点
    private PointF start, end, control;
    private Path mPath;

    public CommentBezierView(Context context) {
        this(context, null);
    }

    public CommentBezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPath = new Path();

        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control = new PointF(0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: w = "+ w + " ," + h  + " ， " + oldw + "," + oldh);
        int centerX = w / 2;
        int centerY = 0;
        control.x = centerX;
        control.y = centerY;

        start.x = 0;
        start.y = h;

        end.x = w;
        end.y = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.moveTo(start.x, start.y);
        mPath.quadTo(control.x, control.y, end.x, end.y);
        canvas.drawPath(mPath, mPaint);
    }
}
