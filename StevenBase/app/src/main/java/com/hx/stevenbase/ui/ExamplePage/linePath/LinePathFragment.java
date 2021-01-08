package com.hx.stevenbase.ui.ExamplePage.linePath;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.hx.steven.fragment.BaseFragment;
import com.hx.stevenbase.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LinePathFragment} factory method to
 * create an instance of this fragment.
 */
public class LinePathFragment extends BaseFragment {
    Unbinder unbinder;
    @Override
    protected int getContentId() {
        return R.layout.fragment_line_path;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}