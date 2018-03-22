package com.hx.steven.component;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hx.steven.R;

/**
 * 自定义按钮，点击确认后，中间显示旋转的进度
 * Created by huangxiao on 2018/3/22.
 * @author huangxiao
 */
public class SubmitButton extends FrameLayout implements View.OnClickListener {
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

    /**
     * image
     */
    int[] imgs = new int[]{R.drawable.sbbtn_selector, R.drawable.sbbtn_loading};

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

//         mInflater = LayoutInflater.from(context);
//         View view =  mInflater.inflate(R.layout.sbbtn_layout,null);
//         addView(view);
//         mButton = (TextView) view.findViewById(R.id.btn_login);
//         mImageView = (ImageView)view.findViewById(R.id.iv_beauty_login_loading);
//
//         mButton.setOnClickListener(this);
//        setBtnEnable(true);
        /**初始化按钮*/
        mButton = new Button(context);
        mButton.setText("登陆");
        mButton.setTextColor(Color.WHITE);
        this.addView(mButton);
        mButton.setBackgroundResource(imgs[0]);
        FrameLayout.LayoutParams btnParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        btnParams.width = 270;
        btnParams.height = 39;
        mButton.setLayoutParams(btnParams);

        /**初始化旋转控件*/
        mImageView = new ImageView(context);
        this.addView(mImageView);
        mImageView.setImageResource(imgs[1]);
        FrameLayout.LayoutParams weelParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        weelParams.width = 23;
        weelParams.height = 23;
        mImageView.setLayoutParams(weelParams);
//        setBringToFront(mImageView);

        /**设置点击事件*/
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoadingAnim();
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //addView之后要重新给子view设置宽高 这个很重要，没有就不显示
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 按钮loading动画开启
     */
    private void startLoadingAnim() {
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
    private void stopLoadingAnim() {
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
    private void setBtnEnable(boolean enable) {
        if (enable) {
            mButton.setBackgroundResource(R.drawable.sbbtn_selector);
            mButton.setEnabled(true);
        } else {
            mButton.setBackgroundResource(R.drawable.sbbtn_touch);
            mButton.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        startLoadingAnim();
    }
}
