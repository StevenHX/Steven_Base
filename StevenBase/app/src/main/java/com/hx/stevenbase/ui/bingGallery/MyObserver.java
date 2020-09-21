package com.hx.stevenbase.ui.bingGallery;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.orhanobut.logger.Logger;


public class MyObserver implements LifecycleObserver {
    private static final String TAG = "MyObserver";
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Logger.d(TAG,"-------onResume----");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Logger.d(TAG,"-------onPause----");
    }

}
