package com.hx.stevenbase.ui.ExamplePage;

import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.AlphaInAnimation;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.DividerItemDecoration;
import com.hx.stevenbase.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExampleActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.example_recycle)
    RecyclerView recyclerView;
    private ExampleAdapter adapter;
    private List<ExampleBean> exampleData = new ArrayList<>();

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        exampleData.add(new ExampleBean("轮播图"));
        exampleData.add(new ExampleBean("Pick选项"));
        exampleData.add(new ExampleBean("FlowTag"));
        exampleData.add(new ExampleBean("ProgressDialog"));
        exampleData.add(new ExampleBean("手写签名"));
        exampleData.add(new ExampleBean("密码输入"));
        exampleData.add(new ExampleBean("弹窗"));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.addItemDecoration(new DividerItemDecoration().setDrawOuterBorder(true));
        adapter = new ExampleAdapter(R.layout.example_item, exampleData);
        adapter.setAdapterAnimation(new AlphaInAnimation());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_example;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        launch(this,bundle,ExampleDetailActivity.class);
    }
}