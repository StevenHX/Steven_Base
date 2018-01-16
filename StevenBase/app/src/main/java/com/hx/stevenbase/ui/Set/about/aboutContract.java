package com.hx.stevenbase.ui.Set.about;

import com.hx.steven.Mvp.BaseMvpPresenter;
import com.hx.steven.Mvp.BaseMvpView;

/**
 * Created by user on 2018/1/16.
 */

public interface aboutContract {
    interface View extends BaseMvpView {
        void aboutSuccess(aboutBean about);
        void aboutFail(String msg);
    }
    abstract class Presenter extends BaseMvpPresenter<aboutContract.View> {
        abstract void aboutRequest(aboutDto about);
    }
}
