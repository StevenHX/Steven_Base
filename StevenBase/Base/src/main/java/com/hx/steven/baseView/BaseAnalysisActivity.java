package com.hx.steven.baseView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseAnalysisActivity extends DecoratorActivity {

    public BaseAnalysisActivity(IBaseView iBaseView) {
        super(iBaseView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showLoading(String msg) {
        showLoading(msg);
    }

    @Override
    public void dismissLoading() {
        dismissLoading();
    }
}
