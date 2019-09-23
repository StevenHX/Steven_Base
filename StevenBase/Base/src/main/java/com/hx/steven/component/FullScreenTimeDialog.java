package com.hx.steven.component;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 全屏倒计时 dialog
 * 模仿抖音倒计时拍摄控件
 * Created by huangxiao on 2018/6/4.
 */

public class FullScreenTimeDialog extends Dialog {
    private static final long PERIODTIME = 1000;
    private Context context;
    private int maxNumber;
    private String lastTime;
    private CountDownTimer timer;
    private TextView numberTextView;
    private CountDownListener countDownListener;

    public FullScreenTimeDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public FullScreenTimeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected FullScreenTimeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void init() {
        numberTextView = new TextView(context);
        LinearLayout relativeLayout = new LinearLayout(context);
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(rlp);
        relativeLayout.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        numberTextView.setLayoutParams(layoutParams);
        numberTextView.setGravity(Gravity.CENTER);
        numberTextView.setText(String.valueOf(maxNumber / 1000));
        numberTextView.setTextColor(Color.WHITE);
        numberTextView.setTextSize(18f);
        numberTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        relativeLayout.addView(numberTextView);
        setContentView(relativeLayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    /**
     * 设置数字动画
     */
    public void setShowNumberAnimation() {

        ValueAnimator animator1 = ValueAnimator.ofFloat(18f, 210f);
        animator1.setDuration(150);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float size = (float) animation.getAnimatedValue();
                numberTextView.setTextSize(size);
            }
        });
        animator1.start();
    }

    public void setHideNumberAnimation() {
        ValueAnimator animator1 = ValueAnimator.ofFloat(210f, 0f);
        animator1.setDuration(100);
        animator1.setStartDelay(PERIODTIME);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float size = (float) animation.getAnimatedValue();
                numberTextView.setTextSize(size);
                if (size == 0f) {
                    dismiss();
                }
            }
        });
        animator1.start();
    }

    /**
     * 设置倒计时数字
     *
     * @param number 毫秒
     */
    public void setMaxNumber(final int number) {
        this.maxNumber = number;
        timer = new CountDownTimer(this.maxNumber + PERIODTIME, PERIODTIME) {
            @Override
            public void onTick(long millisUntilFinished) {
                numberTextView.setText(String.valueOf(millisUntilFinished / 1000));
                if (!TextUtils.equals(lastTime, String.valueOf(millisUntilFinished / 1000))) {
                    setShowNumberAnimation();
                }
                lastTime = String.valueOf(millisUntilFinished / 1000);
                if (TextUtils.equals(lastTime, "1")) {
                    setHideNumberAnimation();
                    timer.cancel();
                    countDownListener.countDownFinish();
                }
            }

            @Override
            public void onFinish() {

            }
        };
    }

    @Override
    public void show() {
        super.show();
        timer.start();
    }

    public interface CountDownListener {
        void countDownFinish();
    }

    public void setCountDownListener(CountDownListener listener) {
        this.countDownListener = listener;
    }
}
