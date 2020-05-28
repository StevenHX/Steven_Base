package com.hx.stevenbase.ui.Welcome;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.Transition;
import com.hx.stevenbase.R;
import com.hx.stevenbase.ui.main.MainActivity;
import com.hx.stevenbase.ui.main.WebActivity;

public class WelcomeActivity extends AppCompatActivity {
    private ImageView launch_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        launch_img = findViewById(R.id.launch_img);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        Glide.with(this).load("http://p2.img.cctvpic.com/uploadimg/2020/05/27/1590545538454562.gif")
                .transition(DrawableTransitionOptions.with(
                        new DrawableCrossFadeFactory.Builder(500).setCrossFadeEnabled(true).build()
                ))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.launch_ad_bg);
                        layerDrawable.setDrawableByLayerId(R.id.iv_launch_top, resource);
                        launch_img.setBackground(layerDrawable);
                    }
                });

        Handler handler = new Handler();
        handler.postDelayed(this::openMain, 6000);
    }

    private void openMain() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
}
