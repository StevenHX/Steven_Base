package com.hx.stevenbase.ui.main;

import android.content.Intent;

import com.hx.mediaselect.constract.Code;
import com.hx.mediaselect.constract.Setting;
import com.hx.mediaselect.model.Photo;
import com.hx.mediaselect.util.BitmapUtil;
import com.hx.steven.activity.BaseX5WebActivity;
import com.hx.steven.util.JsonHelp;
import com.hx.steven.web.CallBackNameContract;
import com.hx.steven.web.WebManager;
import com.hx.stevenbase.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebActivity extends BaseX5WebActivity {
    @Override
    public Object getWebInterface() {
        return new WebInterface();
    }

    @Override
    protected int getLaunchImageRes() {
        return R.color.transparent;
    }

    @Override
    protected void initView() {
        super.initView();
        WebManager.getInstance().getWebStrategyInterface().loadUrl("http://192.168.22.13:9527");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (requestCode == Code.SELECT_ALBUM_REQUEST_CODE) {
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(Code.PHOTO_RESULT_KEY);
                Logger.d(resultPhotos);
                List<String> results = new ArrayList<>();
                for (Photo photo : resultPhotos) {
                    String base64Str = BitmapUtil.toBase64(this, photo.getUri(), Setting.dstWidth, Setting.dstHeight);
                    results.add(base64Str);
                }
                Map<String, Object> params = new HashMap<>();
                params.put("result", 1);
                params.put("data", results);
                WebManager.getInstance().getWebStrategyInterface().callJs(CallBackNameContract.OPEN_ALBUM, JsonHelp.toJson(params));
            }
        }
    }
}
