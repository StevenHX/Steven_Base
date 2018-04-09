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
        models.clear();
        models = null;
    }
//===================================与View有关公共方法==========================================

    /**
     * 返回目标view
     * @return
     */
    public  V getMvpView(){
        return mvpView;
    }
    /**
     * 检查view和presenter是否连接
     */
    public void checkViewAttach(){
        if(mvpView == null){
            throw  new MvpViewNotAttachedException();
        }
    }
    /**
     * 自定义异常
     */
    public static   class  MvpViewNotAttachedException extends RuntimeException {
        public  MvpViewNotAttachedException(){
            super("请求数据前请先调用 attachView(MvpView) 方法与View建立连接");
        }
    }

//==========================================与model相关公共方法===================================
    /**
     * 获取model
     */
    public List<BaseMvpModel> getMvpModels(){
        if(models == null){
            throw  new MvpViewNotAttachedException();
        }else{
            return models;
        }
    }

    /**
     * 自定义异常
     */
    public static   class  MvpModelNotAttachedException extends RuntimeException {
        public  MvpModelNotAttachedException(){
            super("请求数据前请先调用 attachModels(MvpView) 方法与View建立连接");
        }
    }
}
