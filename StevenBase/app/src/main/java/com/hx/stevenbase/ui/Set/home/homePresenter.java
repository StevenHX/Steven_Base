package com.hx.stevenbase.ui.Set.home;

import com.hx.stevenbase.http.Api;
import com.hx.stevenbase.http.BaseDisposableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Steven on 2018/2/27.
 */

public class homePresenter extends homeContract.Presenter {
    @Override
    void HomeArticlesRequest(int page) {
        subscribe(
                Api.getInstance().getApiService().getHomeArticles(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseDisposableObserver<homeBean>(){

                    @Override
                    public void onSuccess(homeBean result) {
                        getMvpView().homeSuccess(result);
                    }

                    @Override
                    public void onFail(homeBean result, Throwable t) {
                        getMvpView().homeFail(result.getErrorMsg().toString());
                    }
                })
        );
    }
}
