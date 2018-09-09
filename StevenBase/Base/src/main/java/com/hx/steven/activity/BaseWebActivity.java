package com.hx.steven.activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hx.steven.R;
import com.orhanobut.logger.Logger;

public abstract class BaseWebActivity extends BaseActivity {
    public static final String SCHEME = "jscall:";
    public static final String URL = "H5_URL";
    public static final String TITLE = "H5_TITLE";
    public static final String CONTRACTNO = "CONTRACT_NO";
    public static final String ISHASHEADER = "IS_HAS_HEADER";
    private ProgressBar progressBar;
    private WebView webView;

    private String url;
    private String title;
    private String contractNo = "";//合同号
    private boolean isshowHeader;//是否显示头部

    {
        url = getIntent().getStringExtra(URL);
        title = getIntent().getStringExtra(TITLE);
        contractNo = getIntent().getStringExtra(CONTRACTNO);
        isshowHeader = getIntent().getBooleanExtra(ISHASHEADER,true);
    }

    @Override
    protected void initView() {
        webView = (WebView) findViewById(R.id.web_webView);
        progressBar = (ProgressBar) findViewById(R.id.web_progressbar);
        setTitle(title);
        initWebView();
        webView.loadUrl(url);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }

    private void initWebView()
    {
        webView.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setDomStorageEnabled(true);//设置缓存
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                result.confirm();
                return true;
            }
            //显示进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                    if (newProgress == 100) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }
        });//与浏览器窗口有关回调

        webView.setWebViewClient(new WebViewClient() {//网页相关回调
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
            {
                return filterSpecialUrl(url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Logger.d("shouldOverrideUrlLoading: "+"========================>" + url);
                return filterSpecialUrl(url);
            }

            private boolean filterSpecialUrl(String url){
                if(url.startsWith(SCHEME)){
                    //逻辑
                    FilterLogic();
                    return true;
                }
                return false;
            }

        });
    }


    public void Open(Context context, String title, String contractNo, String url,boolean isshowHeader)
    {
        Intent intent = new Intent(context, BaseWebActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITLE, title);
        intent.putExtra(CONTRACTNO,contractNo);
        intent.putExtra(ISHASHEADER,isshowHeader);
        context.startActivity(intent);
    }
    //重写返回方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {//判断webview是否可以后退
            webView.goBack();//网页返回
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressBar != null) {
            progressBar = null;
        }
        if(webView!=null){
            webView = null;
        }
    }

    @Override
    protected int getContentId() {
        return R.layout.base_activity_webview;
    }


    /**
     * 拦截URL逻辑
     */
    protected abstract void FilterLogic();
}
