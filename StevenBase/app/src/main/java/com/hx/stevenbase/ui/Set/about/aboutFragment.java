package com.hx.stevenbase.ui.Set.about;

import android.Manifest;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.hx.steven.fragment.BaseMvpFragment;
import com.hx.steven.util.MPermissionUtil;
import com.hx.stevenbase.R;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class aboutFragment extends BaseMvpFragment<aboutPresenter, aboutContract.View> implements aboutContract.View {

    @BindView(R.id.about_name)
    EditText aboutName;
    @BindView(R.id.about_pwd)
    EditText aboutPwd;
    @BindView(R.id.about_ok)
    Button aboutOk;
    Unbinder unbinder;
    private aboutDto aboutDto;
    @Override
    protected aboutPresenter createPresenter() {
        return new aboutPresenter();
    }

    @Override
    public void initMvpView(View view) {
        unbinder = ButterKnife.bind(this, view);
        aboutDto = new aboutDto();


        MPermissionUtil.requestPermissionsResult(this, 0, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new MPermissionUtil.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                Logger.d(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
            }

            @Override
            public void onPermissionDenied() {
                MPermissionUtil.showTipsDialog(context);
            }
        });
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_about;
    }

    @Override
    public void aboutSuccess(aboutBean about) {
       dismissLoding();
    }

    @Override
    public void aboutFail(String msg) {
       dismissLoding();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.about_ok)
    public void onViewClicked() {
        aboutDto.setMobile(aboutName.toString());
        aboutDto.setPassword(aboutPwd.toString());
        aboutDto.setSimulator(false);
        showProgressDialog("加载中");
        mPresenter.aboutRequest(aboutDto);
    }
}
