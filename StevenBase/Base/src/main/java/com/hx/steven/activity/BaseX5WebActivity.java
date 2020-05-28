package com.hx.steven.activity;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.hx.steven.R;
import com.hx.steven.web.WebManager;
import com.hx.steven.web.X5Strategy;
import com.tencent.smtt.sdk.WebView;

public abstract class BaseX5WebActivity extends BaseActivity {
    {
        setEnableMultiple(false);
    }

    private WebView mWebView;
    private ImageView imageView;

    protected abstract Object getWebInterface();

    protected abstract @DrawableRes
    int getLaunchImageRes();

    public WebView getWebView() {
        return mWebView;
    }

    @Override
    protected void initView() {
        mWebView = findViewById(R.id.web_webView);
        imageView = findViewById(R.id.main_launch_img);
        imageView.setBackgroundResource(getLaunchImageRes());
        WebManager.getInstance().initWebManager(mWebView, getWebInterface(), new X5Strategy());
    }

    @Override
    protected int getContentId() {
        return R.layout.base_activity_webview;
    }

    private ImageView getImageView() {
        return imageView;
    }

    public void removeImage() {
        getImageView().post(() -> {
            getImageView().setVisibility(View.GONE);
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        });
    }
}
