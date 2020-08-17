package com.hx.stevenbase.ui.jetpack;

import androidx.lifecycle.Observer;

import com.hx.stevenbase.R;
import com.hx.stevenbase.databinding.ActivityTestBindingBinding;
import com.orhanobut.logger.Logger;

public class TestBindingActivity extends MVVMActivity<TestViewModel,ActivityTestBindingBinding> {

    @Override
    public void initView() {
        super.initView();
        mViewModel.setTestInfo("11111111111111");
        mViewModel.getTestInfo().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Logger.e(s);
            }
        });
    }

    @Override
    protected TestViewModel createViewModel() {
        return getViewModel(TestViewModel.class,new TestRepository());
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_test_binding;
    }
}