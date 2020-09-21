package com.hx.stevenbase.ui.bingGallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hx.stevenbase.R;
import com.hx.stevenbase.databinding.ActivityBingGalleryBinding;

public class BingGalleryActivity extends FragmentActivity {
    private ActivityBingGalleryBinding mBinding;
    private ImageViewModel mViewModel;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bing_gallery);
        mViewModel = new ViewModelProvider(
                this, new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(ImageViewModel.class);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("加载中");

        mViewModel.getImage().observe(this, new Observer<Data<ImageBean.ImagesBean>>() {
            @Override
            public void onChanged(@Nullable Data<ImageBean.ImagesBean> imagesBeanData) {
                if (imagesBeanData.getErrorMsg() != null) {
                    Toast.makeText(BingGalleryActivity.this, imagesBeanData.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                    return;
                }
                mBinding.setImageBean(imagesBeanData.getData());
                setTitle(imagesBeanData.getData().getCopyright());
                mProgressDialog.dismiss();
            }
        });

        mBinding.setPresenter(new Presenter());

        mProgressDialog.show();
        mViewModel.LoadImage();
        getLifecycle().addObserver(new MyObserver());
    }

    public class Presenter {

        public void onClick(View view) {
            mProgressDialog.show();
            switch (view.getId()) {
                case R.id.btn_load:
                    mViewModel.LoadImage();
                    break;
                case R.id.btn_previous:
                    mViewModel.previousImage();
                    break;
                case R.id.btn_next:
                    mViewModel.nextImage();
                    break;
                default:
                    break;
            }
        }

    }
}
