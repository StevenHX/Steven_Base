package com.hx.steven.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * bitmap工具类
 * Created by huangxiao on 2018/8/31.
 */
public class BitmapUtil {
    /**
     * 圆形bitmap
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    public static float[] getBitmapWH(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        return new float[]{options.outWidth,options.outHeight};
    }

    public static float getBitmapRatio(String path){
        float[] wh = getBitmapWH(path);
        float width = wh[0];
        float height = wh[1];
        return height/width;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //加载图片
        BitmapFactory.decodeResource(res,resId,options); //计算缩放比
        options.inSampleSize = calculateInSampleSize(options,reqHeight,reqWidth); //重新加载
        options.inJustDecodeBounds =false;
        return BitmapFactory.decodeResource(res,resId,options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if(height>reqHeight||width>reqWidth){
            int halfHeight = height/2;
            int halfWidth = width/2; //计算缩放比，是2的指数
            while((halfHeight/inSampleSize)>=reqHeight&&(halfWidth/inSampleSize)>=reqWidth){
                inSampleSize*=2;
            }
        }
        return inSampleSize;
    }
}

