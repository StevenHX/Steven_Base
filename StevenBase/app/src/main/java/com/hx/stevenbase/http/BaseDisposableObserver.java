package com.hx.stevenbase.http;

import com.hx.steven.http.BaseBean;
import com.orhanobut.logger.Logger;

import io.reactivex.observers.DisposableObserver;


/**
 * Created by Steven on 2018/2/27.
 */

public abstract class  BaseDisposableObserver<T> extends DisposableObserver<BaseBean<T>> {
    @Override
    public void onNext(BaseBean<T> t) {
        if(t.getErrorCode() == 0){
            onSuccess(t.getData());
        }else{
            onFail(t.getData(),new Exception("fail"));
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
