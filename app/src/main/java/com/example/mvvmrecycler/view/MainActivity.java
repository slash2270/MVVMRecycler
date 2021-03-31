package com.example.mvvmrecycler.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.mvvmrecycler.R;
import com.example.mvvmrecycler.viewmodel.MainViewModel;
import com.example.mvvmrecycler.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;

    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        model = new MainViewModel();

        model.initView(this);

        model.setTitle();

        model.getData(binding);

        model.setBtn(binding);

        binding.setModel(model);

    }

}