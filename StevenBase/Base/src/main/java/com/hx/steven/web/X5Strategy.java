package com.hx.steven.web;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.hx.steven.app.BaseApplication;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

public class X5Strategy implements WebStrategyInterface {
    private String TAG = "WebManager";
    private WebView webView;

    public void initWebView(View webView, Object objectInterface) {
        this.webView = (WebView) webView;
        this.webView.clearCache(true);
        this.webView.clearHistory();
        this.webView.requestFocus();
        WebSettings webSetting = this.webView.getSettings();
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //限制手机字体大小造成样式变样
        webSetting.setTextZoom(100);
        //启用数据库
        webSetting.setDatabaseEnabled(true);
        String dir = BaseApplication.getAppContext().getDir("database", Context.MODE_PRIVATE).getPath();
        //设置定位的数据库路径
        webSetting.setGeolocationDatabasePath(dir);
        //启用地理定位
        webSetting.setGeolocationEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setAppCacheEnabled(false);
        webSetting.setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(0);
        }
        CookieManager.getInstance().setAcceptThirdPartyCookies(this.webView, true);
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().flush();

        this.webView.setSaveEnabled(false);
        this.webView.setHorizontalScrollBarEnabled(false);//水平不显示
        this.webView.setVerticalScrollBarEnabled(false); //垂直不显示
        this.webView.addJavascriptInterface(objectInterface, "android");
    }

    public void loadUrl(String url) {
        if (webView == null) return;
        Log.e(TAG, "loadUrl :" + url);
        webView.post(() -> webView.loadUrl(url));
    }

    public void callJs(String func, String data) {
        if (webView == null) return;
        Log.e(TAG, "callback js:" + func + "==>" + data);
        webView.post(() -> webView.loadUrl("javascript:" + func + "(" + data + ")"));
    }

    @Override
    public View getWebView() {
        return webView;
    }
}
