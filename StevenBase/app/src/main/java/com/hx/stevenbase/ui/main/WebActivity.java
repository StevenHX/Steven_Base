package com.hx.stevenbase.ui.main;

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
        return R.color.transport;
    }

    @Override
    protected void initView() {
        super.initView();
        BarColorUtils.setBarColor(this,"#F0F8FF",true);
        WebManager.getInstance().getWebStrategyInterface().loadUrl("http://192.168.22.38:8084/");
        removeImage();
    }


}
