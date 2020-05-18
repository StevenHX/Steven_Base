package com.hx.steven.wxShare;

public interface ShareCallback {
    void onSuccess(String result);

    void onFailure(String errCode, String msg);
}
