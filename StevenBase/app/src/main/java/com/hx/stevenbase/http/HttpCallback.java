package com.hx.stevenbase.http;

import com.hx.steven.http.BaseBean;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class HttpCallback<T extends BaseBean> implements Callback<T> {
    private static final String TAG = "HttpCallback";
    @Override
    public  void onResponse(Call<T> call, Response<T> response) {
       if (response.raw().code()==200){
           onSuccess(response.body());
       }else{
           Logger.e(response.raw().message());
           onFailure(call, new RuntimeException(response.raw().message()));
            }
       }
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Logger.e(t.getMessage());
        onFail(call,t);
    }

    /**
     * 接口调用成功，调用此函数
     * @param result
     */
    public abstract void onSuccess(T  result);

    /**
     * 接口调用失败，调用此函数 比如：服务端参数校验失败
     */
    public abstract void onFail(Call<T> call, Throwable t);

}
