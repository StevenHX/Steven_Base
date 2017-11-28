package com.hx.stevenbase;

import android.view.View;
import android.widget.Button;
import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.MultipleStatusView;
import com.hx.steven.util.LogUtil;
import com.hx.steven.util.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {
    @Override
    protected void initView() {
        Button btn = (Button) findViewById(R.id.hello);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showCustomToast(MainActivity.this,"23232");
            }
        });
        LogUtil.e("11111111");
        setTitle("首页");
        showStatus(MultipleStatusView.STATUS_LOADING);
        showStatus(MultipleStatusView.STATUS_CONTENT);
//        showProgressDialog();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isShowHeader() {
        return true;
    }
}
