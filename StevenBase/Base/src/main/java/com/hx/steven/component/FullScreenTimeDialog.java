package com.hx.steven.component;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

/**
 * 全屏倒计时 dialog
 * 模仿抖音倒计时拍摄控件
 * Created by huangxiao on 2018/6/4.
 */

public class FullScreenTimeDialog extends Dialog {
    private static final long periodtime = 1000;
    private int[] images = new int[]{};
    private Context context;
    private ImageView numberIv;
    private int maxNumber;
    private CountDownTimer timer;
    private CountDownListener countDownListener;

    public FullScreenTimeDialog(@NonNull Context context) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
    }

    public FullScreenTimeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected FullScreenTimeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void init() {
        numberIv = new ImageView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        numberIv.setLayoutParams(layoutParams);
        setContentView(numberIv);
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
    public void setNumberAnimation(){
      ObjectAnimator animatorx  =  ObjectAnimator
                .ofFloat(numberIv,"scaleX",1,2);
      animatorx.setDuration(periodtime);
      animatorx.setInterpolator(new OvershootInterpolator());
      animatorx.start();

        ObjectAnimator animatory  =  ObjectAnimator
                .ofFloat(numberIv,"scaleY",1,2);
        animatory.setDuration(periodtime);
        animatory.setInterpolator(new OvershootInterpolator());
        animatory.start();
    }

    /**
     * 设置倒计时数字
     * @param number 毫秒
     */
    public void setMaxNumber(final int number){
        this.maxNumber = number;
        if(images.length != maxNumber/1000) {
            throw new RuntimeException("图片数组与数字不匹配");
        }
        timer = new CountDownTimer(this.maxNumber+periodtime,periodtime) {
            @Override
            public void onTick(long millisUntilFinished) {
                int position = images.length - (int) (millisUntilFinished/1000);
                numberIv.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),images[position],null));
                setNumberAnimation();
            }

            @Override
            public void onFinish() {
                timer.cancel();
                dismiss();
                countDownListener.countDownFinish();
            }
        };

    }

    /**
     * 设置图片数组
     * 必须在设置时间前设置
     * @param images
     */
    public void setImages(int[] images){
        this.images = images;
    }

    @Override
    public void show() {
        super.show();
        timer.start();
    }

    public interface CountDownListener{
        void countDownFinish();
    }
    public void setCountDownListener(CountDownListener listener){
        this.countDownListener = listener;
    }
}
