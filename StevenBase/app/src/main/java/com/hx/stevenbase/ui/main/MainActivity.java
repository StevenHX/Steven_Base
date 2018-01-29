package com.hx.stevenbase.ui.main;

import android.Manifest;
import android.util.Log;
import android.view.View;

import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.ButtonLayout;
import com.hx.steven.component.CircleImageView;
import com.hx.steven.util.LogUtil;
import com.hx.steven.util.ToastUtil;
import com.hx.stevenbase.Bean.User;
import com.hx.stevenbase.R;
import com.hx.stevenbase.app.App;
import com.hx.stevenbase.gen.DaoSession;
import com.meituan.android.walle.WalleChannelReader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private String [] tips = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS};
    private CircleImageView circleImageView;
    private ButtonLayout btnlayout;
    private DaoSession daoSession;
    @Override
    protected void initView() {
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
        ToastUtil.showToast(this,channel);
        User userone = new User(null,"hx",25);
        daoSession = App.getDaoSession();
        try {
            daoSession.getUserDao().insert(userone);
        }catch (Exception e){
            ToastUtil.showToast(this, "插入数据失败");
        }
//        ToastUtil.showToast(this,"插入成功");
//        Button btn = (Button) findViewById(R.id.hello);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                launch(MainActivity.this, LoginActivity.class);
//                launch(MainActivity.this, SetActivity.class);
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
//            }
//        });
        LogUtil.e("11111111");
        setTitle("首页");
        hideLeftIcon();
        circleImageView = (CircleImageView) findViewById(R.id.circleImage);
        circleImageView.setImageResource(R.drawable.hugh);


        btnlayout = (ButtonLayout) findViewById(R.id.btnlayout);
        List<String> data = new ArrayList<>();
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
        data.add("1");
//        btnlayout.setItemBackground(R.drawable.btnbg);
        btnlayout.setItemTextSize(10);
        btnlayout.setItemClickListener(new ButtonLayout.ItemClickListener() {
            @Override
            public void itemClick(View view, int position) {
                Log.d(TAG, "itemClick: "+position);
            }
        });
        btnlayout.setDatas(this,data,ButtonLayout.BUTTON_TYPE);
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
