package com.hx.stevenbase.ui.main;

import com.hx.steven.activity.BaseWebActivity;
import com.hx.steven.manager.X5WebManager;
import com.hx.steven.util.BarColorUtils;

public class WebActivity extends BaseWebActivity {
    @Override
    public Object getWebInterface() {
        return new WebInterface();
    }

    @Override
    protected void initView() {
        super.initView();
        BarColorUtils.setBarColor(this, "#FFB6C1", false);
        X5WebManager.getInstance().loadUrl("https://happy-read-h5-test.xiyueapp.info/");
    }
}
