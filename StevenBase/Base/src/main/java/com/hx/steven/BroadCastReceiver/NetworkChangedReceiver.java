package com.hx.steven.BroadCastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.hx.steven.activity.BaseActivity;
import com.hx.steven.util.NetworkUtil;

/**
 * Created by user on 2017/11/27.
 */

public class NetworkChangedReceiver extends BroadcastReceiver {
    public NetEvevt evevt = BaseActivity.evevt;
    @Override
    public void onReceive(Context context, Intent intent) {
        int netWorkStates = NetworkUtil.getNetWorkStates(context);

        switch (netWorkStates) {
            case NetworkUtil.TYPE_NONE:
                evevt.onNetChange(NetworkUtil.TYPE_NONE);
                break;
            case NetworkUtil.TYPE_MOBILE:
                evevt.onNetChange(NetworkUtil.TYPE_MOBILE);
                break;
            case NetworkUtil.TYPE_WIFI:
                evevt.onNetChange(NetworkUtil.TYPE_WIFI);
                break;

            default:
                break;
        }
    }
    // 自定义接口
    public interface NetEvevt {
        void onNetChange(int netMobile);
    }
}
