package com.hx.steven.activity;

import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

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
    private long mExitTime;

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //不执行父类点击事件
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
            } else if (mWebView != null && !mWebView.canGoBack()) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    //大于2000ms则认为是误操作，使用Toast进行提示
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    //并记录下本次点击“返回键”的时刻，以便下次进行判断
                    mExitTime = System.currentTimeMillis();
                } else {
                    //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                    finish();
                }
            }
            return true;
        }
        //继续执行父类其他点击事件
        return super.onKeyUp(keyCode, event);
    }
}
