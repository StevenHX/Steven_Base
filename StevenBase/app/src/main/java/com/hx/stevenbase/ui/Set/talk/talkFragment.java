package com.hx.stevenbase.ui.Set.talk;


import android.support.v4.app.Fragment;
import android.view.View;

import com.hx.steven.fragment.BaseLazyFragment;
import com.hx.stevenbase.R;
import com.orhanobut.logger.Logger;

/**
 * A simple {@link Fragment} subclass.
 */
public class talkFragment extends BaseLazyFragment {
    {
        setEnableMultiple(false);
    }
    Thread mThread;
    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getContentId() {
        return R.layout.talk_fragment;
    }

    public void getData(){
        for (int i = 0; i <100 ; i++) {
            Logger.d("i="+i);
        }
    }

    @Override
    public void onFirstUserVisible() {
//        mThread = new Thread(this::getData);
//        mThread.start();
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onFirstUserInvisible() {

    }

    @Override
    public void onUserInvisible() {

    }
}
