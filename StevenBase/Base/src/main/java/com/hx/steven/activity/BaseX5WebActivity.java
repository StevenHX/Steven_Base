package com.hx.steven.activity;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
    private X5Strategy x5Strategy;

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
        x5Strategy = new X5Strategy();
        WebManager.getInstance().initWebManager(mWebView, getWebInterface(), x5Strategy);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == X5Strategy.FILE_CHOOSER_REQUEST_CODE) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (x5Strategy.getUploadMessageAboveL() != null) {
                onActivityResultAboveL(data);
            } else if (x5Strategy.getUploadMessage() != null) {
                x5Strategy.getUploadMessage().onReceiveValue(result);
                x5Strategy.setUploadMessage(null);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(Intent intent) {
        Uri[] results = null;
        if (intent != null) {
            String dataString = intent.getDataString();
            ClipData clipData = intent.getClipData();
            if (clipData != null) {
                results = new Uri[clipData.getItemCount()];
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    results[i] = item.getUri();
                }
            }
            if (dataString != null)
                results = new Uri[]{Uri.parse(dataString)};
        }
        x5Strategy.getUploadMessageAboveL().onReceiveValue(results);
        x5Strategy.setUploadMessageAboveL(null);
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
