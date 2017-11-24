package com.hx.steven.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.hx.steven.R;

/**
 * Created by user on 2017/11/24.
 */

public class MultipleStatusView extends RelativeLayout{
    private static final RelativeLayout.LayoutParams DEFAULT_LAYOUT_PARAMS = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    public static final int STATUS_CONTENT    = 0x00;
    public static final int STATUS_LOADING    = 0x01;
    public static final int STATUS_EMPTY      = 0x02;
    public static final int STATUS_ERROR      = 0x03;
    public static final int STATUS_NO_NETWORK = 0x04;
    private static final int NULL_RESOURCE_ID = -1;

    private View mEmptyView;
    private View mErrorView;
    private View mLoadingView;
    private View mNoNetworkView;
    private View mContentView;
    private int  mEmptyViewResId;
    private int  mErrorViewResId;
    private int  mLoadingViewResId;
    private int  mNoNetworkViewResId;
    private int  mContentViewResId;

    private LayoutInflater mInflater;
    private OnClickListener mOnRetryClickListener;

    public MultipleStatusView(Context context) {
        super(context);
    }

    public MultipleStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultipleStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultipleStatusView, defStyleAttr, 0);
        mEmptyViewResId = a.getResourceId(R.styleable.MultipleStatusView_emptyView, R.layout.empty_view);
        mErrorViewResId = a.getResourceId(R.styleable.MultipleStatusView_errorView, R.layout.error_view);
        mLoadingViewResId = a.getResourceId(R.styleable.MultipleStatusView_loadingView, R.layout.loading_view);
        mNoNetworkViewResId = a.getResourceId(R.styleable.MultipleStatusView_noNetworkView, R.layout.no_network_view);
        mContentViewResId = a.getResourceId(R.styleable.MultipleStatusView_contentView, NULL_RESOURCE_ID);
        a.recycle();
        mInflater = LayoutInflater.from(getContext());
    }
}
