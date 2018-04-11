package com.hx.stevenbase.ui.Set.me;


import android.view.View;
import android.widget.ImageView;

import com.hx.steven.component.BannerView.BannerView;
import com.hx.steven.component.SubmitButton;
import com.hx.steven.fragment.BaseFragment;
import com.hx.stevenbase.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author huangxiao
 * @date 2018.3.22
 */
public class MeFragment extends BaseFragment {
    {
        setEnableMultiple(false);
    }
    /**
     * butterKnife解绑对象
     */
    Unbinder unbinder;
    /**
     * banner控件
     */
    @BindView(R.id.bannerView_me)
    BannerView mBannerViewMe;
    /**
     * 登录按钮
     */
    @BindView(R.id.sbBtn_login)
    SubmitButton mSbBtnLogin;

    private List<View> mImageViews = new ArrayList<>();
    private int[] imgs = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.c, R.drawable.c};

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        initData();
        mSbBtnLogin.setOnSBBtnClickListener(new SubmitButton.SBBtnClickListerner() {
            @Override
            public void clickSubmitButton(View view) {
                mBannerViewMe.stopAutoPlay();
            }
        });
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mImageViews.clear();
        for (int img : imgs) {
            //新建ImageView,并添加到集合中
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(img);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageViews.add(imageView);
        }
        mBannerViewMe.setView(mImageViews)
                .startAutoPlay(3000);
    }
}
