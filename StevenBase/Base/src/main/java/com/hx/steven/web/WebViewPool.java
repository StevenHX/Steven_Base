package com.hx.steven.web;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tencent.smtt.sdk.WebView;

import java.util.ArrayList;
import java.util.List;

public class WebViewPool {
    private static final String APP_CACHE_DIRNAME = "webCache";
    private static List<WebView> available = new ArrayList<>();
    private static List<WebView> inUse = new ArrayList<>();
    private static final byte[] lock = new byte[]{};
    private static int maxSize = 1;
    private int currentSize = 0;
    private static long startTimes = 0;
    private static volatile WebViewPool instance = null;

    public static WebViewPool getInstance() {
        if (instance == null) {
            synchronized (WebViewPool.class) {
                if (instance == null) {
                    instance = new WebViewPool();
                }
            }
        }
        return instance;
    }

    /**
     * Webview 初始化
     * 最好放在application oncreate里
     */
    public static void init(Context context) {
        for (int i = 0; i < maxSize; i++) {
            WebView webView = new WebView(context);
            initWebSetting(context, webView);
            //webView.loadUrl(DEMO_URL);
            available.add(webView);
        }
    }

    /**
     * 获取webview
     */
    public WebView getWebView(Context context) {
        synchronized (lock) {
            WebView webView;
            if (available.size() > 0) {
                webView = available.get(0);
                available.remove(0);
                currentSize++;
                inUse.add(webView);
            } else {
                webView = new WebView(context);
                initWebSetting(context, webView);
                inUse.add(webView);
                currentSize++;
            }
            return webView;
        }
    }

    /**
     * 回收webview ,不解绑
     *
     * @param webView 需要被回收的webview
     */
    public void removeWebView(WebView webView) {
        webView.loadUrl("");
        webView.stopLoading();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.clearCache(true);
        webView.clearHistory();
        synchronized (lock) {
            inUse.remove(webView);
            if (available.size() < maxSize) {
                available.add(webView);
            } else {
                webView = null;
            }
            currentSize--;
        }
    }

    /**
     * 回收webview ,解绑
     *
     * @param webView 需要被回收的webview
     */
    public void removeWebView(ViewGroup viewGroup, WebView webView) {
        viewGroup.removeView(webView);
        webView.loadUrl("");
        webView.stopLoading();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.clearCache(true);
        webView.clearHistory();
        synchronized (lock) {
            inUse.remove(webView);
            if (available.size() < maxSize) {
                available.add(webView);
            } else {
                webView = null;
            }
            currentSize--;
        }
    }

    /**
     * 设置webview池个数
     *
     * @param size webview池个数
     */
    public void setMaxPoolSize(int size) {
        synchronized (lock) {
            maxSize = size;
        }
    }

    private static void initWebSetting(Context context, WebView webView) {
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
    }
}
