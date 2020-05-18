package com.hx.steven.activity;

import com.hx.steven.R;
import com.hx.steven.web.WebManager;
import com.hx.steven.web.X5Strategy;
import com.tencent.smtt.sdk.WebView;

public abstract class BaseX5WebActivity extends BaseActivity {
    {
        setEnableMultiple(false);
    }

    private WebView mWebView;

    protected abstract Object getWebInterface();

    public WebView getWebView() {
        return mWebView;
    }

    @Override
    protected void initView() {
        mWebView = findViewById(R.id.web_webView);
        WebManager.getInstance().initWebManager(mWebView,getWebInterface(),new X5Strategy());
    }

    @Override
    protected int getContentId() {
        return R.layout.base_activity_webview;
    }
}
