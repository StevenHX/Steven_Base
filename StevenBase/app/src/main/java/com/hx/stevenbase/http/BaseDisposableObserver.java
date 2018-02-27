package com.hx.stevenbase.http;

import com.hx.steven.http.BaseBean;

import io.reactivex.observers.DisposableObserver;


/**
 * Created by Steven on 2018/2/27.
 */

public abstract class  BaseDisposableObserver<T extends BaseBean> extends DisposableObserver<T> {
    @Override
    public void onNext(T t) {
        if(t.isOk()){
            onSuccess(t);
        }else{
            onFail(t,new Exception("not ok"));
        }
    }

    @Override
    public void onError(Throwable e) {
        onFail(null,e);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T  result);
    public abstract void onFail(T result,Throwable t);
}
