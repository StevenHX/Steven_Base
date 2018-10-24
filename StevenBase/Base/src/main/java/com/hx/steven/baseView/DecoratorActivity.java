package com.hx.steven.baseView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
