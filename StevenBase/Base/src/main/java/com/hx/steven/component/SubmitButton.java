package com.hx.steven.component;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hx.steven.R;

/**
 * 自定义按钮，点击确认后，中间显示旋转的进度
 * @author huangxiao
 * @date 2018/3/22
 */
public class SubmitButton extends FrameLayout {
    /**
     * 按钮控件
     */
    private TextView mButton;
    /**
     * 旋转控件
     */
    private ImageView mImageView;
    /**
     * 旋转属性动画
     */
    private ObjectAnimator mLoadingAnim;
    /**
     * inflate
     */
    private LayoutInflater mInflater;


    public SubmitButton(Context context) {
        this(context,null);
    }

    public SubmitButton(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SubmitButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    /**
     * 初始化控件
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
         removeAllViews();

         mInflater = LayoutInflater.from(context);
         View view =  mInflater.inflate(R.layout.sbbtn_layout,null);
         addView(view);
         mButton = (TextView) view.findViewById(R.id.btn_login);
         mImageView = (ImageView)view.findViewById(R.id.iv_beauty_login_loading);


        /**设置点击事件*/
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSBBtnClickListerner!=null) mSBBtnClickListerner.clickSubmitButton(v);
            }
        });
    }




    /**
     * 按钮loading动画开启
     */
    public void startLoadingAnim() {
        mImageView.setVisibility(VISIBLE);
        mButton.setText("");
        mLoadingAnim = ObjectAnimator.ofFloat(mImageView, "rotation", 0.0f, 359.0f);
        mLoadingAnim.setDuration(300);
        mLoadingAnim.setRepeatCount(ValueAnimator.INFINITE);
        mLoadingAnim.setRepeatMode(ValueAnimator.RESTART);
        mLoadingAnim.start();
    }

    /**
     * 按钮loading动画停止
     */
    public void stopLoadingAnim() {
        mLoadingAnim.cancel();
        mImageView.setVisibility(GONE);
        mButton.setText("登录");
    }

    /**
     * 设置控件层级到最前面
     * @param currentView
     */
    private void setBringToFront(View currentView){
        bringChildToFront(currentView);
        updateViewLayout(currentView,currentView.getLayoutParams());
    }
    /**
     * 设置控件层级恢复原级
     * @param currentView
     */
    private void moveToBack(View currentView) {
        ViewGroup viewGroup = ((ViewGroup) currentView.getParent());
        int index = viewGroup.indexOfChild(currentView);
        for(int i = 0; i<index; i++) {
            viewGroup.bringChildToFront(viewGroup.getChildAt(i));
        }
    }

    /**
     * 设置login button 状态
     *
     * @param enable
     */
    public void setBtnEnable(boolean enable) {
        if (enable) {
            mButton.setBackgroundResource(R.drawable.sbbtn_selector);
            mButton.setEnabled(true);
        } else {
            mButton.setBackgroundResource(R.drawable.sbbtn_touch);
            mButton.setEnabled(false);
        }
    }

    interface SBBtnClickListerner{
        void clickSubmitButton(View view);
    }

    private SBBtnClickListerner mSBBtnClickListerner;
    private void setOnSBBtnClickListener(SBBtnClickListerner mSBBtnClickListener){
        this.mSBBtnClickListerner = mSBBtnClickListener;
    }

}
