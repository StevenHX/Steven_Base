package com.hx.stevenbase.ui.Welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.hx.steven.component.SuperTextView;
import com.hx.stevenbase.R;
import com.hx.stevenbase.ui.main.WebActivity;

public class WelcomeActivity extends AppCompatActivity {
//    private ImageView launch_img;
//    private TextView jumpAd_tv;
    private SuperTextView tvName;
    private Handler handler;

    Runnable runnable = this::openMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
//        launch_img = findViewById(R.id.launch_img);
//        jumpAd_tv = findViewById(R.id.jumpAd);
        tvName = findViewById(R.id.tvName);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

//        Glide.with(this).load("http://p2.img.cctvpic.com/uploadimg/2020/05/27/1590545538454562.gif")
//                .transition(DrawableTransitionOptions.with(
//                        new DrawableCrossFadeFactory.Builder(500).setCrossFadeEnabled(true).build()
//                ))
//                .into(new SimpleTarget<Drawable>() {
//                    @Override
//                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                        LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(WelcomeActivity.this, R.drawable.launch_ad_bg);
//                        layerDrawable.setDrawableByLayerId(R.id.iv_launch_top, resource);
//                        launch_img.setBackground(layerDrawable);
//                    }
//                });
//
//        handler = new Handler();
//        handler.postDelayed(runnable, 6000);
//        jumpAd_tv.setOnClickListener(v -> openMain());

        Animation anim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.splash);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
               tvName.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                openMain();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tvName.startAnimation(anim);
    }

    private void openMain() {
        Intent intent = new Intent(WelcomeActivity.this, WebActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
//        handler.removeCallbacks(runnable);
    }
}
