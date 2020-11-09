package com.hx.mediaselect.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.hx.mediaselect.R;
import com.hx.mediaselect.constract.Code;
import com.hx.mediaselect.constract.Setting;
import com.hx.mediaselect.ui.adapter.PreviewPhotosAdapter;

import java.util.ArrayList;

/**
 * 预览页
 */
public class PreviewActivity extends AppCompatActivity implements PreviewPhotosAdapter.OnClickListener, View.OnClickListener{
    private static final String TAG = "PreviewActivity";
    private ArrayList<Photo> photos = new ArrayList<>();
    private int previewIndex;

    public static void start(Activity act, ArrayList<Photo> photos,int index) {
        Intent intent = new Intent(act, PreviewActivity.class);
        intent.putParcelableArrayListExtra("photos",photos);
        intent.putExtra("index",index);
        act.startActivityForResult(intent, Code.REQUEST_PREVIEW_ACTIVITY);
    }

    private ViewPager2 vp2;
    private PreviewPhotosAdapter previewPhotosAdapter;
    private AppCompatImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_easy_photos);
        ivBack = findViewById(R.id.iv_back);
        vp2 = findViewById(R.id.vp2);
        ivBack.setOnClickListener(this);
        initView();
        initData();
    }


    private void initView() {
        previewPhotosAdapter = new PreviewPhotosAdapter(this,photos,this);
        vp2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        vp2.setAdapter(previewPhotosAdapter);
    }

    private void initData() {
        Intent intent = getIntent();
        photos = intent.getParcelableArrayListExtra("photos");
        previewIndex = intent.getIntExtra("index",0);
        previewPhotosAdapter.setPhotos(photos);
        vp2.setCurrentItem(previewIndex);
        previewPhotosAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPhotoClick() {

    }

    @Override
    public void onPhotoScaleChanged() {

    }

    private void doBack() {
        Intent intent = new Intent();
        intent.putExtra(Code.PREVIEW_CLICK_BACK_KEY, false);
        setResult(Code.PREVIEW_CLICK_BACK_CODE, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.iv_back == id) {
            doBack();
        }
    }
}
