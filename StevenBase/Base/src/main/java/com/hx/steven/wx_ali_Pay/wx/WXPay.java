package com.hx.steven.wx_ali_Pay.wx;

import android.content.Context;

import com.hx.steven.util.JsonHelp;
import com.hx.steven.wx_ali_Pay.IPay;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

public class WXPay implements IPay {

    private IWXAPI msgApi;
    PayReq req;

    /**
     * 添加参数
     *
     * @param maps
     */
    WXPayModel mWXPayModel;

    String weChatId;
    /**
     * 初始化对象
     */
    public WXPay(Context act) {
        req = new PayReq();
        registerApp(act);
    }

    @Override
    public void toPay() {
        // 检查是否安装微信
        if (msgApi.isWXAppInstalled()) {
            genPayReq();
            sendPayReq(this.weChatId);
        }
    }

    public void setDataMap(Map<String, Object> maps) {
        String data = maps.get("data").toString();
        mWXPayModel = JsonHelp.json2Bean(data, WXPayModel.class);
        Logger.d("mWXPayModel===============", JsonHelp.toJson(mWXPayModel) + "");
    }

    public void setWeChatId(String weChatId){
        this.weChatId = weChatId;
    }

    private void genPayReq() {
        req.appId = mWXPayModel.getAppid();
        req.partnerId = mWXPayModel.getPartnerid();
        req.prepayId = mWXPayModel.getPrepayid();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = mWXPayModel.getNoncestr();
        req.timeStamp = mWXPayModel.getTimestamp();
        req.sign = mWXPayModel.getSign();
    }

    private void sendPayReq(String wxChatId) {
        msgApi.registerApp(wxChatId);
        msgApi.sendReq(req);
    }

    /**
     * 注册APP
     */
    public void registerApp(Context act) {
        if (msgApi == null) {
            msgApi = WXAPIFactory.createWXAPI(act, null);
        }
    }

    /**
     * 操作完成以后调用
     */
    public void DestroyWx() {
        if (msgApi != null) {
            msgApi.unregisterApp();
            msgApi = null;
        }
    }

}
