package com.hx.steven.web;

import android.view.View;

public class WebManager {
    private String TAG = "WebManager";
    private static WebManager instance;

    public static WebManager getInstance() {
        if (instance == null) {
            synchronized (WebManager.class) {
                if (instance == null) {
                    instance = new WebManager();
                }
            }
        }
        return instance;
    }

    private WebStrategyInterface webStrategyInterface;

    public void initWebManager(View webView, Object objectInterface, WebStrategyInterface webStrategyInterface) {
        this.webStrategyInterface = webStrategyInterface;
        this.webStrategyInterface.initWebView(webView, objectInterface);
    }

    public WebStrategyInterface getWebStrategyInterface() {
        return webStrategyInterface;
    }

    public View getWebView() {
        return webStrategyInterface.getWebView();
    }
}
