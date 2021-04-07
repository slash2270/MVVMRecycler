package com.example.mvvmrecycler.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.mvvmrecycler.R;
import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.datamodel.DataModel;
import com.example.mvvmrecycler.viewmodel.MainViewModel;
import com.example.mvvmrecycler.databinding.MainActivityBinding;

import static com.example.mvvmrecycler.data.DBConstant.DATABASE_NAME;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;

    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        model = new MainViewModel();

        model.initView(this);

        model.setTitleBtn();

        model.setRv(binding);

        model.getData(binding);

        model.setBtnClick(binding);

        binding.setModel(model);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        delDb();

    }

    private void delDb(){

        DBManager dbManager = new DBManager();
        dbManager.deleteDb(getApplicationContext(), DATABASE_NAME);

    }

}