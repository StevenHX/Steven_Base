package com.hx.steven.push;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.hx.steven.activity.BaseX5WebActivity;
import com.hx.steven.app.BaseApplication;
import com.hx.steven.util.JsonHelp;
import com.hx.steven.util.SharedPreferencesUtil;
import com.hx.steven.web.WebManager;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class JCustomReceiver extends JPushMessageReceiver {
    private static final String TAG = "PushMessageReceiver";

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        Log.e(TAG, "[onMessage] " + customMessage);
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageOpened] " + message);
        Map params = new HashMap<String, String>();
        params.put("result", 1);
        params.put("data", message.notificationExtras);
        WebManager.getInstance().getWebStrategyInterface().callJs("receivedMsgCallback", JsonHelp.toJson(params));
        SharedPreferencesUtil.setString(BaseApplication.getAppContext(), "msg_key", "");
        JPushInterface.setBadgeNumber(context, 0);
        try {
            Intent i = new Intent(context, BaseX5WebActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(JPushInterface.EXTRA_NOTIFICATION_TITLE, message.notificationTitle);
            bundle.putString(JPushInterface.EXTRA_ALERT, message.notificationContent);
            bundle.putString(JPushInterface.EXTRA_EXTRA, message.notificationExtras);
            i.putExtras(bundle);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        } catch (Throwable throwable) {
        }
    }

    private Intent getLaunchAppIntent(final String packageName, final boolean isNewTask) {
        Intent intent = BaseApplication.getAppContext().getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) return null;
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }
    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        Log.e(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮");
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageArrived] " + message);
        SharedPreferencesUtil.setString(BaseApplication.getAppContext(), "msg_key", message.notificationExtras);
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageDismiss] " + message);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        Log.e(TAG, "[onRegister] " + registrationId);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        Log.e(TAG, "[onConnected] " + isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e(TAG, "[onCommandResult] " + cmdMessage);
    }
}
