package com.hx.steven.manager;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.hx.steven.app.BaseApplication;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import java.util.HashMap;

public class X5WebManager {
    private String TAG = "WebManager";
    private static X5WebManager instance;
    private WebView webView;

    public static X5WebManager getInstance() {
        if (instance == null) {
            synchronized (X5WebManager.class) {
                if (instance == null) {
                    instance = new X5WebManager();
                }
            }
        }
        return instance;
    }


    public void initX5WebView() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d(TAG, " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        // 防止和crosswalk冲突、多进程加快初始化速度
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_PRIVATE_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
        //x5内核初始化接口
        QbSdk.initX5Environment(BaseApplication.getAppContext(), cb);
    }

    public void initWebView(WebView webView, Object objectInterface) {
        this.webView = webView;
        webView.clearCache(true);
        webView.clearHistory();
        webView.requestFocus();
        WebSettings webSetting = webView.getSettings();
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
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().flush();

        webView.setSaveEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.addJavascriptInterface(objectInterface, "android");
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


}
