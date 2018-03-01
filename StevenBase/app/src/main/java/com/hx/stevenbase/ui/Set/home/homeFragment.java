package com.hx.stevenbase.ui.Set.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hx.steven.fragment.BaseMvpLazyFragment;
import com.hx.steven.util.LoadType;
import com.hx.steven.util.ToastUtil;
import com.hx.stevenbase.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends BaseMvpLazyFragment<homePresenter,homeContract.View>
        implements homeContract.View, BaseQuickAdapter.OnItemChildClickListener,
        BaseQuickAdapter.OnItemClickListener{

    @BindView(R.id.recycler_question)
    RecyclerView recyclerQuestion;
    @BindView(R.id.refreshLayout_question)
    TwinklingRefreshLayout refreshLayoutQuestion;
    Unbinder unbinder;

    private homeAdapter adapter;//首页文章适配器
    private Banner mBannerAds;//banner控件

    @Override
    protected void initMvpLazyFragment(View view) {
        unbinder = ButterKnife.bind(this, view);
        /**设置RecyclerView adapter*/
        recyclerQuestion.setLayoutManager(new LinearLayoutManager(context));
        adapter = new homeAdapter(R.layout.home_item,null);
        adapter.setEnableLoadMore(false);
        recyclerQuestion.setAdapter(adapter);
        /**设置bannerView*/
        View   mHomeBannerHeadView = LayoutInflater.from(context).inflate(R.layout.home_banner_head, null);
        mBannerAds =  mHomeBannerHeadView.findViewById(R.id.banner_ads);
        adapter.addHeaderView(mHomeBannerHeadView);

        /**设置事件监听*/
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
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
    }

    @Override
    protected homePresenter createPresenter() {
        return new homePresenter();
    }
    @Override
    protected int getContentId() {
        return R.layout.fragment_question;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     *=======================================回调方法=======================================================
     */

    @Override
    public void setHomeSuccess(homeBean home,int mLoadType) {
        switch (mLoadType){
            case LoadType.TYPE_REFRESH_SUCCESS:
                refreshLayoutQuestion.finishRefreshing();
                adapter.setNewData(home.getDatas());
                getMultipleStatusView().showContent();
                break;
            case LoadType.TYPE_LOAD_MORE_SUCCESS:
               if(home.getDatas()!=null) {
                   adapter.addData(home.getDatas());
               }
                List<homeBean.DatasBean> list = home.getDatas();
                if (list == null || list.isEmpty() || list.size() <20) {
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
        List<String> titles = new ArrayList();
        for (homeBannerBean banner : homeBannerBeans) {
            images.add(banner.getImagePath());
            titles.add(banner.getTitle());
        }
        mBannerAds.setImages(images)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context)
                                .load(path)
                                .into(imageView);
                    }
                })
                .start();
        mBannerAds.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ToastUtil.showToast(context,"bannerClick");
            }
        });
    }

    @Override
    public void homeFail(String msg) {
        getMultipleStatusView().showError();
        getMultipleStatusView().setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.reFresh();
            }
        });
    }

    @Override
    public void onFirstUserVisible() {
        getMultipleStatusView().showLoading();
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
        ToastUtil.showToast(context,"ItemChildClick");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.showToast(context,"ItemClick");
    }
}
