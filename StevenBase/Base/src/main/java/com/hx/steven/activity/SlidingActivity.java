package com.hx.steven.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hx.steven.component.swipeback.SlidingLayout;

public class SlidingActivity extends AppCompatActivity {
    private boolean enableSliding = true;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (enableSliding()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
    }

    protected boolean enableSliding() {
        return enableSliding;
    }
    protected void setEnableSliding(boolean enableSliding){
        this.enableSliding = enableSliding;
    }
}
