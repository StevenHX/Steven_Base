package com.hx.stevenbase.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hx.steven.component.FlowTag.OnInitSelectedPosition;
import com.hx.stevenbase.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven on 2018/1/28.
 */

public class TagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {
    private final Context mContext;
    private final List<T> mDataList;

    public TagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2 = LayoutInflater.from(mContext).inflate(R.layout.tag_item, null);

        TextView textView = (TextView) view2.findViewById(R.id.tv_tag);
        T t = mDataList.get(i);

        if (t instanceof String) {
            textView.setText((String) t);
        }
        return view2;
    }

    public void onlyAddAll(List<T> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }
    @Override
    public boolean isSelectedPosition(int position) {
        return position%2 == 0;
    }
}
