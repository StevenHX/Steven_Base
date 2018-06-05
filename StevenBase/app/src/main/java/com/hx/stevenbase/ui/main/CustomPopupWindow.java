package com.hx.stevenbase.ui.main;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.PopupWindow;

/**
 * Created by xiaolp on 2017/11/8
 * description:全局弹窗
 */

public class CustomPopupWindow {
    private static final String TAG = "CustomPopupWindow";
    private PopupWindow mPopupWindow;
    private View contentView;
    private final Window mWindow;
    private ValueAnimator valueAnimator;

    private boolean isBgChange;

    public CustomPopupWindow(Builder builder) {
        Context mContext = builder.context;
        Activity mActivity = (Activity) builder.context;
        mWindow = mActivity.getWindow();
        contentView = LayoutInflater.from(mContext).inflate(builder.contentViewId, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mPopupWindow = new PopupWindow(contentView, builder.width, builder.height, builder.focus);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (isBgChange) {
                    doAnim(false);
                }
                if (builder.listener != null) {
                    builder.listener.onDismiss();
                }
            }
        });

        mPopupWindow.setOutsideTouchable(builder.outsideCancel);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(builder.outsideCancel);
        if (builder.animStyle != -1) {
            mPopupWindow.setAnimationStyle(builder.animStyle);
        }
        isBgChange = builder.isChangeBg;
    }

    public PopupWindow getmPopupWindow() {
        if (mPopupWindow != null) {
            return mPopupWindow;
        } else {
            return null;
        }
    }

    /**
     * popup 消失
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 根据id获取view
     */
    public View getItemView(@IdRes int viewId) {
        if (mPopupWindow != null) {
            return this.contentView.findViewById(viewId);
        }
        return null;
    }

    /**
     * 根据父布局，显示位置
     *
     * @param parent
     * @param gravity
     * @param x
     * @param y
     * @return
     */
    public CustomPopupWindow showAtLocation(View parent, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(parent, gravity, x, y);
            if (isBgChange) {
                doAnim(true);
            }

        }
        return this;
    }


    /**
     * 做透明度动画，灰色背景
     */
    private void doAnim(boolean isOpen) {
        if (valueAnimator != null && valueAnimator.isRunning())
            valueAnimator.cancel();
        float startInt = isOpen ? 1f : 0.6f;
        float endInt = isOpen ? 0.6f : 1f;
        valueAnimator = ValueAnimator.ofFloat(startInt, endInt);
        valueAnimator.setDuration(200);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            float result = (float) animation.getAnimatedValue();
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.alpha = result;
            mWindow.setAttributes(params);
        });
        valueAnimator.start();
    }

    public CustomPopupWindow showAsDropDown(View view, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(view, x, y);
            if (isBgChange) {
                doAnim(true);
            }
        }
        return this;
    }

    /**
     * 根据id设置焦点监听
     *
     * @param viewid
     * @param listener
     */
    public void setOnFocusListener(int viewid, View.OnFocusChangeListener listener) {
        View view = getItemView(viewid);
        view.setOnFocusChangeListener(listener);
    }

    /**
     * popwindows是否显示
     *
     * @return
     */
    public boolean isShowing() {
        return mPopupWindow != null && mPopupWindow.isShowing();
    }

    public View getContentView() {
        return contentView;
    }

    /**
     * builder 类
     */
    public static class Builder {
        private int contentViewId;
        private int width;
        private int height;
        private boolean focus;
        private boolean outsideCancel;
        private int animStyle = -1;
        private boolean isChangeBg = true;
        private OnPopDismissListener listener;
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContentView(@LayoutRes int contentViewId) {
            this.contentViewId = contentViewId;
            return this;
        }

        public Builder setwidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setheight(int height) {
            this.height = height;
            return this;
        }

        public Builder setFocus(boolean focus) {
            this.focus = focus;
            return this;
        }

        public Builder setOutSideCancel(boolean outsidecancel) {
            this.outsideCancel = outsidecancel;
            return this;
        }

        public Builder setAnimationStyle(int animstyle) {
            this.animStyle = animstyle;
            return this;
        }

        public Builder isChangeBg(boolean isChangeBg) {
            this.isChangeBg = isChangeBg;
            return this;
        }

        public Builder setDissmiss(OnPopDismissListener listener) {
            this.listener = listener;
            return this;
        }

        public CustomPopupWindow builder() {
            return new CustomPopupWindow(this);
        }
    }


    public interface OnPopDismissListener {
        void onDismiss();
    }
}
