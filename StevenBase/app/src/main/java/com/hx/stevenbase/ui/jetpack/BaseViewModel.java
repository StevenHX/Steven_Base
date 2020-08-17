package com.hx.stevenbase.ui.jetpack;

import androidx.lifecycle.ViewModel;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class BaseViewModel<T extends BaseRepository> extends ViewModel {


    private T repository;

    public BaseViewModel(T repository) {
        this.repository = repository;
    }

    public T getRepository(){
        return repository;
    }

}
