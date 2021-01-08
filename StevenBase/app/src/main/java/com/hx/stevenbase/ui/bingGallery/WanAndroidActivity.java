package com.hx.stevenbase.ui.bingGallery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.AlphaInAnimation;
import com.hx.stevenbase.R;
import com.hx.stevenbase.ui.Set.home.homeAdapter;
import com.hx.stevenbase.ui.Set.home.homeBannerBean;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class WanAndroidActivity extends AppCompatActivity {
    private RecyclerView recycler_wan;
    private TwinklingRefreshLayout refreshLayout_wan;
    private homeAdapter adapter;
    private WanViewModel viewModel;
    private Banner mBannerAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wan_android);

        viewModel = new ViewModelProvider(
                this, new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(WanViewModel.class);

        recycler_wan = findViewById(R.id.recycler_wan);
        recycler_wan.setLayoutManager(new LinearLayoutManager(this));
        adapter = new homeAdapter(R.layout.home_recycle_item, null);
        adapter.setAdapterAnimation(new AlphaInAnimation());
        recycler_wan.setAdapter(adapter);

        View mHomeBannerHeadView = LayoutInflater.from(this).inflate(R.layout.home_banner_head, null);
        mBannerAds = mHomeBannerHeadView.findViewById(R.id.banner_ads);
        mBannerAds.setIndicatorGravity(BannerConfig.CENTER);
        mBannerAds.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        adapter.addHeaderView(mHomeBannerHeadView);


        viewModel.getHomeData().observe(this, datasBeans -> {
            refreshLayout_wan.finishRefreshing();
            refreshLayout_wan.finishLoadmore();
            adapter.setNewData(datasBeans);
        });
        refreshLayout_wan = findViewById(R.id.refreshLayout_wan);
        refreshLayout_wan.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                viewModel.reFresh();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                viewModel.loadMore();
            }
        });

        viewModel.getBannerData().observe(this, homeBannerBeans -> {
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

        viewModel.loadHomeData();
        viewModel.loadBanner();
    }
}
