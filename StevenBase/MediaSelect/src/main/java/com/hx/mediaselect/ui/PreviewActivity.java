package com.hx.mediaselect.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.hx.mediaselect.R;
import com.hx.mediaselect.constract.Code;
import com.hx.mediaselect.model.Photo;
import com.hx.mediaselect.ui.adapter.PreviewPhotosAdapter;

import java.util.ArrayList;

/**
 * 预览页
 */
public class PreviewActivity extends AppCompatActivity implements PreviewPhotosAdapter.OnClickListener, View.OnClickListener {
    private static final String TAG = "PreviewActivity";
    private ArrayList<Photo> photos = new ArrayList<>();
    private int previewIndex;

    public static void start(Activity act, ArrayList<Photo> photos, int index) {
        Intent intent = new Intent(act, PreviewActivity.class);
        intent.putParcelableArrayListExtra("photos", photos);
        intent.putExtra("index", index);
        act.startActivityForResult(intent, Code.REQUEST_PREVIEW_ACTIVITY);
    }

    private MyRecycleView vp2;
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
        previewPhotosAdapter = new PreviewPhotosAdapter(this, photos, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        vp2.setLayoutManager(linearLayoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(vp2);
        vp2.setAdapter(previewPhotosAdapter);
    }

    private void initData() {
        Intent intent = getIntent();
        photos = intent.getParcelableArrayListExtra("photos");
        previewIndex = intent.getIntExtra("index", 0);
        previewPhotosAdapter.setPhotos(photos);
        vp2.scrollToPosition(previewIndex);
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
