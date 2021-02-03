package com.hx.stevenbase.ui.ExamplePage.form;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.hx.steven.fragment.BaseFragment;
import com.hx.steven.util.AppUtils;
import com.hx.stevenbase.R;
import com.hx.stevenbase.component.FormContainerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormExampleFragment#} factory method to
 * create an instance of this fragment.
 */
public class FormExampleFragment extends BaseFragment {

    private FormContainerView formContainerView;

    @Override
    protected int getContentId() {
        return R.layout.fragment_form_example;
    }

    @Override
    protected void initView(View view) {
        formContainerView = view.findViewById(R.id.form_container);
        try {
            formContainerView.init(AppUtils.getFromAssets(getContext(), "form_example.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}