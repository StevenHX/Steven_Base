package com.hx.stevenbase.ui.Set.home;

import com.hx.steven.http.BaseBean;
import com.hx.stevenbase.http.Api;
import com.hx.stevenbase.http.HttpCallback;

import java.util.List;

import retrofit2.Call;

/**
 *
 * Created by huangxiao on 2018/4/10.
 */

public class HomeModel implements homeContract.Model {

    @Override
    public void doLoadHomeArticles(int page, final HomeListener.HomeArticleListener listener) {
        Api.getInstance().getApiService().getHomeArticles(page).enqueue(new HttpCallback<homeBean>() {
            @Override
            public void onSuccess(homeBean result) {
               listener.homrArticleSuccess(result);
            }

            @Override
            public void onFail(Call<BaseBean<homeBean>> call, Throwable t) {
                listener.homrArticleFail(t);
            }
        });
    }

    @Override
    public void doLoadHomeBanner(final HomeListener.HomeBannerListener listener) {
        Api.getInstance().getApiService().getHomeBanners().enqueue(new HttpCallback<List<homeBannerBean>>() {
            @Override
            public void onSuccess(List<homeBannerBean> result) {
                listener.bannerSuccess(result);
            }

            @Override
            public void onFail(Call<BaseBean<List<homeBannerBean>>> call, Throwable t) {
                listener.bannerFail(t);
            }
        });
    }
}
