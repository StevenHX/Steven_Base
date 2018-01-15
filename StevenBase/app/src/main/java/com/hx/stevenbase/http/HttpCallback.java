package com.hx.stevenbase.http;

import android.util.Log;
import com.hx.steven.app.BaseApplication;
import com.hx.steven.http.BaseBean;
import com.hx.steven.util.LogUtil;
import com.hx.steven.util.ToastUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class HttpCallback<T extends BaseBean> implements Callback<T> {
    private static final String TAG = "HttpCallback";
    @Override
    public  void onResponse(Call<T> call, Response<T> response) {
       if (response.raw().code()==200){
            if(response.body().isOk()){
                if(response.body().getResult()==null){//如果result为null
                    LogUtil.e(response.body().getMessage());
                    onFail(call,new RuntimeException(response.body().getMessage()));
                }else{
                    onSuccess(response.body());
                }
            }
       }else
           LogUtil.e(response.body().getMessage());
           onFailure(call, new RuntimeException(response.body().getMessage()));
       }
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        LogUtil.e(t.toString());
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
