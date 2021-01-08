package com.hx.stevenbase.ui.ExamplePage.progressDialog;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.hx.steven.component.MProgressDialog;
import com.hx.steven.fragment.BaseFragment;
import com.hx.stevenbase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressExampleFragment} factory method to
 * create an instance of this fragment.
 */
public class ProgressExampleFragment extends BaseFragment {
    Unbinder unbinder;
    @Override
    protected int getContentId() {
        return R.layout.fragment_progress_example;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @OnClick(R.id.btn_show_progress_dialog)
    public void onClick(View view) {
        MProgressDialog mProgressDialog = new MProgressDialog.Builder(getContext())
                .build();
        mProgressDialog.show("加载中。。。");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}