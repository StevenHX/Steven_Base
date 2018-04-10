package com.hx.steven.Mvp;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseMvpPresenter<V extends BaseMvpView> implements Presenter<V> {
    private V mvpView;
    private List<BaseMvpModel> models;
    private CompositeDisposable composite = new CompositeDisposable();

    @Override
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    @Override
    public void subscribe(Disposable disposable) {
        composite.add(disposable);
    }

    @Override
    public void unScribe() {
        composite.clear();
    }

    @Override
    public void attachModels(List<BaseMvpModel> models) {
        this.models = models;
    }

    @Override
    public void detachModel() {
        models = null;
    }
//===================================与View有关公共方法==========================================

    /**
     * 返回目标view
     */
    public V getMvpView() {
        if (mvpView == null) {
            throw new MvpViewNotAttachedException();
        }
        return mvpView;
    }
    /**
     * 获取具体的View
     */
    public <V extends BaseMvpView> V getMvpView(Class<V> clazz) {
        if (mvpView == null) {
            throw new MvpViewNotAttachedException();
        }
        return (V) mvpView;
    }

//==========================================与model相关公共方法===================================
    /**
     * 获取所有的model
     */
    public List<BaseMvpModel> getMvpModels() {
        if (models == null) {
            throw new MvpModelNotAttachedException();
        } else {
            return models;
        }
    }


    /**
     * 获取具体的某一个model
     */
    public <T extends BaseMvpModel> T getModel(Class<T> clazz) {
        if (models == null) {
            throw new MvpModelNotAttachedException();
        } else {
            for (int i = 0; i < models.size(); i++) {
                BaseMvpModel model = models.get(i);
                if (model.getClass().getName().equals(clazz.getName())) {
                    return (T) model;
                }
            }
            return null;
        }
    }

    /**
     * 自定义异常
     */
    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("请求数据前请先调用 attachView(MvpView) 方法与View建立连接");
        }
    }

    /**
     * 自定义异常
     */
    public static class MvpModelNotAttachedException extends RuntimeException {
        public MvpModelNotAttachedException() {
            super("请求数据前请先调用 attachModels(MvpView) 方法与model建立连接");
        }
    }
}
