package com.hx.stevenbase.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 贴纸view
 */
public class StickerView extends View {
    Paint mPaint;
    private Bitmap mStickerBitmap;//贴纸图像
    private Bitmap mDelBitmap;//删除图像
    private Matrix mMatrix;//图像变化矩阵
    private boolean isFocus;//当前贴纸是否聚焦
    protected int mMode;//当前模式

    private float[] mSrcPoints;//矩阵变换前点坐标
    private float[] mDstPoints;//矩阵变换后点坐标
    private RectF mStickerBound;//贴纸范围
    private RectF mDelBound;//删除按钮范围
    private PointF mMidPointF;//贴纸中心点坐标

    public static final int MODE_NONE = 0;//初始状态
    public static final int MODE_SINGLE = 1;//标志是否可移动
    public static final int MODE_MULTIPLE = 2;//标志是否可缩放，旋转

    private static final int PADDING = 30;//避免图像与边框太近，这里设置一个边距
    public StickerView(Context context, Bitmap bitmap) {
        super(context);
        this.mStickerBitmap = bitmap;
        init(context);
    }

    public StickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save(); // 保存状态（入栈）

        canvas.translate(50, 0);
        canvas.scale(2f, 2f);
        mPaint.setColor(Color.BLUE); // 绘制蓝色方块
        canvas.drawRect(0, 0, 50, 50, mPaint);

        canvas.restore(); // 恢复状态（出栈）

        mPaint.setColor(Color.GREEN); // 绘制绿色方块
        canvas.drawRect(0, 0, 50, 50, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
