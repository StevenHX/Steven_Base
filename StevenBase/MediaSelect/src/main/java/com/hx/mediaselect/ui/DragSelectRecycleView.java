package com.hx.mediaselect.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义滑动多选recycleView
 */
public class DragSelectRecycleView extends RecyclerView {
    public interface  DispatchTouchEventListener {
        void action(MotionEvent ev);
    }
    private DispatchTouchEventListener dispatchTouchEventListener;
    public void setDispatchTouchEventListener(DispatchTouchEventListener dispatchTouchEventListener) {
        this.dispatchTouchEventListener = dispatchTouchEventListener;
    }
    public DragSelectRecycleView(@NonNull Context context) {
        super(context);
    }

    public DragSelectRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragSelectRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (dispatchTouchEventListener != null) {
            dispatchTouchEventListener.action(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
