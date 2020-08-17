package com.hx.stevenbase.ui.jetpack;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TestRepository extends BaseRepository {
    MutableLiveData<String> strData;
    public LiveData<String> getTestInfo() {
        return strData;
    }
    public void setTestInfo(String info) {
        if (strData ==null) {
            strData = new MutableLiveData<>();
        }
        strData.setValue(info);
    }
}
