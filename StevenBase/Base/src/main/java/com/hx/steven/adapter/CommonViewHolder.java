package com.hx.steven.adapter;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommonViewHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> mViews;

    private View mConvertView;

    /**
     * 私有构造方法
     *
     * @param itemView
     */
    private CommonViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static CommonViewHolder create(Context context, int layoutId, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new CommonViewHolder(itemView);
    }

    public static CommonViewHolder create(View itemView) {
        return new CommonViewHolder(itemView);
    }

    /**
     * 通过id获得控件
     *
     * @param viewId 内部View id
     * @param <T>    Item View Class类型
     * @return Item View
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 获取Item View
     *
     * @return Item View
     */
    public View getConvertView() {
        return mConvertView;
    }
}
