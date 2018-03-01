package com.hx.stevenbase.ui.Set.home;

import com.hx.steven.util.LoadType;
import com.hx.stevenbase.http.Api;
import com.hx.stevenbase.http.BaseDisposableObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Steven on 2018/2/27.
 */

public class homePresenter extends homeContract.Presenter {

    private int mPage;//默认是0
    private boolean mIsRefresh = true;//默认是刷新状态

    @Override
    void loadHomeArticles(int page) {

        subscribe(
                Api.getInstance().getApiService().getHomeArticles(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseDisposableObserver<homeBean>(){

                    @Override
                    public void onSuccess(homeBean result) {
                        int loadType = mIsRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;
                        getMvpView().setHomeSuccess(result,loadType);
                    }

                    @Override
                    public void onFail(homeBean result, Throwable t) {
                        getMvpView().homeFail(t.getMessage());
                    }
                })
        );
    }

    @Override
    void loadHomeBanner() {
        subscribe(
                Api.getInstance().getApiService().getHomeBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseDisposableObserver<List<homeBannerBean>>(){

                    @Override
                    public void onSuccess(List<homeBannerBean> result) {
                        getMvpView().setHomeBanner(result);
                    }

                    @Override
                    public void onFail(List<homeBannerBean> result, Throwable t) {
                        getMvpView().homeFail(t.getMessage());
                    }
                })
        );
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
