package com.hx.stevenbase.ui.ExamplePage.dialog;

import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.hx.steven.fragment.BaseFragment;
import com.hx.stevenbase.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DialogExampleFragment} factory method to
 * create an instance of this fragment.
 */
public class DialogExampleFragment extends BaseFragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.btn_confirm)
    AppCompatButton confirmBtn;
    @Override
    protected int getContentId() {
        return R.layout.fragment_dialog_example;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        confirmBtn.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                BasePopupView popupView = new XPopup.Builder(getActivity())
                        .dismissOnBackPressed(false)
//                        .navigationBarColor(Color.BLUE)
//                        .hasBlurBg(true)
                        .dismissOnTouchOutside(false)
                        .autoDismiss(false)
                        .popupAnimation(PopupAnimation.NoAnimation)
                        .asConfirm("哈哈", "床前明月光，疑是地上霜；举头望明月，低头思故乡。",
                                "取消", "确定",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {

                                    }
                                }, null, false);
                popupView.show();
                break;
            default:
                break;
        }
    }
}