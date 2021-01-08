package com.hx.stevenbase.ui.ExamplePage;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hx.stevenbase.R;

import java.util.List;

public class ExampleAdapter extends BaseQuickAdapter<ExampleBean, BaseViewHolder> {


    public ExampleAdapter(int layoutResId, @Nullable List<ExampleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExampleBean item) {
        helper.setText(R.id.example_item_name,item.getName());
        helper.setImageResource(R.id.example_item_img,R.drawable.example_img);
    }
}
