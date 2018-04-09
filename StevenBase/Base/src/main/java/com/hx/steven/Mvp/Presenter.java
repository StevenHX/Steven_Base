package com.hx.steven.Mvp;

import java.util.List;

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
     * rxJava统一注册管理
     */
    void subscribe(Disposable disposable);
    /**
     * rxJava取消订阅
     */
    void unScribe();

    /**
     * presenter 与 model 绑定
     */
    void attachModels(List<BaseMvpModel> models);
    /**
     * presenter 与 model 解绑
     */
    void detachModel();
}
