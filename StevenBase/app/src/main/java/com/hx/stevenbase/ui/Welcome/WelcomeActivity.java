package com.hx.stevenbase.ui.Welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.gyf.barlibrary.FlymeOSStatusBarFontUtils;
import com.hx.steven.activity.BaseActivity;
import com.hx.steven.util.BarColorUtils;
import com.hx.stevenbase.R;
import com.hx.stevenbase.ui.main.MainActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        Handler handler = new Handler();
        handler.postDelayed(this::openMain, 3000);
    }

    private void openMain() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
}
