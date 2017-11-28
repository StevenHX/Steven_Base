package com.hx.steven.Mvp;


import android.util.Log;

public class BaseMvpPresenter<V extends BaseMvpView> implements Presenter<V> {
    private static final String TAG = "BaseMvpPresenter";
    private V mvpView;
    @Override
    public void attachView(V mvpView) {
        Log.d(TAG, "attachView: ");
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        Log.d(TAG, "detachView: ");
        mvpView = null;
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
