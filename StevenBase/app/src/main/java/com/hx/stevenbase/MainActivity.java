package com.hx.stevenbase;

import android.Manifest;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.MultipleStatusView;
import com.hx.steven.util.CircleImageView;
import com.hx.steven.util.LogUtil;
import com.hx.steven.util.MPermissionUtil;
import com.hx.steven.util.ToastUtil;
import com.hx.stevenbase.ui.Login.LoginActivity;
import com.hx.stevenbase.ui.Set.SetActivity;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.MDAlertDialog;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.wevey.selector.dialog.NormalSelectionDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {
    private String [] tips = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS};
    private CircleImageView circleImageView;
    @Override
    protected void initView() {
        Button btn = (Button) findViewById(R.id.hello);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                launch(MainActivity.this, LoginActivity.class);
                launch(MainActivity.this, SetActivity.class);
//                ToastUtil.showCustomToast(MainActivity.this,"2333333");

//                new MDAlertDialog.Builder(MainActivity.this)
//                        .setContentText("加载中...")
//                        .setContentTextSize(30)
//                        .setContentTextColor(R.color.base_deep)
//                        .setOnclickListener(new DialogOnClickListener() {
//                            @Override
//                            public void clickLeftButton(View view) {
//
//                            }
//
//                            @Override
//                            public void clickRightButton(View view) {
//
//                            }
//                        })
//
//                        .build()
//                        .show();
//                NormalSelectionDialog selectionDialog =  new NormalSelectionDialog.Builder(MainActivity.this)
//                        .setTitleText("消息")
//                        .setlTitleVisible(true)
//                        .setTitleTextColor(R.color.base_deep)
//                        .build();
//                ArrayList<String> data = new ArrayList<String>();
//                data.add("1111");
//                data.add("2222");
//                data.add("3333");
//                data.add("4444");
//                 selectionDialog.setDataList(data);
//                 selectionDialog.show();
            }
        });
        LogUtil.e("11111111");
        setTitle("首页");
        hideLeftIcon();
        circleImageView = (CircleImageView) findViewById(R.id.circleImage);
        circleImageView.setImageResource(R.drawable.hugh);


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
