package com.hx.stevenbase.ui.Set.home;

import com.hx.steven.Mvp.BaseMvpModel;
import com.hx.steven.Mvp.BaseMvpPresenter;
import com.hx.steven.Mvp.BaseMvpView;

import java.util.List;

/**
 *
 * Created by Steven on 2018/2/27.
 */

public interface homeContract {
    interface View extends BaseMvpView {
        void setHomeSuccess(homeBean home,int LoadType);
        void setHomeBanner(List<homeBannerBean> homeBannerBeans);
        void homeFail(String msg,int LoadType);
    }
    abstract class Presenter extends BaseMvpPresenter<homeContract.View> {
        /**获取首页列表*/
        abstract void loadHomeArticles(int page);
        /**获取首页banner*/
        abstract void loadHomeBanner();
        /**刷新*/
        abstract  void reFresh();
        /**加载更多*/
        abstract void loadMore();
    }
    interface Model extends BaseMvpModel{
        void doLoadHomeArticles(int page,HomeListener.HomeArticleListener listener);
        void doLoadHomeBanner(HomeListener.HomeBannerListener listener);
    }

}

