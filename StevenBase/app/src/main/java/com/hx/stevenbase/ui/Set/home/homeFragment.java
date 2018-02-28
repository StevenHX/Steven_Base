package com.hx.stevenbase.ui.Set.home;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hx.steven.fragment.BaseFragment;
import com.hx.steven.fragment.BaseMvpFragment;
import com.hx.steven.fragment.BaseMvpLazyFragment;
import com.hx.stevenbase.R;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends BaseMvpLazyFragment<homePresenter,homeContract.View> implements homeContract.View{

    @BindView(R.id.recycler_question)
    RecyclerView recyclerQuestion;
    @BindView(R.id.refreshLayout_question)
    TwinklingRefreshLayout refreshLayoutQuestion;
    Unbinder unbinder;

    private int page = 0;
    private homeAdapter adapter;//首页文章适配器

    @Override
    protected void initMvpLazyFragment(View view) {
        unbinder = ButterKnife.bind(this, view);
        recyclerQuestion.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    protected homePresenter createPresenter() {
        return new homePresenter();
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

    /**
     *=======================================回调方法=======================================================
     */

    @Override
    public void homeSuccess(homeBean home) {
        getMultipleStatusView().showContent();
        adapter = new homeAdapter(R.layout.home_item,home.getData().getDatas());
        recyclerQuestion.setAdapter(adapter);
    }

    @Override
    public void homeFail(String msg) {
        getMultipleStatusView().showError();
        dismissLoding();
    }

    @Override
    public void onFirstUserVisible() {
        Logger.e("onFirstUserVisible()");
        getMultipleStatusView().showLoading();
        mPresenter.HomeArticlesRequest(0);
    }

    @Override
    public void onUserVisible() {
        Logger.e("onUserVisible()");
    }

    @Override
    public void onFirstUserInvisible() {
        Logger.e("onFirstUserInvisible()");
    }

    @Override
    public void onUserInvisible() {
        Logger.e("onUserInvisible()");
    }
}
