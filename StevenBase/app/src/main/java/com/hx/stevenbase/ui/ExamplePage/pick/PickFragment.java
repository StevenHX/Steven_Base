package com.hx.stevenbase.ui.ExamplePage.pick;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hx.steven.fragment.BaseFragment;
import com.hx.steven.util.TimeUtil;
import com.hx.steven.util.ToastUtil;
import com.hx.stevenbase.R;
import com.hx.stevenbase.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PickFragment#} factory method to
 * create an instance of this fragment.
 */
public class PickFragment extends BaseFragment {
    Unbinder unbinder;

    private List<String> options1Items = new ArrayList<>();

    @Override
    protected int getContentId() {
        return R.layout.fragment_pick;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        options1Items.add("北京烤鸭");
        options1Items.add("北京豆汁");
        options1Items.add("天津包子");
        options1Items.add("天津大麻花");
        options1Items.add("山东煎饼");
    }

    @OnClick({R.id.btn_option_select,R.id.btn_time_select})
    public void click(View view){
        switch (view.getId()){
            case R.id.btn_option_select:
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1);
//                                + options2Items.get(options1).get(option2)
//                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        ToastUtil.showToast(getActivity(),tx);
                    }
                }).build();
                pvOptions.setPicker(options1Items/*, options2Items, options3Items*/);
                pvOptions.show();
                break;
            case R.id.btn_time_select:
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        ToastUtil.showToast(getActivity(), TimeUtil.timeAgo(date));
                    }
                }).build();
                pvTime.show();
                break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}