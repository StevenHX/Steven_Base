package com.hx.stevenbase.ui.Set.about;

import androidx.fragment.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hx.steven.Mvp.BaseMvpModel;
import com.hx.steven.fragment.BaseMvpFragment;
import com.hx.stevenbase.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class aboutFragment extends BaseMvpFragment<aboutPresenter> implements aboutContract.View {
    {
        setEnableMultiple(false);
    }
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
    public List<BaseMvpModel> createModels() {
        return null;
    }

    @Override
    public void initMvpView(View view) {
        unbinder = ButterKnife.bind(this, view);
        aboutDto = new aboutDto();
    }

    @Override
    protected int getContentId() {
        return R.layout.about_fragment;
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
