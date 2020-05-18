package com.hx.steven.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.hx.steven.manager.WxManager;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WxManager.getInstance().getApi().handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp instanceof SendAuth.Resp) {
            String state = ((SendAuth.Resp) baseResp).state;
            if (state != null && state.equals(WxManager.getInstance().getLoginSate())) {
                WxManager.getInstance().authReturn((SendAuth.Resp) baseResp);
            }
        } else if (baseResp instanceof SendMessageToWX.Resp) {
            WxManager.getInstance().shareReturn((SendMessageToWX.Resp) baseResp);
        }
        finish();
    }
}
