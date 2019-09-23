package com.hx.steven.baseView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * activity 装饰抽象父类
 * @author huangxiao
 */
public abstract class DecoratorActivity extends AppCompatActivity implements IBaseView{
    /**
     * view接口
     */
    protected IBaseView iBaseView;

    public DecoratorActivity(IBaseView iBaseView){
        this.iBaseView = iBaseView;
    }


}
