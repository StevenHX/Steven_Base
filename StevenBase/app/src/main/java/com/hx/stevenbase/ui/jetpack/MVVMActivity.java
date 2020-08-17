package com.hx.stevenbase.ui.jetpack;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public abstract class MVVMActivity<VM extends ViewModel,VDB extends ViewDataBinding> extends BindingActivity<VDB>{

    protected VM mViewModel;

    @Override
    public void initView() {
        mViewModel = createViewModel();
        super.initView();
    }

    public <T extends ViewModel> T getViewModel(@NonNull Class<T> modelClass,BaseRepository repository){
        MyViewModelFactory factory = new MyViewModelFactory(repository);
        return new ViewModelProvider(this,factory).get(modelClass);
    }

    public <T extends ViewModel> T getViewModel(@NonNull Class<T> modelClass, @NonNull ViewModelProvider.Factory factory){
        return new ViewModelProvider(this,factory).get(modelClass);
    }


    protected abstract VM createViewModel();

}
