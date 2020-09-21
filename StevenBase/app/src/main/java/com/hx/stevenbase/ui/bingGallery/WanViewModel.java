package com.hx.stevenbase.ui.bingGallery;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hx.steven.http.BaseBean;
import com.hx.stevenbase.ui.Set.home.homeBannerBean;
import com.hx.stevenbase.ui.Set.home.homeBean;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WanViewModel extends ViewModel {
    MutableLiveData<List<homeBean.DatasBean>> homeData;
    MutableLiveData<List<homeBannerBean>> bannerData;
    private ImageRepertory mRepertory;
    int page;

    public WanViewModel() {
        this.homeData = new MutableLiveData<>();
        this.bannerData = new MutableLiveData<>();
        this.mRepertory = new ImageRepertory();
    }

    public MutableLiveData<List<homeBean.DatasBean>> getHomeData() {
        return homeData;
    }

    public MutableLiveData<List<homeBannerBean>> getBannerData() {
        return bannerData;
    }

    public void loadHomeData() {
        mRepertory.getApiService().getHomeArticles(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<homeBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<homeBean> homeBeanBaseBean) {
                        homeData.setValue(homeBeanBaseBean.getData().getDatas());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void reFresh() {
        page = 0;
        mRepertory.getApiService().getHomeArticles(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<homeBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<homeBean> homeBeanBaseBean) {
                        homeData.setValue(homeBeanBaseBean.getData().getDatas());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        loadBanner();
    }

    public void loadMore() {
        mRepertory.getApiService().getHomeArticles(++page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<homeBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<homeBean> homeBeanBaseBean) {
                        List<homeBean.DatasBean> datasBeanList = homeData.getValue();
                        datasBeanList.addAll(homeBeanBaseBean.getData().getDatas());
                        homeData.setValue(datasBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void loadBanner() {
        mRepertory.getApiService().getHomeBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<List<homeBannerBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<List<homeBannerBean>> listBaseBean) {
                        bannerData.setValue(listBaseBean.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
