package com.hx.steven.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

public class Mp4Util {
    /**
     * 获取视频的比例
     * @return
     */
    public static float getVideoRatio(String path){
        float[] widthHeight = getVideoWidthHeight(path);
        float width = widthHeight[0];
        float height = widthHeight[1];
        return height*1.0f / width;
    }

    /**
     * 获取视频的第一帧
     */
    public static Bitmap getVideoFirstFrame(String path){
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(path);
            return mmr.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
        }
        catch (RuntimeException e){

        }
        return null;
    }
    /**
     * 获取视频的宽高
     */
    public static float[] getVideoWidthHeight(String videoPath){
        Bitmap bitmap = getVideoFirstFrame(videoPath);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float[] wh = new float[]{width,height};
        return wh;
    }
}
