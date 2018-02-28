package com.hx.stevenbase.ui.Set.home;

import com.hx.steven.Mvp.BaseMvpPresenter;
import com.hx.steven.Mvp.BaseMvpView;

/**
 *
 * Created by Steven on 2018/2/27.
 */

public interface homeContract {
    interface View extends BaseMvpView {
        void homeSuccess(homeBean home);
        void homeFail(String msg);
    }
    abstract class Presenter extends BaseMvpPresenter<homeContract.View> {
        abstract void HomeArticlesRequest(int page);
    }
}
