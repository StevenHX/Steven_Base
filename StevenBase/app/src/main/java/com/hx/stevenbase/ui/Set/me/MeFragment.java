package com.hx.stevenbase.ui.Set.me;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.hx.steven.fragment.BaseFragment;
import com.hx.stevenbase.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author huangxiao
 * @date 2018.3.22
 */
public class MeFragment extends BaseFragment {

//    @BindView(R.id.vp_circle)
//    CircularViewpager mVpCircle;
//    @BindView(R.id.ll_dot)
//    ViewPagerIndicator mLlDot;
    Unbinder unbinder;

    private List<View> bannerViews = new ArrayList<View>();

    private ArrayList<ImageView> imageViews;
    private int pirorDot;

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
//        initData();
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void initData() {
//        imageViews = new ArrayList<>();
//        int[] imgs = new int[]{R.drawable.a, R.drawable.b, R.drawable.c};
//        for (int img : imgs) {
//            //新建ImageView,并添加到集合中
//            ImageView imageView = new ImageView(context);
//            imageView.setImageResource(img);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageViews.add(imageView);
//        }
//        CircularPagerAdaper adapter = new CircularPagerAdaper(imageViews);
//        mVpCircle.setAdapter(adapter);
//        //开始自动循环
//        mVpCircle.start();
//        mVpCircle.setOnPagerClickListener(imageViews.size(), new CircularViewpager.OnPagerClickListener() {
//            @Override
//            public void onPagerClick(int pagerPosition) {
//            }
//        });
//        mLlDot.setViewPager(mVpCircle).setCount(imgs.length).create();

    }
    /**
     * 以最小内存读取本地资源图片
     * @param context
     * @param bitmapResId
     * @return
     */
    public static Bitmap readBitmap(Context context, int bitmapResId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(bitmapResId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

}
