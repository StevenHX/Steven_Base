package com.hx.stevenbase.ui.Set.home;

import java.util.List;

/**
 * Created by Xiejq on 2018/4/10.
 */

public interface HomeListener {

    interface HomeArticleListener {
        void homrArticleSuccess(homeBean home);
        void homrArticleFail(Throwable t);
    }

    interface HomeBannerListener {
        void bannerSuccess(List<homeBannerBean> result);
        void bannerFail(Throwable t);
    }
}
