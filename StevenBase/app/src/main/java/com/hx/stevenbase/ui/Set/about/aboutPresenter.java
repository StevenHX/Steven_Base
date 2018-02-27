package com.hx.stevenbase.ui.Set.about;

import com.hx.stevenbase.http.Api;
import com.hx.stevenbase.http.BaseDisposableObserver;

import java.util.logging.Logger;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 2018/1/16.
 */

public class aboutPresenter extends aboutContract.Presenter {

    @Override
    void aboutRequest(final aboutDto about) {

        subscribe(Api.getInstance().getApiService()
                .about(about)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseDisposableObserver<aboutBean>(){

                    @Override
                    public void onSuccess(aboutBean result) {
                        com.orhanobut.logger.Logger.d(result);
                    }

                    @Override
                    public void onFail(aboutBean result, Throwable t) {
                        com.orhanobut.logger.Logger.d(t);
                    }
                }));
//                .subscribeWith(new DisposableObserver<aboutBean>() {
//                    @Override
//                    public void onNext(aboutBean aboutBean) {
//                        com.orhanobut.logger.Logger.d(aboutBean);
//                        getMvpView().dismissLoding();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        com.orhanobut.logger.Logger.e(e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        com.orhanobut.logger.Logger.d("---------------complete-------------");
//                    }
//                })
//        );
    }
}
