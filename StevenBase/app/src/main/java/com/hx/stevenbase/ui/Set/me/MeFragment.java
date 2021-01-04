package com.hx.stevenbase.ui.Set.me;


import android.view.View;

import com.hx.steven.fragment.BaseFragment;
import com.hx.stevenbase.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author huangxiao
 * @date 2018.3.22
 */
public class MeFragment extends BaseFragment {
    {
        setEnableMultiple(false);
    }
    /**
     * butterKnife解绑对象
     */
    Unbinder unbinder;

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected int getContentId() {
        return R.layout.me_fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
