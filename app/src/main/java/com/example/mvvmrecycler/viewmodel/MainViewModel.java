package com.example.mvvmrecycler.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.adapter.RvAdapter;
import com.example.mvvmrecycler.datamodel.DataModel;
import com.example.mvvmrecycler.databinding.MainActivityBinding;
import com.example.mvvmrecycler.tools.Function;

import java.util.ArrayList;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.example.mvvmrecycler.data.DBConstant.DATABASE_NAME;

public class MainViewModel extends ViewModel{

    public ObservableField<String> ovfNumber;
    public ObservableField<String> ovfTitle;
    public ObservableField<String> ovfUrl;
    public ObservableField<String> ovfRefresh;
    public ObservableField<String> ovfIncrease;
    public ObservableBoolean isLoading;

    private Function function;

    private Context context;

    private DBManager dbManager;

    private SQLiteDatabase db;

    private DataModel dataModel;

    private ArrayList<MainBean> arrView;
    private RvAdapter adapter;

    private int arrViewSize;

    public void initView(Activity activity){

        context = activity.getApplicationContext();

        dataModel = new DataModel();
        dbManager = new DBManager();

        ovfNumber = new ObservableField<>();
        ovfTitle = new ObservableField<>();
        ovfUrl= new ObservableField<>();
        ovfRefresh = new ObservableField<>();
        ovfIncrease = new ObservableField<>();

        isLoading = new ObservableBoolean(false);

        function = new Function();

        arrView = new ArrayList<>();

    }

    public void setTitleBtn(){

        isLoading.set(true);

        dataModel.dataTitleBtn(new DataModel.dataCallBack() {
            @Override
            public void number(String number) {

                ovfNumber.set(number);

            }

            @Override
            public void title(String title) {

                ovfTitle.set(title);

            }

            @Override
            public void url(String url) {

                ovfUrl.set(url);

            }

            @Override
            public void increase(String increase) {

                ovfIncrease.set(increase);

            }

            @Override
            public void refresh(String refresh) {

                ovfRefresh.set(refresh);

            }
        });

        isLoading.set(false);

    }

    public void setBtnClick(MainActivityBinding binding){

        binding.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter.addItem(arrViewSize);

            }
        });

        binding.btnIncrease.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                adapter.addItem(arrViewSize);

                return false;
            }
        });

        binding.btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbManager.deleteDb(context, DATABASE_NAME);
                getData(binding);

            }
        });

        binding.btnRefresh.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                dbManager.deleteDb(context, DATABASE_NAME);
                getData(binding);

                return false;

            }
        });
    }

    public void getData(MainActivityBinding binding){

        if(arrViewSize > 0){

            arrView.clear();

        }

        dataModel.getData(new DataModel.jsonData() {

            @Override
            public RvAdapter addRvAdapter(RvAdapter rvAdapter) {

                adapter = rvAdapter;

                return adapter;
            }

            @Override
            public int addArrSize(int arrSize) {

                arrViewSize = arrSize;

                return arrViewSize;
            }

        },binding, context, arrView);

    }

    public void setRv(MainActivityBinding binding) {

        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(binding.rv.getContext()));

    }

}
