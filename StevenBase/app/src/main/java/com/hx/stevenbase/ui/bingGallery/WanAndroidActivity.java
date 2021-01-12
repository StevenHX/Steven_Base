package com.hx.stevenbase.ui.bingGallery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.animation.AlphaInAnimation;
import com.hx.steven.Mvvm.BaseMVVMActivity;
import com.hx.stevenbase.R;
import com.hx.stevenbase.databinding.ActivityWanAndroidBinding;
import com.hx.stevenbase.ui.Set.home.homeBannerBean;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class WanAndroidActivity extends BaseMVVMActivity<ActivityWanAndroidBinding, WanViewModel> {
    private WanAdapter adapter;
    private Banner mBannerAds;

    @Override
    protected int getContentId() {
        return R.layout.activity_wan_android;
    }

    @Override
    public Class<WanViewModel> getViewModelClass() {
        return WanViewModel.class;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getmBinding().recyclerWan.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WanAdapter(R.layout.wan_recycle_item, null);
        adapter.setAdapterAnimation(new AlphaInAnimation());
        getmBinding().recyclerWan.setAdapter(adapter);

        View mHomeBannerHeadView = LayoutInflater.from(this).inflate(R.layout.home_banner_head, null);
        mBannerAds = mHomeBannerHeadView.findViewById(R.id.banner_ads);
        mBannerAds.setIndicatorGravity(BannerConfig.CENTER);
        mBannerAds.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        adapter.addHeaderView(mHomeBannerHeadView);


        getmViewModel().getHomeData().observe(this, datasBeans -> {
            getmBinding().refreshLayoutWan.finishRefreshing();
            getmBinding().refreshLayoutWan.finishLoadmore();
            adapter.setNewData(datasBeans);
        });
        getmBinding().refreshLayoutWan.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                getmViewModel().reFresh();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                getmViewModel().loadMore();
            }
        });

        getmViewModel().getBannerData().observe(this, homeBannerBeans -> {
            List<String> images = new ArrayList();
            for (homeBannerBean banner : homeBannerBeans) {
                images.add(banner.getImagePath());
            }
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
        });

        getmViewModel().loadHomeData();
        getmViewModel().loadBanner();
    }
}
