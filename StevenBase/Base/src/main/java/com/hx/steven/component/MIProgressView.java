package com.hx.steven.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 * Created by huangxiao on 2018/4/12.
 */

public class MIProgressView extends View {
    private static final int ARC_FULL_DEGREE = 360;
    /**
     * 总共条数
     */
    private static final int COUNT = 100;
    /**
     * 条与条之间的间隔角度
     */
    private static final float ARC_EACH_PROGRESS = ARC_FULL_DEGREE * 1.0F / (COUNT - 1);
    private float max = 100;
    private float progress = 70;
    /**
     * 控件的宽度
     */
    private int viewWidth;
    /**
     * 控件的高度
     */
    private int viewHeight;
    /**
     * 中心点位置横坐标
     */
    private int centerX;
    /**
     * 中心点位置竖坐标
     */
    private int centerY;
    /**
     * 中心圆半径
     */
    private int circleRadius;
    /**
     * 进度条线的长度
     */
    private int arcLineLength;
    /**
     * 进度条线的宽度
     */
    private int arcLineWidth;
    /**
     * 画进度条的画笔
     */
    private Paint mProgressPaint;
    /**
     * 中间圆形背景画笔
     */
    private Paint mBackgroundPaint;


    public MIProgressView(Context context) {
        this(context, null);
    }

    public MIProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MIProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (viewWidth == 0 || viewHeight == 0) {

            /**获取控件的宽度和高度*/
            viewWidth = getWidth();
            viewHeight = getHeight();
            /**获取控件的中心点位置坐标*/
            centerX = viewWidth/2;
            centerY = viewHeight/2;
            /**获取中心圆的半径*/
            circleRadius = Math.min(viewWidth, viewHeight) / 2;
            /**获取每个进度条的长度和宽度*/
            arcLineLength = circleRadius/6;
            arcLineWidth = circleRadius/30;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float start = (360 - ARC_FULL_DEGREE) >> 1; //进度条起始角度
        float sweep1 = ARC_FULL_DEGREE * (progress / max); //进度划过的角度

        /**绘制进度条*/
        mProgressPaint.setColor(Color.parseColor(calColor(0.5f, "#ffff0000", "#ff00ff00")));
        mProgressPaint.setStrokeWidth(arcLineWidth);

        float drawDegree = 0f;
        while (drawDegree <= ARC_FULL_DEGREE) {
            double a = ( start + drawDegree) / 180 * Math.PI;
            float lineStartX = centerX - circleRadius * (float) Math.sin(a);
            float lineStartY = centerY + circleRadius * (float) Math.cos(a);
            float lineStopX = lineStartX + arcLineLength * (float) Math.sin(a);
            float lineStopY = lineStartY - arcLineLength * (float) Math.cos(a);

            if (drawDegree > sweep1) {
                //绘制进度条背景
                mProgressPaint.setColor(Color.parseColor("#88aaaaaa"));
                mProgressPaint.setStrokeWidth(arcLineWidth >> 1);
            }

            canvas.drawLine(lineStartX, lineStartY, lineStopX, lineStopY, mProgressPaint);
            drawDegree += ARC_EACH_PROGRESS;
        }


        mBackgroundPaint.setStyle(Paint.Style.FILL);//设置填充
        mBackgroundPaint.setColor(Color.parseColor("#41668b"));
        canvas.drawCircle(centerX, centerY, (circleRadius - arcLineLength) * 0.8f, mBackgroundPaint);


        mBackgroundPaint.setStyle(Paint.Style.STROKE);//设置空心
        mBackgroundPaint.setStrokeWidth(2);
        mBackgroundPaint.setColor(Color.parseColor("#aaaaaaaa"));
        canvas.drawCircle(centerX, centerY, (circleRadius - arcLineLength) * 0.8f,mBackgroundPaint);
    }


    /**
     * 计算渐变效果中间的某个颜色值。
     * 仅支持 #aarrggbb 模式,例如 #ccc9c9b2
     */
    public String calColor(float fraction, String startValue, String endValue) {
        int start_a, start_r, start_g, start_b;
        int end_a, end_r, end_g, end_b;


//start
        start_a = getIntValue(startValue, 1, 3);
        start_r = getIntValue(startValue, 3, 5);
        start_g = getIntValue(startValue, 5, 7);
        start_b = getIntValue(startValue, 7, 9);


        //end
        end_a = getIntValue(endValue, 1, 3);
        end_r = getIntValue(endValue, 3, 5);
        end_g = getIntValue(endValue, 5, 7);
        end_b = getIntValue(endValue, 7, 9);


        return "#" + getHexString((int) (start_a + fraction * (end_a - start_a)))
                + getHexString((int) (start_r + fraction * (end_r - start_r)))
                + getHexString((int) (start_g + fraction * (end_g - start_g)))
                + getHexString((int) (start_b + fraction * (end_b - start_b)));
    }


    //从原始#AARRGGBB颜色值中指定位置截取，并转为int.
    private int getIntValue(String hexValue, int start, int end) {
        return Integer.parseInt(hexValue.substring(start, end), 16);
    }


    private String getHexString(int value) {
        String a = Integer.toHexString(value);
        if (a.length() == 1) {
            a = "0" + a;
        }
        return a;
    }
}
