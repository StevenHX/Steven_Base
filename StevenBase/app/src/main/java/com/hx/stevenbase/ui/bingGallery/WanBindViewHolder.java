package com.hx.stevenbase.ui.bingGallery;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hx.stevenbase.databinding.WanRecycleItemBinding;

import org.jetbrains.annotations.NotNull;

public class WanBindViewHolder extends BaseViewHolder {
    WanRecycleItemBinding binding = null;

    public WanBindViewHolder(@NotNull View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
    }

    public WanRecycleItemBinding getBinding() {
        return binding;
    }
}
