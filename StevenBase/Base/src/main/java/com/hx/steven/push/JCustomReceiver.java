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
        super.onNotifyMessageOpened(context,message);
        Log.e(TAG, "[onNotifyMessageOpened] " + message);
        JPushInterface.setBadgeNumber(context, 0);
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
