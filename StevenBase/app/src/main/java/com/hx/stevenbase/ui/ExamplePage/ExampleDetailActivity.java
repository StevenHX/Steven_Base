package com.hx.stevenbase.ui.ExamplePage;

import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;

import com.hx.steven.activity.BaseActivity;
import com.hx.stevenbase.R;
import com.orhanobut.logger.Logger;

public class ExampleDetailActivity extends BaseActivity {
    @Override
    protected void initView() {
        int position = getIntent().getIntExtra("position",0);
        Logger.d(position);
        NavController navHostController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavGraph navGraph = navHostController.getNavInflater().inflate(R.navigation.nav_graph);
        switch (position){
            case 0:
                navGraph.setStartDestination(R.id.banner_example_fragment);
                break;
            case 1:
                navGraph.setStartDestination(R.id.pick_example_fragment);
                break;
            case 2:
                navGraph.setStartDestination(R.id.flow_tag_example_fragment);
                break;
            case 3:
                navGraph.setStartDestination(R.id.progress_example_fragment);
                break;
            case 4:
                navGraph.setStartDestination(R.id.line_path_example_fragment);
                break;
            case 5:
                navGraph.setStartDestination(R.id.pwd_example_fragment);
                break;
            case 6:
                navGraph.setStartDestination(R.id.dialog_example_fragment);
                break;
            case 8:
                navGraph.setStartDestination(R.id.form_example_fragment);
            default:
                break;
        }
        navHostController.setGraph(navGraph);

        // 跳转到secondFragment
//        navHostController.navigate(R.id.dialog_example_fragment);
        // 返回上一个Fragment
//        navHostController.navigateUp();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_example_detail;
    }
}