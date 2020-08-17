package com.hx.stevenbase.ui.jetpack;

import androidx.lifecycle.LiveData;

public class TestViewModel<T extends BaseRepository> extends BaseViewModel<T> {


    public TestViewModel(T repository) {
        super(repository);
    }

    public LiveData<String> getTestInfo() {
        return ((TestRepository)getRepository()).getTestInfo();
    }
    public void setTestInfo(String info) {
        ((TestRepository)getRepository()).setTestInfo(info);
    }
}
