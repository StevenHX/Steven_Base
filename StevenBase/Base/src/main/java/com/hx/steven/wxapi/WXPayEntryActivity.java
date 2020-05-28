package com.hx.steven.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.hx.steven.manager.WxManager;
import com.hx.steven.util.JsonHelp;
import com.hx.steven.web.CallBackNameContract;
import com.hx.steven.web.WebManager;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * Created by user on 2018/3/27.
 */


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout view = new RelativeLayout(this);
        view.setBackgroundColor(0x00FFFFFF);
        setContentView(view);
        WxManager.getInstance().getApi().handleIntent(getIntent(), this);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WxManager.getInstance().getApi().handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    /**
     * 微信支付成功以后广播支付成功的信息
     */
    @Override
    public void onResp(BaseResp resp) {
        Logger.e("onResp==========", resp.errCode + "====" + resp.getType());
        Logger.e("微信支付成功返回数据==========", JsonHelp.toJson(resp) + "");
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case 0:
                    WebManager.getInstance().getWebStrategyInterface().callJs(CallBackNameContract.ORDER_PAY_WITH_APP, "1");
                    break;
                case -1:
                case -2:
                    WebManager.getInstance().getWebStrategyInterface().callJs(CallBackNameContract.ORDER_PAY_WITH_APP, "0");
                    break;
                default:
                    break;
            }
            WxManager.getInstance().destroyWx();
        }
        finish();

    }


}
