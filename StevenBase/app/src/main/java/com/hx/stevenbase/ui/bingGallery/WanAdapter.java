package com.hx.stevenbase.ui.bingGallery;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hx.stevenbase.R;
import com.hx.stevenbase.databinding.WanRecycleItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Steven on 2018/2/28.
 */

public class WanAdapter extends BaseQuickAdapter<WanBean.DatasBean, WanBindViewHolder> {

    public WanAdapter(int layoutResId, @Nullable List<WanBean.DatasBean> data) {
        super(layoutResId, data);
        addChildClickViewIds(R.id.ivCollect, R.id.tvTitle);
    }

    @Override
    protected void convert(@NotNull WanBindViewHolder wanBindViewHolder, WanBean.DatasBean datasBean) {
        WanRecycleItemBinding binding = wanBindViewHolder.getBinding();
        binding.setWan(datasBean);
        binding.executePendingBindings();
    }

//    @Override
//    protected void convert(@NotNull BaseViewHolder helper, WanBean.DatasBean item) {
//        helper.setText(R.id.tvAuthor, item.getAuthor());
//        helper.setText(R.id.tvNiceDate, item.getNiceDate());
//        helper.setText(R.id.tvTitle, item.getTitle());
//        helper.setText(R.id.tvChapterName, item.getChapterName());
//        helper.setImageResource(R.id.ivCollect, item.isCollect()
//                ? R.drawable.home_recycle_item_icon_like : R.drawable.home_recycle_item_icon_nolike);
//    }
}
