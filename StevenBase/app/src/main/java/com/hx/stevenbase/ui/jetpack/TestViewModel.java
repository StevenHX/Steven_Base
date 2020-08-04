package com.hx.stevenbase.ui.jetpack;

import androidx.lifecycle.ViewModel;

public class TestViewModel extends ViewModel {
    UserInfoData userInfoData = new UserInfoData("张三",43);

    public UserInfoData getUserInfo() {
        return userInfoData;
    }
}
