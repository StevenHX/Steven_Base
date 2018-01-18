package com.hx.steven.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hx.steven.util.LogUtil;

/**
 * Created by user on 2018/1/16.
 */

public class CircleImageView extends ImageView{
    private static final int DEFUALT_WIDTH = 200;
    private static final int DEFUALT_HEIGHT = 200;
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;


    private Bitmap mBitmap;//图片bitmap
    private BitmapShader mBitmapShader;//图片渲染
    private int mBitmapWidth;//图片宽
    private int mBitmapHeight;//图片高
    private float mDrawableRadius;//图片半径

    private final RectF mDrawableRect = new RectF();

    private Paint mBitmapPaint = new Paint();//图片画笔
    public CircleImageView(Context context) {
        super(context);
        init();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LogUtil.d("init");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.d("onDraw");
        if(getDrawable()==null) return;

        canvas.drawCircle(getWidth()/2,getHeight()/2,mDrawableRadius,mBitmapPaint);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        initializeBitmap();
    }


    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        initializeBitmap();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        initializeBitmap();
    }

    @Override
    public void setImageURI(@Nullable Uri uri) {
        super.setImageURI(uri);
        initializeBitmap();
    }

    private void initializeBitmap() {
        LogUtil.d("initialzeBitmap");
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    private void setup() {
        LogUtil.d("step");
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapPaint.setAntiAlias(true);//设置图片画笔反锯齿
        mBitmapPaint.setShader(mBitmapShader);//给图片画笔设置渲染器

        mDrawableRadius = getWidth()/2;

        mBitmapWidth = mBitmap.getWidth();//获取图片的宽度
        mBitmapHeight = mBitmap.getHeight();//获取图片的高度
        invalidate();//触发onDraw()函数
    }


    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
