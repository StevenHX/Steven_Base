package com.hx.steven.web;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.webkit.JavascriptInterface;

import com.hx.steven.activity.BaseActivity;
import com.hx.steven.app.BaseApplication;
import com.hx.steven.manager.WxManager;
import com.hx.steven.model.AppInfo;
import com.hx.steven.util.AppUtils;
import com.hx.steven.util.BarColorUtils;
import com.hx.steven.util.JsonHelp;
import com.hx.steven.wxShare.AuthCallback;
import com.hx.steven.wxShare.ShareCallback;
import com.hx.steven.wxShare.WxAuthDto;
import com.hx.steven.wxShare.WxShareBean;
import com.hx.steven.wx_ali_Pay.IPay;
import com.hx.steven.wx_ali_Pay.PayModel;
import com.hx.steven.wx_ali_Pay.ali.AliPay;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BaseWebInterface {
    /**
     * 获取粘贴板数据
     */
    @JavascriptInterface
    public void getClipText() {
        Logger.d("getClipText");
        Map<String, Object> params = new HashMap<>();
        try {
            ClipboardManager clipboardManager = (ClipboardManager) BaseApplication.getAppContext()
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData abc = clipboardManager.getPrimaryClip();
            ClipData.Item item = abc.getItemAt(0);
            String text = item.getText().toString();

            params.put("result", 1);
            params.put("msg", "");
            params.put("text", text);
        } catch (Exception e) {
            params.put("result", 0);
            params.put("msg", e.getMessage());
        }

        String result = JsonHelp.toJson(params);
        WebManager.getInstance().getWebStrategyInterface().callJs("getClipTextCallback", result);
    }

    /**
     * 设置状态栏颜色
     *
     * @param data
     */
    @JavascriptInterface
    public void setStatusBarColor(String data) {
        Logger.d("setStatusBarColor:" + data);
        JSONObject obj = null;
        try {
            obj = new JSONObject(data);
            String color = obj.getString("color");
            boolean isDark = obj.getInt("isLight") == 0;
            BarColorUtils.setBarColor(BaseActivity.getThis(), color, isDark);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信登录
     */
    @JavascriptInterface
    public void wxLogin() {
        Logger.d("wxAuthLogin");
        WxManager.getInstance().sendAuth(new AuthCallback() {
            @Override
            public void onSuccess(String result) {
                WebManager.getInstance().getWebStrategyInterface().callJs("wxLoginCallback", result);
            }

            @Override
            public void onFailure(int errCode, String msg) {
                WebManager.getInstance().getWebStrategyInterface().callJs("wxLoginCallback", JsonHelp.toJson(new WxAuthDto()));
            }
        });
    }

    /**
     * 微信分享
     *
     * @param data
     */
    @JavascriptInterface
    public void shareWithApp(String data) {
        Logger.d("shareWithApp shareWithApp-data=" + data);
        WxShareBean wxShareBean = JsonHelp.json2Bean(data, WxShareBean.class);
        if (wxShareBean.getShareType() == 1) {//分享给微信好友
            if (wxShareBean.getShareMedia() == 0) {//网页
                WxManager.getInstance().shareWeb(SendMessageToWX.Req.WXSceneSession, wxShareBean, shareCallback);
            } else if (wxShareBean.getShareMedia() == 1) {//图片url
                WxManager.getInstance().shareImage(0, SendMessageToWX.Req.WXSceneSession, wxShareBean, shareCallback);
            } else if (wxShareBean.getShareMedia() == 2) {//base64图片
                WxManager.getInstance().shareImage(1, SendMessageToWX.Req.WXSceneSession, wxShareBean, shareCallback);
            } else if (wxShareBean.getShareMedia() == 3) {//小程序
                WxManager.getInstance().shareMiniProgram(wxShareBean, shareCallback);
            }
        } else if (wxShareBean.getShareType() == 2) {// 分享到朋友圈
            if (wxShareBean.getShareMedia() == 0) {//网页
                WxManager.getInstance().shareWeb(SendMessageToWX.Req.WXSceneTimeline, wxShareBean, shareCallback);
            } else if (wxShareBean.getShareMedia() == 1) {//图片url
                WxManager.getInstance().shareImage(0, SendMessageToWX.Req.WXSceneTimeline, wxShareBean, shareCallback);
            } else if (wxShareBean.getShareMedia() == 2) {//base64图片
                WxManager.getInstance().shareImage(1, SendMessageToWX.Req.WXSceneTimeline, wxShareBean, shareCallback);
            }
        }
    }

    private ShareCallback shareCallback = new ShareCallback() {
        @Override
        public void onSuccess(String result) {
            Map<String, Object> params = new HashMap<>();
            params.put("result", 1);
            params.put("data", result);
            WebManager.getInstance().getWebStrategyInterface().callJs("shareWithAppCallback", JsonHelp.toJson(params));
        }

        @Override
        public void onFailure(String errCode, String msg) {
            Map<String, Object> params = new HashMap<>();
            params.put("result", 0);
            params.put("msg", msg);
            WebManager.getInstance().getWebStrategyInterface().callJs("shareWithAppCallback", JsonHelp.toJson(params));
        }
    };

    /**
     * 第三方支付
     *
     * @param str
     */
    @JavascriptInterface
    public void orderPayWithApp(String str) {
        Logger.d("orderPayWithApp====>", str + "");
        PayModel mNowPayModel = JsonHelp.json2Bean(str, PayModel.class);
        if (mNowPayModel.getType() == 1) {//支付宝支付
            IPay apay = new AliPay();
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("data", mNowPayModel.getPayInfo());
            apay.setDataMap(maps);
            apay.toPay();
        } else if (mNowPayModel.getType() == 2) {//微信支付
            WxManager.getInstance().wxPay(mNowPayModel.getPayInfo());
        }
    }

    /**
     * 获取指定包名的app的版本信息
     */
    @JavascriptInterface
    public void getAppVersionById(String data) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
            String packageName = jsonObject.getString("packageName");
            AppInfo appInfo = AppUtils.getPackages(packageName);
            Map<String, Object> params = new HashMap<>();
            params.put("result", 1);
            params.put("versionName", appInfo != null ? appInfo.getVersionName() : "");
            params.put("versionCode", appInfo != null ? appInfo.getVersionCode() : "");
            WebManager.getInstance().getWebStrategyInterface().callJs("getAppVersionByIdCallBack", JsonHelp.toJson(params));
        } catch (JSONException e) {
            Map<String, Object> params = new HashMap<>();
            params.put("result", 0);
            WebManager.getInstance().getWebStrategyInterface().callJs("getAppVersionByIdCallBack", JsonHelp.toJson(params));
            e.printStackTrace();
        }
    }


}
