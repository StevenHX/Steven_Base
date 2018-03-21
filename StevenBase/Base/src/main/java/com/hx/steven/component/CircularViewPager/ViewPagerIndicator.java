package com.hx.steven.component.CircularViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.hx.steven.R;
import java.util.ArrayList;

public class ViewPagerIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {

    //提示点之间的默认间隔
    private int defInterval = 8;
    private int selectImg;
    private int normalImg;
    //提示点之间的间隔
    private int interval;
    //提示点的数量,如果viewpager是无限轮询的话，这个值必须设置，默认大于50的话抛异常
    private int count;
    //与提示点绑定的ViewPager
    private ViewPager viewPager;
    //当前选中的Position
    private int selectPosition = 0;
    private ArrayList<ImageView> dotList;


    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);
        selectImg = R.drawable.dot_blue_bg;
        normalImg = R.drawable.dot_white_bg;
        interval = defInterval;
    }

    /**
     * 设置提示点被选中时的图片
     *
     * @param selectImgRes
     * @return
     */
    public ViewPagerIndicator setSelectImg(int selectImgRes) {
        selectImg = selectImgRes;
        return this;
    }

    /**
     * 设置提示点不被选中时的图片
     *
     * @param normalImgRes
     * @return
     */
    public ViewPagerIndicator setNormalImg(int normalImgRes) {
        normalImg = normalImgRes;
        return this;
    }

    /**
     * 设置各个提示点之间的间隔
     *
     * @param interval
     * @return
     */
    public ViewPagerIndicator setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    /**
     * 设置提示点数量，当ViewPager加载项为无限大时，这个方法必须执行
     *
     * @param count
     * @return
     */
    public ViewPagerIndicator setCount(int count) {
        this.count = count;
        return this;
    }

    /**
     * 提示点与ViewPager绑定
     *
     * @param viewPager
     * @return
     */
    public ViewPagerIndicator setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        selectPosition = viewPager.getCurrentItem();
        viewPager.addOnPageChangeListener(this);
        return this;
    }

    /**
     * 此方法在最后调用
     *
     * @return
     */
    public ViewPagerIndicator create() {
        if (viewPager == null) {
            throw new RuntimeException(this.getClass().getSimpleName() + "----the ViewPager have not to been set!");
        }
        if (viewPager.getAdapter().getCount() > 50 && count == 0) {
            throw new RuntimeException(this.getClass().getSimpleName() + "---the count of ViewPager is too large.Please reset the count!");
        }
        setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        createdotList();
        dotList.get(selectPosition).setImageResource(selectImg);
        return this;
    }

    private void createdotList() {
        if (dotList == null) {
            dotList = new ArrayList<>();
        } else {
            removeAllViews();
            dotList.clear();
        }
        if (count == 0) {
            count = viewPager.getAdapter().getCount();
        }
        for (int i = 0; i < count; i++) {
            dotList.add(creatdotList(i == (count - 1)));
        }
    }

    private ImageView creatdotList(boolean isLast) {
        ImageView iv = new ImageView(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (!isLast) {
            layoutParams.rightMargin = interval;
        }
        iv.setLayoutParams(layoutParams);
        iv.setImageResource(normalImg);
        addView(iv);
        return iv;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            int height = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View childView = getChildAt(i);
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) childView.getLayoutParams();
                int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, marginLayoutParams.width);
                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height);
                childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                int h = childView.getMeasuredHeight();
                if (height < h) {
                    height = h;
                }
            }
            height = height + getPaddingTop() + getPaddingBottom();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        //设置前一个提示点为非选中状态
        int currentposition = position % count;
        if (currentposition != selectPosition) {
            dotList.get(selectPosition).setImageResource(normalImg);
            dotList.get(currentposition).setImageResource(selectImg);
            selectPosition = currentposition;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
