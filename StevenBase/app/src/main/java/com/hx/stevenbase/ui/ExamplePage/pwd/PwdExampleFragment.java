package com.hx.stevenbase.ui.ExamplePage.pwd;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.hx.steven.component.PasswordView;
import com.hx.steven.fragment.BaseFragment;
import com.hx.stevenbase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PwdExampleFragment#} factory method to
 * create an instance of this fragment.
 */
public class PwdExampleFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.pwd_example_view)
    PasswordView pwdView;
    @Override
    protected int getContentId() {
        return R.layout.fragment_pwd_example;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        pwdView.setPasswordLength(6);
        pwdView.setMode(PasswordView.Mode.RECT);
        pwdView.setCipherEnable(false);
        pwdView.setCursorEnable(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}