package com.hx.steven.component;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    //横向布局分割线
    public static final int HORIZONTAL_DIV = RecyclerView.HORIZONTAL;
    //纵向布局分割线
    public static final int VERTICAL_DIV = RecyclerView.VERTICAL;
    //表格布局分割线
    public static final int GRID_DIV = 2;
    private int mOrientation;
    private int mDividerWidth = 0;
    private Paint mPaint;

    private boolean isDrawOuterBorder = false;//默认不绘制边框
    private boolean hasTitle = false;//是否有标题

    /**
     * 默认纵向布分割线
     */
    public DividerItemDecoration() {
        this(VERTICAL_DIV);
    }

    /**
     * @param orientation 方向类型
     */
    public DividerItemDecoration(int orientation) {
        this(orientation, Color.parseColor("#EEEEEE"), 2);
    }

    /**
     * @param orientation 方向类型
     * @param color       分割线颜色
     * @param divWidth    分割线宽度
     */
    public DividerItemDecoration(int orientation, int color, int divWidth) {
        this.setOrientation(orientation);
        mDividerWidth = divWidth;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private int getRealOrientation(RecyclerView parent) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            return GRID_DIV;
        } else if (manager instanceof LinearLayoutManager) {
            int o = ((LinearLayoutManager) manager).getOrientation();
            if (o == RecyclerView.HORIZONTAL) {
                return HORIZONTAL_DIV;
            } else {
                return VERTICAL_DIV;
            }
        }
        return VERTICAL_DIV;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        mOrientation = getRealOrientation(parent);
        switch (mOrientation) {
            case HORIZONTAL_DIV:
                //横向布局分割线
                drawHorizontal(c, parent);
                break;
            case VERTICAL_DIV:
                //纵向布局分割线
                drawVertical(c, parent);
                break;
            case GRID_DIV:
                //表格格局分割线
                drawGrid(c, parent);
                break;
            default:
                //纵向布局分割线
                drawVertical(c, parent);
                break;
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int itemPosition = parent.getChildAdapterPosition(view);
        RecyclerView.Adapter mAdapter = parent.getAdapter();
        if (mAdapter != null) {
            int mChildCount = mAdapter.getItemCount();
            mOrientation = getRealOrientation(parent);
            switch (mOrientation) {
                case HORIZONTAL_DIV:
                    /**
                     * 横向布局分割线
                     * <p>
                     *     如果是第一个Item，则不需要分割线
                     * </p>
                     */
                    if (itemPosition != 0) {
                        outRect.set(mDividerWidth, 0, 0, 0);
                    }
                    break;
                case VERTICAL_DIV:
                    /**
                     * 纵向布局分割线
                     * <p>
                     *     如果是第一个Item，则不需要分割线
                     * </p>
                     */
                    if (itemPosition != 0) {
                        outRect.set(0, mDividerWidth, 0, 0);
                    }
                    break;
                case GRID_DIV:
                    /**
                     * 表格格局分割线
                     * <p>
                     *      1：当是第一个Item的时候，四周全部需要分割线
                     *      2：当是第一行Item的时候，需要额外添加顶部的分割线
                     *      3：当是第一列Item的时候，需要额外添加左侧的分割线
                     *      4：默认情况全部添加底部和右侧的分割线
                     * </p>
                     */
                    RecyclerView.LayoutManager mLayoutManager = parent.getLayoutManager();
                    if (mLayoutManager instanceof GridLayoutManager) {
                        GridLayoutManager mGridLayoutManager = (GridLayoutManager) mLayoutManager;
                        int mSpanCount = mGridLayoutManager.getSpanCount();
                        int top = 0, left = 0, right = 0, bottom = 0;
                        int realIndex = 0;
                        if (hasTitle) {
                            realIndex = itemPosition - 1;
                        }
                        int maxRow = mChildCount / mSpanCount + (mChildCount % mSpanCount == 0 ? 0 : 1);
                        int indexRow = (realIndex + 1) / mSpanCount + ((realIndex + 1) % mSpanCount == 0 ? 0 : 1);
                        if ((realIndex + 1) <= mSpanCount && isDrawOuterBorder) {
                            top = mDividerWidth;
                        }
                        if (((realIndex + mSpanCount) % mSpanCount) == 0 && isDrawOuterBorder) {
                            left = mDividerWidth;
                        }
                        if ((realIndex + 1) % mSpanCount != 0 || isDrawOuterBorder) {
                            right = mDividerWidth;
                        }
                        if (indexRow != maxRow || isDrawOuterBorder) {
                            bottom = mDividerWidth;
                        }
                        outRect.set(left, top, right, bottom);
                    }
                    break;
                default:
                    //纵向布局分割线
                    if (itemPosition != (mChildCount - 1)) {
                        outRect.set(0, 0, 0, mDividerWidth);
                    }
                    break;
            }
        }
    }

    /**
     * 绘制横向列表分割线
     *
     * @param c      绘制容器
     * @param parent RecyclerView
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int mChildCount = parent.getChildCount();
        for (int i = 0; i < mChildCount; i++) {
            if (i == 0 && !isDrawOuterBorder) continue;
            View mChild = parent.getChildAt(i);
            drawLeft(c, mChild, parent);
        }
    }

    /**
     * 绘制纵向列表分割线
     *
     * @param c      绘制容器
     * @param parent RecyclerView
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        int mChildCount = parent.getChildCount();
        for (int i = 0; i < mChildCount; i++) {
            if (i == 0 && !isDrawOuterBorder) continue;
            View mChild = parent.getChildAt(i);
            drawTop(c, mChild, parent);
        }
    }

    /**
     * 绘制表格类型分割线
     *
     * @param c      绘制容器
     * @param parent RecyclerView
     */
    private void drawGrid(Canvas c, RecyclerView parent) {
        int mChildCount = parent.getChildCount();
        for (int i = 0; i < mChildCount; i++) {
            View mChild = parent.getChildAt(i);
            RecyclerView.LayoutManager mLayoutManager = parent.getLayoutManager();
            if (mLayoutManager instanceof GridLayoutManager) {
                GridLayoutManager mGridLayoutManager = (GridLayoutManager) mLayoutManager;
                int mSpanCount = mGridLayoutManager.getSpanCount();
                int realIndex = 0;
                if (hasTitle) {
                    if (i == 0) {
                        drawBottom(c, mChild, parent);
                        continue;
                    }
                    realIndex = i - 1;
                }
                if ((realIndex + 1) <= mSpanCount && isDrawOuterBorder) {
                    drawTop(c, mChild, parent);
                }
                if (((realIndex + mSpanCount) % mSpanCount) == 0 && isDrawOuterBorder) {
                    drawLeft(c, mChild, parent);
                }
                if ((realIndex + 1) % mSpanCount != 0 || isDrawOuterBorder) {
                    drawRight(c, mChild, parent);
                }
                int maxRow = mChildCount / mSpanCount + (mChildCount % mSpanCount == 0 ? 0 : 1);
                int indexRow = (realIndex + 1) / mSpanCount + ((realIndex + 1) % mSpanCount == 0 ? 0 : 1);
                if (indexRow != maxRow || isDrawOuterBorder) {
                    drawBottom(c, mChild, parent);
                }
            }
        }
    }

    /**
     * 绘制右边分割线
     *
     * @param c            绘制容器
     * @param mChild       对应ItemView
     * @param recyclerView RecyclerView
     */
    private void drawLeft(Canvas c, View mChild, RecyclerView recyclerView) {
        RecyclerView.LayoutParams mChildLayoutParams = (RecyclerView.LayoutParams) mChild.getLayoutParams();
        int left = mChild.getLeft() - mDividerWidth - mChildLayoutParams.leftMargin;
        int top = mChild.getTop() - mChildLayoutParams.topMargin;
        int right = mChild.getLeft() - mChildLayoutParams.leftMargin;
        int bottom;
        if (isGridLayoutManager(recyclerView)) {
            bottom = mChild.getBottom() + mChildLayoutParams.bottomMargin + mDividerWidth;
        } else {
            bottom = mChild.getBottom() + mChildLayoutParams.bottomMargin;
        }
        c.drawRect(left, top, right, bottom, mPaint);
    }

    /**
     * 绘制顶部分割线
     *
     * @param c            绘制容器
     * @param mChild       对应ItemView
     * @param recyclerView RecyclerView
     */
    private void drawTop(Canvas c, View mChild, RecyclerView recyclerView) {
        RecyclerView.LayoutParams mChildLayoutParams = (RecyclerView.LayoutParams) mChild.getLayoutParams();
        int left;
        int top = mChild.getTop() - mChildLayoutParams.topMargin - mDividerWidth;
        int right = mChild.getRight() + mChildLayoutParams.rightMargin;
        int bottom = mChild.getTop() - mChildLayoutParams.topMargin;
        if (isGridLayoutManager(recyclerView)) {
            left = mChild.getLeft() - mChildLayoutParams.leftMargin - mDividerWidth;
        } else {
            left = mChild.getLeft() - mChildLayoutParams.leftMargin;
        }
        c.drawRect(left, top, right, bottom, mPaint);
    }

    /**
     * 绘制右边分割线
     *
     * @param c            绘制容器
     * @param mChild       对应ItemView
     * @param recyclerView RecyclerView
     */
    private void drawRight(Canvas c, View mChild, RecyclerView recyclerView) {
        RecyclerView.LayoutParams mChildLayoutParams = (RecyclerView.LayoutParams) mChild.getLayoutParams();
        int left = mChild.getRight() + mChildLayoutParams.rightMargin;
        int top;
        int right = left + mDividerWidth;
        int bottom = mChild.getBottom() + mChildLayoutParams.bottomMargin;
        if (isGridLayoutManager(recyclerView)) {
            top = mChild.getTop() - mChildLayoutParams.topMargin - mDividerWidth;
        } else {
            top = mChild.getTop() - mChildLayoutParams.topMargin;
        }
        c.drawRect(left, top, right, bottom, mPaint);
    }

    /**
     * 绘制底部分割线
     *
     * @param c            绘制容器
     * @param mChild       对应ItemView
     * @param recyclerView RecyclerView
     */
    private void drawBottom(Canvas c, View mChild, RecyclerView recyclerView) {
        RecyclerView.LayoutParams mChildLayoutParams = (RecyclerView.LayoutParams) mChild.getLayoutParams();
        int left = mChild.getLeft() - mChildLayoutParams.leftMargin;
        int top = mChild.getBottom() + mChildLayoutParams.bottomMargin;
        int bottom = top + mDividerWidth;
        int right;
        if (isGridLayoutManager(recyclerView)) {
            right = mChild.getRight() + mChildLayoutParams.rightMargin + mDividerWidth;
        } else {
            right = mChild.getRight() + mChildLayoutParams.rightMargin;
        }
        c.drawRect(left, top, right, bottom, mPaint);
    }

    /**
     * 判断RecyclerView所加载LayoutManager是否为GridLayoutManager
     *
     * @param recyclerView RecyclerView
     * @return 是GridLayoutManager返回true，否则返回false
     */
    private boolean isGridLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager mLayoutManager = recyclerView.getLayoutManager();
        return (mLayoutManager instanceof GridLayoutManager);
    }

    /**
     * 初始化分割线类型
     *
     * @param orientation 分割线类型
     */
    public void setOrientation(int orientation) {
        if (mOrientation != HORIZONTAL_DIV && mOrientation != VERTICAL_DIV && mOrientation != GRID_DIV) {
            throw new IllegalArgumentException("MyDividerItemDecoration：分割线类型设置异常");
        } else {
            this.mOrientation = orientation;
        }
    }

    /**
     * 是否绘制外边框
     *
     * @param isDrawOuterBorder
     * @return
     */
    public DividerItemDecoration setDrawOuterBorder(boolean isDrawOuterBorder) {
        this.isDrawOuterBorder = isDrawOuterBorder;
        return this;
    }

    public DividerItemDecoration setHasTitle(boolean hasTitle) {
        this.hasTitle = hasTitle;
        return this;
    }
}
