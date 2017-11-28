package com.hx.steven.Mvp;

/**
 * Created by user on 2017/11/28.
 */

public interface BaseMvpView {
    /**
     * 显示进度条
     * @param msg   进度条加载内容
     */
    void showLoding(String msg);
    /**
     * 隐藏进度条
     */
    void dismissLoding();
    /**
     * 显示加载错误
     * @param err 错误内容
     */
    void showErr(String err);
}
