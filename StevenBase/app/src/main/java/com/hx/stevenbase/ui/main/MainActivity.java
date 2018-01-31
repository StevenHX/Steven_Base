package com.hx.stevenbase.ui.main;

import android.Manifest;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.hhl.library.FlowTagLayout;
import com.hhl.library.OnTagClickListener;
import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.ButtonLayout;
import com.hx.steven.component.CircleImageView;
import com.hx.steven.component.CommViewPager;
import com.hx.steven.util.LogUtil;
import com.hx.steven.util.ToastUtil;
import com.hx.stevenbase.Bean.User;
import com.hx.stevenbase.R;
import com.hx.stevenbase.app.App;
import com.hx.stevenbase.gen.DaoSession;
import com.meituan.android.walle.WalleChannelReader;
import com.hx.stevenbase.ui.Set.SetActivity;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.NonPageTransformer;
import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;
import com.zhy.magicviewpager.transformer.RotateUpPageTransformer;
import com.zhy.magicviewpager.transformer.RotateYTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private String [] tips = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS};
    private DaoSession daoSession;

    private TagAdapter<String> mColorTagAdapter;
    private FlowTagLayout flowTagLayout;
    private ViewPager viewPager;
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
        flowTagLayout = (FlowTagLayout) findViewById(R.id.color_flow_layout);
        mColorTagAdapter = new TagAdapter<>(this);
        flowTagLayout.setAdapter(mColorTagAdapter);
        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        flowTagLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                Snackbar.make(view, "颜色:" + parent.getAdapter().getItem(position), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
       initColorData();

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


        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        viewPager.setOffscreenPageLimit(3);
        adapter = new pageAdapter(this,images);
        viewPager.setPageTransformer(false,new ScaleInTransformer());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

    private pageAdapter adapter;
    private int[] images = new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.a,R.drawable.b,R.drawable.c};

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isShowHeader() {
        return true;
    }


    private void initColorData() {
        List<String> dataSource = new ArrayList<>();
//        dataSource.add("红色");
//        dataSource.add("黑色");
//        dataSource.add("花边色");
//        dataSource.add("深蓝色");
//        dataSource.add("白色");
//        dataSource.add("玫瑰红色");
        dataSource.add("紫黑紫兰色");
        dataSource.add("葡萄红色");
        dataSource.add("屎黄色");
        dataSource.add("绿色");
        dataSource.add("彩虹色");
        dataSource.add("牡丹色");
        dataSource.add("红色");
        dataSource.add("黑色");
        dataSource.add("1");
        dataSource.add("2");
        dataSource.add("3");
        dataSource.add("7");
        dataSource.add("8");
        dataSource.add("12");
        dataSource.add("15");
        dataSource.add("17");
        dataSource.add("18");
        dataSource.add("19");
        mColorTagAdapter.onlyAddAll(dataSource);
    }

}
