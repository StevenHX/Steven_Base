package com.hx.steven.activity;

import com.hx.steven.R;
import com.hx.steven.manager.X5WebManager;
import com.tencent.smtt.sdk.WebView;

public abstract class BaseWebActivity extends BaseActivity {
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
        X5WebManager.getInstance().initWebView(mWebView,getWebInterface());
    }

    @Override
    protected int getContentId() {
        return R.layout.base_activity_webview;
    }
}
