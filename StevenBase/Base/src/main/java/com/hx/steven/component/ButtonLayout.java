package com.hx.steven.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.hx.steven.R;
import java.util.List;

/**
 * Created by Steven on 2018/1/20.
 */

public class ButtonLayout extends ViewGroup{
    private int horizontal_space;//横向间隔
    private int vertical_space;//竖向间隔
    public static final int BUTTON_TYPE = 1;
    public static final int TEXTVIEW_TYPE = 2;

    private int backgrund;//item背景
    private int textSize;//字体大小
    private ItemClickListener itemClickListener;//item点击回调
    public ButtonLayout(Context context) {
        super(context);
    }

    public ButtonLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ButtonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.ButtonLayout);
        horizontal_space = array.getDimensionPixelSize(R.styleable.ButtonLayout_horizontal_space,0);
        vertical_space = array.getDimensionPixelSize(R.styleable.ButtonLayout_vertical_space,0);
        array.recycle();
    }

    /**
     * 设置数据源
     * @param context
     * @param datas
     * @param type
     */

    public void setDatas(Context context,List<String> datas,int type){
        switch (type){
            case BUTTON_TYPE:
                for ( int i = 0; i < datas.size(); i++) {
                    Button btn = new Button(context);
                    btn.setText(datas.get(i));
                    btn.setBackgroundResource(backgrund);
                    btn.setTextSize(textSize);
                    final int position = i;
                    btn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            itemClickListener.itemClick(view,position);
                        }
                    });
                    addView(btn);
                }
                break;
            case TEXTVIEW_TYPE:
                for (int i = 0; i < datas.size(); i++) {
                    TextView  textView = new TextView(context);
                    textView.setText(datas.get(i));
                    textView.setBackgroundResource(backgrund);
                    textView.setTextSize(textSize);
                    addView(textView);
                }
                break;
        }
    }

    /**
     *设置item背景颜色
     * @param res
     */
    public void setItemBackground(int res){
            this.backgrund = res;
    }

    /**
     * 字体大小
     * @param size
     */
    public  void  setItemTextSize(int size){
        this.textSize = size;
    }

    public void setItemClickListener(ItemClickListener listener){
        this.itemClickListener = listener;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;
        /**
         * 记录每一行的宽度，width不断取最大宽度
         */
        int lineWidth = 0;
        /**
         * 每一行的高度，累加至height
         */
        int lineHeight = 0;

        int count = getChildCount();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        // 遍历每个子元素
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到child的lp
//            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            // 当前子空间实际占据的宽度
            int childWidth = child.getMeasuredWidth() + horizontal_space;
            // 当前子空间实际占据的高度
            int childHeight = child.getMeasuredHeight() + vertical_space;
            /**
             * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
             */
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(lineWidth, childWidth);// 取最大的
                lineWidth = childWidth; // 重新开启新行，开始记录
                // 叠加当前高度，
                height += lineHeight;
                // 开启记录下一行的高度
                lineHeight = childHeight;
                child.setTag(new Location(left, top + height, childWidth + left - horizontal_space, height + child.getMeasuredHeight() + top));
            } else {// 否则累加值lineWidth,lineHeight取最大高度
                child.setTag(new Location(lineWidth + left, top + height, lineWidth + childWidth - horizontal_space + left, height + child.getMeasuredHeight() + top));
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
        }
        width = Math.max(width, lineWidth) + getPaddingLeft() + getPaddingRight();
        height += lineHeight+getPaddingTop() + getPaddingBottom();
        sizeHeight += getPaddingTop() + getPaddingBottom();
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean c, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            Location location = (Location) child.getTag();
            child.layout(location.left, location.top, location.right, location.bottom);
        }
    }

    static class Location{
        public Location(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        private int left;
        private int top;
        private int right;
        private int bottom;
    }

    public   interface ItemClickListener{
        void itemClick(View view, int position);
    }
}
