package com.hx.steven.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.hx.steven.R;
import com.hx.steven.util.AppUtils;
import com.hx.steven.util.UnitConverter;


public class ClearEditText extends AppCompatEditText implements
        OnFocusChangeListener, TextWatcher {
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFocus;

    private float mDrawableWidth;
    private float mDrawableHeight;
    private float mDrawableScale;
    private int textSize;
    private int textColor;
    private int textHintColor;
    private int background;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLeftDrawable(context, attrs);
        initClearDrawable();
    }

    private void initLeftDrawable(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);
        mDrawableWidth = typedArray.getDimension(R.styleable.ClearEditText_drawableWidth, UnitConverter.dpToPx(20));
        mDrawableHeight = typedArray.getDimension(R.styleable.ClearEditText_drawableHeight, UnitConverter.dpToPx(20));
        mDrawableScale = typedArray.getFloat(R.styleable.ClearEditText_drawableScale, 0);
        textSize = typedArray.getDimensionPixelSize(R.styleable.ClearEditText_textSize, AppUtils.sp2px(context, 14));
        textColor = typedArray.getColor(R.styleable.ClearEditText_textColor, ContextCompat.getColor(context, R.color.dimgray));
        textHintColor = typedArray.getColor(R.styleable.ClearEditText_textHintColor, ContextCompat.getColor(context, R.color.gray_8f));
        background = typedArray.getResourceId(R.styleable.ClearEditText_background, 0);
        typedArray.recycle();

        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        setTextColor(textColor);
        setPadding(UnitConverter.dpToPx(5), 0, UnitConverter.dpToPx(10), 0);
        setHintTextColor(textHintColor);
        if (background != 0)
            setBackground(ContextCompat.getDrawable(getContext(), background));

        Drawable leftDrawable = getCompoundDrawables()[0];
        if (leftDrawable == null) {
            return;
        }
        int width = leftDrawable.getIntrinsicWidth();
        int height = leftDrawable.getIntrinsicWidth();
        Rect rect = new Rect();
        if (mDrawableScale != 0.0f) {
            rect.right = (int) (width * mDrawableScale);
            rect.bottom = (int) (height * mDrawableScale);
            leftDrawable.setBounds(rect);
            return;
        }

        if (mDrawableWidth != 0.0f && mDrawableHeight != 0.0f) {
            rect.right = (int) mDrawableWidth;
            rect.bottom = (int) mDrawableHeight;
            leftDrawable.setBounds(rect);
            return;
        }

    }


    private void initClearDrawable() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
//        	throw new NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = ContextCompat.getDrawable(getContext(), R.drawable.input_del);
        }


        mClearDrawable.setBounds(UnitConverter.spToPx(5), 0, mClearDrawable.getIntrinsicWidth() + UnitConverter.spToPx(5), mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);


    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        if (hasFocus) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }


    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public void setUnEdit() {
        setFocusable(false);
        setEnabled(false);
        setKeyListener(null);
    }

}
