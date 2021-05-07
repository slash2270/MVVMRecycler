package com.example.mvvmrecycler.viewmodel;

import android.app.Activity;
import android.content.Context;

import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.adapter.RvAdapter;
import com.example.mvvmrecycler.datamodel.DynamicDataModel;
import com.example.mvvmrecycler.databinding.MainActivityBinding;
import com.example.mvvmrecycler.datamodel.StaticDataModel;

import java.util.ArrayList;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.example.mvvmrecycler.tools.Constant.DATABASE_NAME;
import static com.example.mvvmrecycler.tools.Function.arrMainCompare;

public class MainViewModel extends ViewModel implements DynamicDataModel.GetAdapterSize{

    public ObservableField<String> ovfNumber;

    public ObservableBoolean isLoading;

    private DBManager dbManager;

    private ArrayList<MainBean> arrView; //和arrAdapter屬於同步性質
    private RvAdapter adapter;
    public String strTitle, strUrl, strRefresh, strIncrease;
    private int arrViewSize;

    public void initView(){

        dbManager = new DBManager();

        ovfNumber = new ObservableField<>();

        isLoading = new ObservableBoolean(false);

        arrView = new ArrayList<>();

    }

    public void setTitleBtn(){

        isLoading.set(true);

        StaticDataModel staticDataModel = new StaticDataModel();
        staticDataModel.setTextTitleBtn(new StaticDataModel.SetTextTitleBtn() {
            @Override
            public void number(String number) { ovfNumber.set(number); }

            @Override
            public String title(String title) { strTitle = title;return strTitle; }

            @Override
            public void url(String url) { strUrl = url; }

            @Override
            public void increase(String increase) { strIncrease = increase; }

            @Override
            public void refresh(String refresh) { strRefresh = refresh; } });

        isLoading.set(false);

    }

    public void getData(MainActivityBinding binding, Activity activity, Context context){

        if(arrView.size() > 0){ arrView = new ArrayList<>(); }

        DynamicDataModel dynamicDataModel = new DynamicDataModel(context,this, binding, activity, arrView);
        dynamicDataModel.execute();

    }

    public void setRv(MainActivityBinding binding) {

        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(binding.rv.getContext()));

    }

    @Override
    public void addRvAdapter(RvAdapter rvAdapter) { adapter = rvAdapter; }

    @Override
    public void addArrSize(int arrSize) { arrViewSize = arrSize; }

    public void setBtnClick(MainActivityBinding binding, Activity activity, Context context){

            binding.btnIncrease.setOnClickListener(v -> {

                adapter.addItem(arrViewSize);

                arrMainCompare(arrView);

            });

            binding.btnIncrease.setOnLongClickListener(v -> {

                adapter.addItem(arrViewSize);

                arrMainCompare(arrView);

                return false;
            });

            binding.btnRefresh.setOnClickListener(v -> {

                dbManager.deleteDb(context, DATABASE_NAME);
                getData(binding, activity, context);

            });

            binding.btnRefresh.setOnLongClickListener(v -> {

                dbManager.deleteDb(context, DATABASE_NAME);
                getData(binding, activity, context);

                return false;

            });

    }

}