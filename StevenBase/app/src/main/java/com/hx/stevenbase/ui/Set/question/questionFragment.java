package com.hx.stevenbase.ui.Set.question;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hx.steven.fragment.BaseFragment;
import com.hx.stevenbase.R;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class questionFragment extends BaseFragment {

    @BindView(R.id.recycler_question)
    RecyclerView recyclerQuestion;
    @BindView(R.id.refreshLayout_question)
    TwinklingRefreshLayout refreshLayoutQuestion;
    Unbinder unbinder;

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);

    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_question;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
