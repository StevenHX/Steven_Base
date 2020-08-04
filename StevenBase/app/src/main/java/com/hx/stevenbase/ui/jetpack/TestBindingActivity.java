package com.hx.stevenbase.ui.jetpack;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.hx.stevenbase.R;
import com.hx.stevenbase.databinding.ActivityTestBindingBinding;

public class TestBindingActivity extends AppCompatActivity {
    ActivityTestBindingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            binding = DataBindingUtil.setContentView(this, R.layout.activity_test_binding);
            binding.setLifecycleOwner(this);
            binding.setTitle("hello world!!!!!");
    }
}