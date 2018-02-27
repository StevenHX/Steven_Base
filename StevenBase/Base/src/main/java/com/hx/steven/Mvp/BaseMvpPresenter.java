package com.hx.steven.Mvp;

import com.orhanobut.logger.Logger;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseMvpPresenter<V extends BaseMvpView> implements Presenter<V> {
    private V mvpView;
    private CompositeDisposable composite = new CompositeDisposable();

    @Override
    public void attachView(V mvpView) {
        Logger.d("attachView: ");
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        Logger.d( "detachView: ");
        mvpView = null;
    }

    @Override
    public void subscribe(Disposable disposable) {
        Logger.d("subscribe");
        composite.add(disposable);
    }

    @Override
    public void unScribe() {
        Logger.d("unScribe");
        composite.clear();
    }

    /**
     * 判断 view是否为空
     * @return
     */
    public  boolean isAttachView(){
        return mvpView != null;
    }
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
        if(! isAttachView()){
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
}
