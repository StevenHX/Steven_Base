package com.hx.steven.fragment;


import android.os.Bundle;

import com.orhanobut.logger.Logger;

public abstract class BaseLazyFragment extends BaseFragment {
    private boolean isPrepared;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }


    /**
     * 第一次onResume中的调用onUserVisible避免操作与onFirstUserVisible操作重复
     */
    private boolean isFirstResume = true;

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
                Logger.e("onUserVisible()");
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
                Logger.e("onFirstUserInvisible()");
            } else {
                onUserInvisible();
                Logger.e("onUserInvisible()");
            }
        }
    }

    public synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
            Logger.e("onFirstUserVisible()");
        } else {
            isPrepared = true;
        }
    }


    /**
     * 第一次fragment可见（进行初始化工作）
     */
    public abstract void onFirstUserVisible();

    /**
     * fragment可见（切换回来或者onResume）
     */
    public abstract void onUserVisible();

    /**
     * 第一次fragment不可见（不建议在此处理事件）
     */
    public abstract void onFirstUserInvisible() ;

    /**
     * fragment不可见（切换掉或者onPause）
     */
    public abstract void onUserInvisible();




}
