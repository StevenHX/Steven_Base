package com.hx.stevenbase.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.FlowTag.FlowTagLayout;
import com.hx.steven.component.ProgressBarView;
import com.hx.steven.util.ToastUtil;
import com.hx.steven.viewpageTransformer.ScaleInTransformer;
import com.hx.stevenbase.Bean.User;
import com.hx.stevenbase.R;
import com.hx.stevenbase.app.App;
import com.hx.stevenbase.gen.DaoSession;
import com.hx.stevenbase.ui.Set.SetActivity;
import com.meituan.android.walle.WalleChannelReader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.id_viewpager)
    ViewPager viewPager;
    @BindView(R.id.hello)
    Button hello;
    @BindView(R.id.color_flow_layout)
    FlowTagLayout colorFlowLayout;
    @BindView(R.id.pbView)
    ProgressBarView pbView;

    private DaoSession daoSession;

    private TagAdapter<String> mColorTagAdapter;

    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

    @Override
    protected void initView() {
        setTitle("首页");
        hideLeftIcon();

        ButterKnife.bind(this);
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
        ToastUtil.showToast(this, channel);
        User userone = new User(null, "hx", 25);
        daoSession = App.getDaoSession();
        try {
            daoSession.getUserDao().insert(userone);
        } catch (Exception e) {
            ToastUtil.showToast(this, "插入数据失败");
        }
        ToastUtil.showToast(this,"插入成功");

//        MPermissionUtil.requestPermissionsResult(this,0,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new MPermissionUtil.OnPermissionListener() {
//            @Override
//            public void onPermissionGranted() {
//
//            }
//
//            @Override
//            public void onPermissionDenied() {
//
//            }
//        });

        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        viewPager.setOffscreenPageLimit(3);
        adapter = new pageAdapter(this, images);
        viewPager.setPageTransformer(false, new ScaleInTransformer());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        pbView.setMax(100);
        pbView.setProgress(43);
        pbView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbView.setProgress(56);
            }
        });
    }

    private pageAdapter adapter;
    private int[] images = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.a, R.drawable.b, R.drawable.c};

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isShowHeader() {
        return true;
    }


    @OnClick(R.id.hello)
    public void onViewClicked() {
        launch(MainActivity.this, SetActivity.class);
        ToastUtil.showCustomToast(MainActivity.this, "2333333");

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
    }
}
