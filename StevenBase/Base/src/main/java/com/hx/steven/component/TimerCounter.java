package com.hx.steven.component;

import android.os.CountDownTimer;
import android.widget.TextView;

public class TimerCounter extends CountDownTimer {
    private TextView mBtn;
    private final static int TOTOL_TIME = 60;//倒计时60s

    public TimerCounter(TextView btn) {
        super(TOTOL_TIME*1000, 1000);
        this.mBtn = btn;
        mBtn.setText("获取验证码");
        mBtn.setTextColor(0xfffa8d19);
    }


    @Override
    public void onFinish() {
        mBtn.setClickable(true);
        mBtn.setText("获取验证码");
        mBtn.setTextColor(0xfffa8d19);
    }


    @Override
    public void onTick(long arg0) {
        mBtn.setClickable(false);
        mBtn.setTextColor(0xff666666);
        mBtn.setText((arg0 / 1000) +"s重新获取...");
    }
}