package com.hx.steven.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hx.steven.R;

/**
 *
 * Created by Steven on 2018/3/2.
 */

public class HeaderView extends LinearLayout implements View.OnClickListener {
    /**数据*/
    private String leftString;//左侧文字
    private int leftTvColor;//左侧文字颜色
    private boolean showLeftTv;//显示左侧文字

    private Drawable leftIcon;//左侧图案
    private boolean showLeftIcon;//显示左侧图标

    private String titleString;//标题文字
    private int titleTvColor;//标题颜色
    private  boolean showTitle;//显示标题

    private String rightString;//右侧文字
    private int rightTvColor;//右侧颜色
    private boolean showRightTv;//显示右侧文字

    private Drawable rightIcon;//右侧图标
    private boolean showRIghtIcon;//显示右侧图标

    /**控件*/
    private LayoutInflater inflater;

    private ImageView leftIv;//left_icon
    private TextView leftTv;//左边文字控件
    private TextView titleTv;//title控件
    private TextView rightTv;//右侧文字控件
    private ImageView rightIv;//右侧图片控件
    private OnHeadClickListener headClickListener;

    public HeaderView(Context context) {
        this(context,null);
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HeaderView, defStyleAttr, 0);
        leftString = a.getString(R.styleable.HeaderView_leftString);
        leftTvColor = a.getColor(R.styleable.HeaderView_leftColor, Color.DKGRAY);
        showLeftTv = a.getBoolean(R.styleable.HeaderView_showLeftTv,true);
        leftIcon = a.getDrawable(R.styleable.HeaderView_leftIcon);
        showLeftIcon = a.getBoolean(R.styleable.HeaderView_showLeftIcon,false);
        titleString = a.getString(R.styleable.HeaderView_titleString);
        titleTvColor = a.getColor(R.styleable.HeaderView_titleColor,Color.BLACK);
        showTitle = a.getBoolean(R.styleable.HeaderView_showTitle,true);
        rightString = a.getString(R.styleable.HeaderView_rightString);
        rightTvColor = a.getColor(R.styleable.HeaderView_rightColor,Color.DKGRAY);
        showRightTv = a.getBoolean(R.styleable.HeaderView_showRightTv,false);
        rightIcon = a.getDrawable(R.styleable.HeaderView_rightIcon);
        showRIghtIcon = a.getBoolean(R.styleable.HeaderView_showRightIcon,false);
        a.recycle();
        init(context);
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        inflater = LayoutInflater.from(context);
        View headView = inflater.inflate(R.layout.activity_base_header,null);
        addView(headView);
        leftTv =  headView.findViewById(R.id.header_left);
        leftIv = headView.findViewById(R.id.header_left_icon);
        titleTv = headView.findViewById(R.id.header_title);
        rightTv = headView.findViewById(R.id.header_right);
        rightIv = headView.findViewById(R.id.header_right_icon);

        /**设置左侧文字*/
        leftTv.setText(leftString);
        leftTv.setTextColor(leftTvColor);
        leftTv.setVisibility(showLeftTv?VISIBLE:INVISIBLE);
        /**设置左侧图标*/
        leftIv.setImageDrawable(leftIcon);
        leftIv.setVisibility(showLeftIcon?VISIBLE:GONE);
        /**设置标题*/
        titleTv.setText(titleString);
        titleTv.setTextColor(titleTvColor);
        titleTv.setVisibility(showTitle?VISIBLE:INVISIBLE);
        /**设置右侧文字*/
        rightTv.setText(rightString);
        rightTv.setTextColor(rightTvColor);
        rightTv.setVisibility(showRightTv?VISIBLE:INVISIBLE);
        /**设置右侧图标*/
        rightIv.setImageDrawable(rightIcon);
        rightIv.setVisibility(showRIghtIcon?VISIBLE:GONE);

        leftTv.setOnClickListener(this);
        rightTv.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.header_left) {
            headClickListener.ClickLeft();
        } else if (i == R.id.header_right) {
            headClickListener.ClickRight();
        }
    }

    public interface OnHeadClickListener{
        void ClickLeft();
        void ClickRight();
    }
    /**
     * ===================================public方法================================================
     * */
    /**设置左侧文字*/
    public void setLeftString(String text){
        this.leftTv.setText(text);
    }
    /**设置左侧文字颜色*/
    public void setLeftTvColor(int leftTvColor){
        this.leftTv.setTextColor(leftTvColor);
    }
    /**设置左侧文字是否显示*/
    public void setShowLeftTv(boolean showLeftTv){
        this.leftTv.setVisibility(showLeftTv?VISIBLE:INVISIBLE);
    }
    /**设置左侧图案*/
    public void setLeftIcon(int resId){
        this.leftIv.setImageResource(resId);
    }
    /**设置左侧图标是否显示*/
    public void setShowLeftIcon(boolean showLeftIcon){
        this.leftIv.setVisibility(showLeftIcon?VISIBLE:GONE);
    }
    /**设置标题*/
    public void setTitleString(String title){
        this.titleTv.setText(title);
    }
    /**设置标题颜色*/
    public void setTitleTvColor(int titleTvColor){
        this.titleTv.setTextColor(titleTvColor);
    }
    /**设置标题是否显示*/
    public void setShowTitle(boolean showTitle){
        this.titleTv.setVisibility(showTitle?VISIBLE:INVISIBLE);
    }
    /**设置右侧文字*/
    public void setRightString(String text){
        this.rightTv.setText(text);
    }
    /**设置右侧文字颜色*/
    public void setRightTvColor(int rightTvColor){
        this.rightTv.setTextColor(rightTvColor);
    }
    /**设置右侧文字是否显示*/
    public void setShowRightTv(boolean showRightTv){
        this.rightTv.setVisibility(showRightTv?VISIBLE:INVISIBLE);
    }
    /**设置右侧图标*/
    public void setRightIcon(int resId){
        this.rightIv.setImageResource(resId);
    }
    /**设置右侧图标是否显示*/
    public void setShowRIghtIcon(boolean showRIghtIcon){
        this.rightIv.setVisibility(showRIghtIcon?VISIBLE:GONE);
    }
    /**设置点击事件*/
    public void setOnHeadClickListener(OnHeadClickListener listener){
        this.headClickListener = listener;
    }
}
