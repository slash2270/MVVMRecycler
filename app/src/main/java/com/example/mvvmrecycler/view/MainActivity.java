package com.example.mvvmrecycler.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.mvvmrecycler.R;
import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.viewmodel.MainViewModel;

import static com.example.mvvmrecycler.tools.Constant.DATABASE_NAME;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.mvvmrecycler.databinding.MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        MainViewModel model = new MainViewModel();

        model.initView();
        model.setTitleBtn();
        model.setRv(binding);
        model.getData(binding, this, getApplicationContext());
        model.setBtnClick(binding, this, getApplicationContext());

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