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

            }

            @Override
            public void onFail(aboutBean result, Throwable t) {

            }
        }));
    }
}
