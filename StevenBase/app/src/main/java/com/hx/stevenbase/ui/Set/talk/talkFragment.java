package com.hx.stevenbase.ui.Set.talk;


import android.view.View;

import androidx.fragment.app.Fragment;

import com.hx.steven.fragment.BaseLazyFragment;
import com.hx.steven.util.AppUtils;
import com.hx.stevenbase.R;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class talkFragment extends BaseLazyFragment {

    {
        setEnableMultiple(false);
    }

    private Unbinder unbinder;

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected int getContentId() {
        return R.layout.talk_fragment;
    }

    @Override
    public void onFirstUserVisible() {
    }

    @Override
    public void onUserVisible() {
        Logger.d("screenWidth=" + AppUtils.getScreenWidth(context) + ",screenHeight=" + AppUtils.getScreenHeight(context));
    }

    @Override
    public void onFirstUserInvisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
