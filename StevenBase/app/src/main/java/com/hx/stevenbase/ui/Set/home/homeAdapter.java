package com.hx.stevenbase.ui.Set.home;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hx.stevenbase.R;

import java.util.List;

/**
 * Created by Steven on 2018/2/28.
 */

public class homeAdapter extends BaseQuickAdapter<homeBean.DatasBean, BaseViewHolder> {


    public homeAdapter(int layoutResId, @Nullable List<homeBean.DatasBean> data) {
        super(layoutResId, data);
        addChildClickViewIds(R.id.ivCollect, R.id.tvTitle);
    }

    @Override
    protected void convert(BaseViewHolder helper, homeBean.DatasBean item) {
        helper.setText(R.id.tvAuthor, item.getAuthor());
        helper.setText(R.id.tvNiceDate, item.getNiceDate());
        helper.setText(R.id.tvTitle, item.getTitle());
        helper.setText(R.id.tvChapterName, item.getChapterName());
        helper.setImageResource(R.id.ivCollect, item.isCollect()
                ? R.drawable.home_recycle_item_icon_like : R.drawable.home_recycle_item_icon_nolike);
    }
}
