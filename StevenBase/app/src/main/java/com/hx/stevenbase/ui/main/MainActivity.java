package com.hx.stevenbase.ui.main;

import android.Manifest;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Button;

import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.FlowTag.FlowTagLayout;
import com.hx.steven.component.FullScreenTimeDialog;
import com.hx.steven.component.ProgressBarView;
import com.hx.steven.util.ToastUtil;
import com.hx.steven.viewpageTransformer.ScaleInTransformer;
import com.hx.stevenbase.R;
import com.hx.stevenbase.app.App;
import com.hx.stevenbase.dataBean.User;
import com.hx.stevenbase.gen.DaoSession;
import com.hx.stevenbase.gen.UserDao;
import com.meituan.android.walle.WalleChannelReader;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    {
        setEnableMultiple(false);
    }
    @BindView(R.id.id_viewpager)
    ViewPager viewPager;
    @BindView(R.id.hello)
    Button hello;
    @BindView(R.id.color_flow_layout)
    FlowTagLayout colorFlowLayout;
    @BindView(R.id.pbView)
    ProgressBarView pbView;

    private DaoSession daoSession;

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        /**瓦力多渠道打包*/
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
        ToastUtil.showToast(this, channel);
        /**greenDao数据库操作*/
        User userone = new User(null, "hx", 25);
        daoSession = App.getDaoSession();
        try {
            daoSession.getUserDao().insert(userone);
        } catch (Exception e) {
            ToastUtil.showToast(this, "插入数据失败");
        }
        ToastUtil.showToast(this,"插入成功");

        List<User> users = daoSession.getUserDao().queryBuilder().where(UserDao.Properties.Age.eq(25)).list();
        Logger.d(users.get(0).getName());

        RequestPermissions(0, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        /**viewPager变换操作*/
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        viewPager.setOffscreenPageLimit(3);
        adapter = new pageAdapter(this, images);
        viewPager.setPageTransformer(false, new ScaleInTransformer());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        /**自定义progressView*/
        pbView.setMax(100);
        pbView.setProgress(43);
    }

    private pageAdapter adapter;
    private int[] images = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.a, R.drawable.b, R.drawable.c};

    @Override
    protected int getContentId() {
        return R.layout.main_activity;
    }


    @OnClick(R.id.hello)
    public void onViewClicked() {
        FullScreenTimeDialog dialog = new FullScreenTimeDialog(this);
        dialog.setMaxNumber(3000);
        dialog.setCountDownListener(()->{
            Log.e("xxxxx","countDownFinish");
        });
        dialog.show();
    }
}
