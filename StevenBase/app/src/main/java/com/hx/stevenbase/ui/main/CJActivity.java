package com.hx.stevenbase.ui.main;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hx.stevenbase.R;

import java.lang.reflect.Field;

public class CJActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_cj);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
            WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
            layoutParams.flags=(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|layoutParams.flags);
        }
        setStatusBar();

        Handler handler = new Handler();
        String str = "12121";
        str.intern();
    }

    public int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final RelativeLayout rl_title_bar = (RelativeLayout) findViewById(R.id.rl_title_bar);
            final int statusHeight = getStatusBarHeight();
            rl_title_bar.post(new Runnable() {
                @Override
                public void run() {
                    int titleHeight = rl_title_bar.getHeight();
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rl_title_bar.getLayoutParams();
                    layoutParams.height = titleHeight + statusHeight;
                    rl_title_bar.setLayoutParams(layoutParams);
                }
            });
        }
    }

}
