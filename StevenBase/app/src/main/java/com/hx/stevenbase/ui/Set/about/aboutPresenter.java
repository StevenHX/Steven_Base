package com.hx.stevenbase.ui.Set.about;

import com.hx.stevenbase.http.Api;
import com.hx.stevenbase.http.HttpCallback;

import retrofit2.Call;

/**
 * Created by user on 2018/1/16.
 */

public class aboutPresenter extends aboutContract.Presenter {

    @Override
    void aboutRequest(aboutDto about) {
        checkViewAttach();
        Api.getService().about(about).enqueue(new HttpCallback<aboutBean>() {
            @Override
            public void onSuccess(aboutBean result) {
                getMvpView().aboutSuccess(result);
            }

            @Override
            public void onFail(Call<aboutBean> call, Throwable t) {
                getMvpView().aboutFail(t.getMessage());
            }
        });
    }
}
