package com.hx.steven.wxShare;

public interface AuthCallback {

    void onSuccess(String result);

    void onFailure(int errCode, String msg);
}
