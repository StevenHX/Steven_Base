package com.hx.steven.Mvp;

import io.reactivex.disposables.Disposable;

public interface Presenter<V extends BaseMvpView> {
    /**
     * presenter和对应的view绑定
     * @param mvpView  目标view
     */
    void attachView(V mvpView);
    /**
     * presenter与view解绑
     */
    void detachView();

    /**
     *统一注册管理
     */
    void subscribe(Disposable disposable);
    /**
     *取消订阅
     */
    void unScribe();
}
