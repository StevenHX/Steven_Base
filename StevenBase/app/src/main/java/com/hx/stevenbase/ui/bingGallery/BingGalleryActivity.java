package com.hx.stevenbase.ui.bingGallery;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hx.steven.Mvvm.BaseMVVMActivity;
import com.hx.stevenbase.R;
import com.hx.stevenbase.databinding.ActivityBingGalleryBinding;

public class BingGalleryActivity extends BaseMVVMActivity<ActivityBingGalleryBinding, ImageViewModel> {
    @Override
    protected int getContentId() {
        return R.layout.activity_bing_gallery;
    }

    @Override
    public Class<ImageViewModel> getViewModelClass() {
        return ImageViewModel.class;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showProgressDialog("加载中");

        getmViewModel().getImage().observe(this, imagesBeanData -> {
            if (imagesBeanData.getErrorMsg() != null) {
                Toast.makeText(BingGalleryActivity.this, imagesBeanData.getErrorMsg(), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
                return;
            }
            getmBinding().setImageBean(imagesBeanData.getData());
            setTitle(imagesBeanData.getData().getCopyright());
            dismissProgressDialog();
        });

        getmBinding().setPresenter(new Presenter());

        showProgressDialog("加载中");
        getmViewModel().LoadImage();
        getLifecycle().addObserver(new MyObserver());
    }

    public class Presenter {

        public void onClick(View view) {
            showProgressDialog("加载中");
            switch (view.getId()) {
                case R.id.btn_load:
                    getmViewModel().LoadImage();
                    break;
                case R.id.btn_previous:
                    getmViewModel().previousImage();
                    break;
                case R.id.btn_next:
                    getmViewModel().nextImage();
                    break;
                default:
                    break;
            }
        }

    }
}
