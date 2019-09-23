package com.hx.steven.component;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hx.steven.R;

public class WxActionBar extends LinearLayout{
    private Drawable leftIcon;//左侧图案
    private String titleString;//标题文字
    private LayoutInflater inflater;
    private ImageView leftIv;//left_icon
    private TextView titleTv;//title控件
    private OnHeadClickListener headClickListener;

    public WxActionBar(Context context) {
        this(context, null);
    }

    public WxActionBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WxActionBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WxActionBar, defStyleAttr, 0);
        leftIcon = a.getDrawable(R.styleable.WxActionBar_wxleftIcon);
        titleString = a.getString(R.styleable.WxActionBar_wxtitleString);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        inflater = LayoutInflater.from(context);
        View headView = inflater.inflate(R.layout.wx_actionbar, null);
        addView(headView);
        leftIv = headView.findViewById(R.id.wxaction_back);
        titleTv = headView.findViewById(R.id.wxaction_title);
        leftIv.setImageDrawable(leftIcon);
        titleTv.setText(titleString);
        leftIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (headClickListener != null)
                    headClickListener.ClickLeft();
            }
        });
    }

    public void setLeftIcon(int resId) {
        this.leftIv.setImageResource(resId);
    }

    /**
     * 设置标题
     */
    public void setTitleString(String title) {
        if (title != null) {
            this.titleTv.setText(title);
        }
    }

    /**
     * 设置点击事件
     */
    public void setOnHeadClickListener(OnHeadClickListener listener) {
        this.headClickListener = listener;
    }

    public interface OnHeadClickListener {
        void ClickLeft();
    }
}
