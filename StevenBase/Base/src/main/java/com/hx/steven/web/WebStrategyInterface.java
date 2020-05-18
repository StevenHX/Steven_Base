package com.hx.steven.web;

import android.view.View;

public interface WebStrategyInterface {
     void initWebView(View webView, Object objectInterface);
     void loadUrl(String url);
     void callJs(String func, String data);
     View getWebView();
}
