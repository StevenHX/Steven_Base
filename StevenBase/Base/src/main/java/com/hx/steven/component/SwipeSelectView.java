package com.hx.steven.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 滑动选择控件
 * Created by huangxiao on 2018/4/20.
 */

public class SwipeSelectView extends View {
    private final int MOST_HEIGHT = 120;//最高高度
    private final String BG_COLOR = "#10000000";//透明白
    private final String UNSELECT_PROGRESS_COLOR = "#ffffff";//白色
    private final String SELECT_PROGRESS_COLOR = "#fec934";//橙色
    private Paint linePaint;//进度条画笔
    private Paint bgPaint;//背景和文字画笔
    private int mWidth;//控件宽度
    private int mHeight;//控件高度
    private int lineCount = 60;//进度线数量
    private int lineWidth;//进度线宽度
    private int lineHeight;//进度线高度
    private float lineEachDistance;//每个进度条所占用的宽度
    private float downx ;

    public SwipeSelectView(Context context) {
        this(context, null);
    }

    public SwipeSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bgPaint = new Paint();
        bgPaint.setColor(Color.parseColor(BG_COLOR));

        linePaint = new Paint();
        linePaint.setColor(Color.parseColor(UNSELECT_PROGRESS_COLOR));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       switch (event.getAction()){
           case MotionEvent.ACTION_DOWN:
               break;
           case MotionEvent.ACTION_MOVE:
               downx = event.getX();
               invalidate();
               break;
           case MotionEvent.ACTION_UP:
           case MotionEvent.ACTION_CANCEL:
               break;
       }
       return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**绘制背景*/
        RectF bgRect = new RectF();
        bgRect.left = 0;
        bgRect.top = 0;
        bgRect.right = mWidth;
        bgRect.bottom = mHeight;
        canvas.drawRect(bgRect,bgPaint);
        /**绘制进度*/
        float curDistance = 0f;
        while (curDistance < mWidth){

            /**绘制选中进度*/
            if ((curDistance<downx+3*lineEachDistance || curDistance>downx-3*lineEachDistance)&&downx>0){
                linePaint.setColor(Color.parseColor(SELECT_PROGRESS_COLOR));
                linePaint.setStrokeWidth(lineWidth*1.5f);
                canvas.drawLine(downx-2*lineEachDistance,mHeight/4,downx-2*lineEachDistance,mHeight/4+lineHeight,linePaint);
                canvas.drawLine(downx-lineEachDistance,mHeight/4,downx-lineEachDistance,mHeight/4+lineHeight,linePaint);
                canvas.drawLine(downx,mHeight/4,downx,mHeight/4+lineHeight,linePaint);
                canvas.drawLine(downx+lineEachDistance,mHeight/4,downx+lineEachDistance,mHeight/4+lineHeight,linePaint);
                canvas.drawLine(downx+2*lineEachDistance,mHeight/4,downx+2*lineEachDistance,mHeight/4+lineHeight,linePaint);
            }
                linePaint.setStrokeWidth(lineWidth);
                linePaint.setColor(Color.parseColor(UNSELECT_PROGRESS_COLOR));
                canvas.drawLine(curDistance,mHeight/3,curDistance,mHeight/3+lineHeight,linePaint);

            curDistance+=lineEachDistance;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (hSpecMode == MeasureSpec.AT_MOST || hSpecMode == MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(wSpecSize,MOST_HEIGHT);
            mHeight = MOST_HEIGHT;
        }
        mWidth = wSpecSize;
        lineHeight = mHeight/4;
        lineWidth = lineHeight/4;
        lineEachDistance = mWidth*1.0f/(lineCount+1);
//        downx = 3*lineEachDistance+2+lineWidth;//初始化位置
    }
}
