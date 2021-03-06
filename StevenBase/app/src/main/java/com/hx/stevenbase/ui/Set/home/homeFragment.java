package com.hx.stevenbase.ui.Set.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterViewAnimator;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.AlphaInAnimation;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hx.steven.Mvp.BaseMvpModel;
import com.hx.steven.fragment.BaseMvpLazyFragment;
import com.hx.steven.util.ToastUtil;
import com.hx.stevenbase.R;
import com.hx.stevenbase.ui.Login.LoginActivity;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
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
 */
public class homeFragment extends BaseMvpLazyFragment<homePresenter> implements homeContract.View, OnItemClickListener, OnItemChildClickListener {
    @BindView(R.id.recycler_question)
    RecyclerView recyclerQuestion;
    @BindView(R.id.refreshLayout_question)
    TwinklingRefreshLayout refreshLayoutQuestion;
    Unbinder unbinder;

    homeAdapter adapter;//首页文章适配器
    Banner mBannerAds;//banner控件

    @Override
    protected void initMvpLazyFragment(View view) {
        unbinder = ButterKnife.bind(this, view);
        /**设置RecyclerView adapter*/
        recyclerQuestion.setLayoutManager(new LinearLayoutManager(context));
        adapter = new homeAdapter(R.layout.home_recycle_item, null);
        adapter.setAdapterAnimation(new AlphaInAnimation());
        recyclerQuestion.setAdapter(adapter);
        /**设置bannerView*/
        View mHomeBannerHeadView = LayoutInflater.from(context).inflate(R.layout.home_banner_head, null);
        mBannerAds = mHomeBannerHeadView.findViewById(R.id.banner_ads);
        mBannerAds.setIndicatorGravity(BannerConfig.CENTER);
        mBannerAds.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        adapter.addHeaderView(mHomeBannerHeadView);

        /**设置事件监听*/
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        /**
         * 设置骨架屏
         */
        setEnableSkeletonScreen(true,recyclerQuestion,adapter);

        /**设置RefreshLayout*/
        refreshLayoutQuestion.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                mPresenter.reFresh();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.loadMore();
            }
        });
        /**banner Item点击事件*/
        mBannerAds.setOnBannerListener(position -> ToastUtil.showToast(context, "bannerClick"));
    }

    @Override
    protected homePresenter createPresenter() {
        return new homePresenter();
    }

    @Override
    public List<BaseMvpModel> createModels() {
        List<BaseMvpModel> models = new ArrayList<>();
        models.add(new HomeModel());
        return models;
    }

    @Override
    protected int getContentId() {
        return R.layout.home_fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * =======================================回调方法=======================================================
     */

    @Override
    public void setHomeSuccess(homeBean home, int mLoadType) {
        switch (mLoadType) {
            case LoadType.TYPE_REFRESH_SUCCESS:
                refreshLayoutQuestion.finishRefreshing();
                adapter.setNewData(home.getDatas());
                try {
                    hideSkeletonScreen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case LoadType.TYPE_LOAD_MORE_SUCCESS:
                if (home.getDatas() != null) {
                    adapter.addData(home.getDatas());
                }
                List<homeBean.DatasBean> list = home.getDatas();
                if (list == null || list.isEmpty() || list.size() < 20) {
                    refreshLayoutQuestion.setEnableLoadmore(false);
                } else {
                    refreshLayoutQuestion.finishLoadmore();
                }
                break;
        }
    }

    @Override
    public void setHomeBanner(List<homeBannerBean> homeBannerBeans) {
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
    }

    @Override
    public void homeFail(String msg, int mLoadType) {
        switch (mLoadType) {
            case LoadType.TYPE_REFRESH_ERROR:
                try {
                    hideSkeletonScreen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case LoadType.TYPE_LOAD_MORE_ERROR:
                refreshLayoutQuestion.finishLoadmore();
                break;
        }
    }

    @Override
    public void onFirstUserVisible() {
        mPresenter.reFresh();
    }

    @Override
    public void onUserVisible() {
    }

    @Override
    public void onFirstUserInvisible() {
    }

    @Override
    public void onUserInvisible() {
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.showToast(context, "ItemChildClick");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.showToast(context, "ItemClick");
    }
}
