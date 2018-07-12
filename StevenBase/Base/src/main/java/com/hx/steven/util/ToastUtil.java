package com.hx.steven.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hx.steven.R;

/**
 *
 * Created by user on 2017/11/14.
 */

public class ToastUtil {
    private static final int TIME = 2000;
    private static Toast toast;
    /**
     * 显示Toast
     * @param context 上下文
     * @param content 要显示的内容
     */
    public static void showToast(Context context, String content) {
        content = content==null?"":content;
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }

        toast.show();
    }

    /**
     * 显示Toast
     * @param context 上下文
     * @param resId 要显示的资源id
     */
    public static void showToast(Context context, int resId) {
        showToast(context, (String) context.getResources().getText(resId));
    }
    /**
     * 显示自定义view的Toast
     */
    public static void showCustomToast(Context context,String content){
        content = content==null?"":content;
        View view = View.inflate(context,R.layout.custom_view_toast,null);
        if (toast == null) {
            toast = Toast.makeText(context,content , Toast.LENGTH_SHORT);
        }
        TextView tvPrompt = (TextView)view.findViewById(R.id.custom_toast_tv);
        tvPrompt.setText(content);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
