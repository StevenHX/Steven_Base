package com.hx.stevenbase.ui.Set.home;

import com.hx.steven.util.LoadType;

import java.util.List;

/**
 *
 * Created by Steven on 2018/2/27.
 */

public class homePresenter extends homeContract.Presenter {

    private int mPage;//默认是0
    private boolean mIsRefresh = true;//默认是刷新状态

    @Override
    void loadHomeArticles(int page) {
        getModel(HomeModel.class).doLoadHomeArticles(page, new HomeListener.HomeArticleListener() {
            @Override
            public void homrArticleSuccess(homeBean home) {
                int loadType = mIsRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;
                getMvpView().setHomeSuccess(home,loadType);
            }

            @Override
            public void homrArticleFail(Throwable t) {
                int loadType = mIsRefresh ? LoadType.TYPE_REFRESH_ERROR : LoadType.TYPE_LOAD_MORE_ERROR;
                getMvpView().homeFail(t.getMessage(),loadType);
            }
        });
    }

    @Override
    void loadHomeBanner() {
        getModel(HomeModel.class).doLoadHomeBanner(new HomeListener.HomeBannerListener() {
            @Override
            public void bannerSuccess(List<homeBannerBean> result) {
                getMvpView().setHomeBanner(result);
            }

            @Override
            public void bannerFail(Throwable t) {
                getMvpView().homeFail(t.getMessage(),LoadType.TYPE_NONE);
            }
        });
    }

    @Override
    void reFresh() {
        mPage = 0;
        mIsRefresh = true;
        loadHomeArticles(mPage);
        loadHomeBanner();
       }

    @Override
    void loadMore() {
        mPage++;
        mIsRefresh = false;
        loadHomeArticles(mPage);
    }
}
