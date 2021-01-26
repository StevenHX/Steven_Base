package com.hx.stevenbase.ui.Login;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.Button;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hx.steven.Mvp.BaseMvpModel;
import com.hx.steven.Mvp.BaseMvpActivity;
import com.hx.steven.component.ClearEditText;
import com.hx.stevenbase.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {
    {
        setEnableMultiple(true);
    }
    @BindView(R.id.login_container)
    ConstraintLayout loginBg;
    @BindView(R.id.login_icon)
    ImageView loginIcon;
    @BindView(R.id.login_pwd)
    ClearEditText loginPwd;
    @BindView(R.id.login_name)
    ClearEditText loginName;
    @BindView(R.id.login_ok)
    Button loginOk;

    private LoginDto loginDto;

    @Override
    protected int getContentId() {
        return R.layout.login_activity;
    }


    @Override
    public void loginSuccess(LoginBean loginBean) {

    }

    @Override
    public void loginFail(String msg) {
        
    }

    @Override
    public void initMvpView() {
        ButterKnife.bind(this);
        loginDto = new LoginDto();
        loginName.setShakeAnimation();
        loginBg.setBackground(new BitmapDrawable(blur(BitmapFactory.decodeResource(getResources(),R.drawable.sea),20f)));
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public List<BaseMvpModel> createModels() {
        List<BaseMvpModel> models = new ArrayList<>();
        models.add(new LoginModel());
        return models;
    }

    @OnClick(R.id.login_ok)
    public void onViewClicked() {
        loginDto.setMobile(loginName.getText().toString());
        loginDto.setPassword(loginPwd.getText().toString());
        loginDto.setSimulator(false);

        mPresenter.loginRequest(loginDto);
    }

    private Bitmap blur(Bitmap bitmap, float radius) {
        Bitmap output = Bitmap.createBitmap(bitmap);
        RenderScript rs = RenderScript.create(this);// 构建一个RenderScript对象
        ScriptIntrinsicBlur gaussianBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)); // 创建高斯模糊脚本
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);//创建用于输入的脚本类型
        Allocation allOut = Allocation.createFromBitmap(rs, output); // 创建用于输出的脚本类型
        gaussianBlur.setRadius(radius); // 设置模糊半径，范围0f<radius<=25f
        gaussianBlur.setInput(allIn); // 设置输入脚本类型
        gaussianBlur.forEach(allOut); // 执行高斯模糊算法，并将结果填入输出脚本类型中
        allOut.copyTo(output); // 将输出内存编码为Bitmap，图片大小必须注意
        rs.destroy(); // 关闭RenderScript对象，API>=23则使用rs.releaseAllContexts()
        return output;
    }
}
