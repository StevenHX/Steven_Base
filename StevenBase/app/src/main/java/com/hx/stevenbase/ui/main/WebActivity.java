package com.hx.stevenbase.ui.main;

import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.hx.steven.activity.BaseX5WebActivity;
import com.hx.steven.util.BarColorUtils;
import com.hx.steven.web.WebManager;
import com.hx.stevenbase.R;

public class WebActivity extends BaseX5WebActivity {
    @Override
    public Object getWebInterface() {
        return new WebInterface();
    }

    @Override
    protected int getLaunchImageRes() {
        return R.drawable.launch_bg;
    }

    @Override
    protected void initView() {
        super.initView();
        WebManager.getInstance().getWebStrategyInterface().loadUrl("https://happy-read-h5-test.xiyueapp.info/");
    }


}
