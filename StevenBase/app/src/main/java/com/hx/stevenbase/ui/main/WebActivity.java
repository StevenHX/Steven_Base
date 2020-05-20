package com.hx.stevenbase.ui.main;

import com.hx.steven.activity.BaseX5WebActivity;
import com.hx.steven.web.WebManager;
import com.hx.steven.web.X5Strategy;
import com.hx.steven.util.BarColorUtils;

public class WebActivity extends BaseX5WebActivity {
    @Override
    public Object getWebInterface() {
        return new WebInterface();
    }

    @Override
    protected void initView() {
        super.initView();
        WebManager.getInstance().getWebStrategyInterface().loadUrl("https://happy-read-h5-test.xiyueapp.info/");
    }
}
