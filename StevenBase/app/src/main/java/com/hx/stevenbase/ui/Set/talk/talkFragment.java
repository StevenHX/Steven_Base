package com.hx.stevenbase.ui.Set.talk;


import android.support.v4.app.Fragment;
import android.view.View;
import com.hx.steven.fragment.BaseFragment;
import com.hx.stevenbase.R;
import com.orhanobut.logger.Logger;

/**
 * A simple {@link Fragment} subclass.
 */
public class talkFragment extends BaseFragment {
    {
        setEnableMultiple(false);
    }
    Thread mThread;
    @Override
    protected void initView(View view) {
        mThread = new Thread(this::getData);
        mThread.start();
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_talk;
    }

    public void getData(){
        for (int i = 0; i <100 ; i++) {
            Logger.d("i="+i);
        }
    }
}
