package com.hx.stevenbase.ui.Set.home;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hx.stevenbase.R;

import java.util.List;
/**
 *
 * Created by Steven on 2018/2/28.
 */

public class homeAdapter extends BaseQuickAdapter<homeBean.DatasBean,BaseViewHolder>{


    public homeAdapter(int layoutResId, @Nullable List<homeBean.DatasBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, homeBean.DatasBean item) {
        helper.setText(R.id.tvAuthor,item.getAuthor());
        helper.setText(R.id.tvNiceDate,item.getNiceDate());
        helper.setText(R.id.tvTitle,item.getTitle());
        helper.setText(R.id.tvChapterName,item.getChapterName());
        helper.setImageResource(R.id.ivCollect, item.isCollect()
                ? R.drawable.ic_action_like : R.drawable.ic_action_no_like);
    }
}
