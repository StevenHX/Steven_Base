package com.hx.steven.Mvvm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class BaseViewModel<T> extends ViewModel {

    private HashMap<String, MutableLiveData<T>> liveDataList;

    public BaseViewModel() {
    }

    public void addLiveData(String key, MutableLiveData<T> liveData) {
        if (liveDataList == null) liveDataList = new HashMap<>();
        liveDataList.put(key, liveData);
    }

    public MutableLiveData<T> getLiveData(String key) {
        if (liveDataList != null) return liveDataList.get(key);
        return null;
    }
}
