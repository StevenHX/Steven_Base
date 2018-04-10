package com.hx.stevenbase.ui.Set.about;

import com.hx.steven.http.BaseBean;
import com.hx.stevenbase.http.Api;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 2018/1/16.
 */

public class aboutPresenter extends aboutContract.Presenter {

    @Override
    void aboutRequest(final aboutDto about) {

//        subscribe(Api.getInstance().getApiService()
//        .about(about)
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribeWith(new BaseDisposableObserver<aboutBean>(){
//
//            @Override
//            public void onSuccess(aboutBean result) {
//
//            }
//
//            @Override
//            public void onFail(aboutBean result, Throwable t) {
//
//            }
//        }));

        subscribe(
                Api.getInstance().getApiService()
                        .about(about)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BaseBean<aboutBean>>() {
                            @Override
                            public void accept(BaseBean<aboutBean> aboutBeanBaseBean) throws Exception {

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        })
        );
    }
}
