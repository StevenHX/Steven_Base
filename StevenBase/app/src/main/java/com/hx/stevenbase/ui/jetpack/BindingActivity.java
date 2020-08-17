package com.hx.stevenbase.ui.jetpack;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.hx.steven.activity.BaseActivity;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public abstract class BindingActivity<VDB extends ViewDataBinding> extends BaseActivity {

    protected VDB mBinding;

    @Override
    public void initView() {
        mBinding = DataBindingUtil.setContentView(this,getContentId());
    }

    protected abstract @LayoutRes
    int getContentId();
}
