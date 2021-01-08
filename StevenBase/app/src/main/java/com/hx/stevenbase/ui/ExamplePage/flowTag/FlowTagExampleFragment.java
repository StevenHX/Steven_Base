package com.hx.stevenbase.ui.ExamplePage.flowTag;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.hx.steven.component.FlowTag.FlowTagLayout;
import com.hx.steven.fragment.BaseFragment;
import com.hx.stevenbase.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FlowTagExampleFragment#} factory method to
 * create an instance of this fragment.
 */
public class FlowTagExampleFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.example_flow_layout)
    FlowTagLayout example_flow_layout;
    TagAdapter<String> tagAdapter;
    private List<String> tagsData;
    @Override
    protected int getContentId() {
        return R.layout.fragment_flow_tag_example;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        tagAdapter = new TagAdapter<String>(getContext());
        example_flow_layout.setAdapter(tagAdapter);
        tagsData = new ArrayList<>();
        tagsData.add("北京公主坟");
        tagsData.add("天津");
        tagsData.add("山东");
        tagsData.add("河北");
        tagsData.add("河南");
        tagsData.add("江苏-苏州-金鸡湖");
        tagsData.add("上海南京路");
        tagsData.add("浙江");
        tagsData.add("福建");
        tagsData.add("江西景德镇");
        tagsData.add("广东");
        tagsData.add("云南");
        tagAdapter.onlyAddAll(tagsData);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}