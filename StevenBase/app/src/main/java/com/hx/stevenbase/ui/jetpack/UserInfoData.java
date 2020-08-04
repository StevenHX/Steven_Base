package com.hx.stevenbase.ui.jetpack;

import androidx.lifecycle.LiveData;

public class UserInfoData extends LiveData<UserInfoData> {
    String name;
    int age;

    public UserInfoData(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        postValue(this);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        postValue(this);
    }

}
