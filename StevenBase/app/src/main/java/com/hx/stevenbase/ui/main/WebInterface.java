package com.hx.stevenbase.ui.main;

import android.webkit.JavascriptInterface;

import com.hx.mediaselect.constract.AlbumBuilder;
import com.hx.mediaselect.constract.Code;
import com.hx.steven.activity.BaseX5WebActivity;
import com.hx.steven.web.BaseWebInterface;
import com.hx.stevenbase.BuildConfig;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

public class WebInterface extends BaseWebInterface {
    /**
     * 打开相册sdk选择图片
     */
    @JavascriptInterface
    public void openAlbum(String data) {
        Logger.d("openAlbum=" + data);
        JSONObject obj = null;
        try {
            obj = new JSONObject(data);
            int count = obj.getInt("count");
            boolean isShowCamera = obj.getBoolean("isShowCamera");
            int dstWidth  = obj.getInt("dstWidth");
            int dstHeight  = obj.getInt("dstHeight");
            AlbumBuilder.with(BaseX5WebActivity.getThis())
                    .setCount(count)
                    .setIsShowCamera(isShowCamera)
                    .setDstWidth(dstWidth)
                    .setDstHeight(dstHeight)
                    .setFileProviderAuthority(BuildConfig.APPLICATION_ID + ".fileProvider")
                    .start(Code.SELECT_ALBUM_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
