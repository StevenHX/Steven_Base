package com.hx.stevenbase.ui.ExamplePage.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.hx.steven.fragment.BaseFragment;
import com.hx.stevenbase.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BannerExampleFragment} factory method to
 * create an instance of this fragment.
 */
public class BannerExampleFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.banner_example)
    Banner mBannerAds;
    private List<String> images = new ArrayList<>();
    @Override
    protected int getContentId() {
        return R.layout.fragment_banner_example;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        mBannerAds.setIndicatorGravity(BannerConfig.CENTER);
        mBannerAds.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        images.add("https://wanandroid.com/blogimgs/8690f5f9-733a-476a-8ad2-2468d043c2d4.png");
        images.add("https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png");
        images.add("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png");
        mBannerAds.setImages(images)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context)
                                .load(path)
                                .into(imageView);
                    }
                })
                .start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}