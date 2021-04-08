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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.example.mvvmrecycler.data.DBConstant.DATABASE_NAME;

public class MainViewModel extends ViewModel implements DataModel.GetAdapterSize{

    public ObservableField<String> ovfNumber;
    public ObservableField<String> ovfTitle;
    public ObservableField<String> ovfUrl;
    public ObservableField<String> ovfRefresh;
    public ObservableField<String> ovfIncrease;

    public ObservableBoolean isLoading;

    private Context context;

    private DBManager dbManager;

    private SQLiteDatabase db;

    private DataModel dataModel;

    private ArrayList<MainBean> arrView; //和arrAdapter屬於同步性質
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

        arrView = new ArrayList<>();

    }

    public void setTitleBtn(){

        isLoading.set(true);

        dataModel.setTextTitleBtn(new DataModel.SetTextTitleBtn() {
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

    public ArrayList<MainBean> getData(MainActivityBinding binding){

        arrView.clear();

        dataModel.getData(this,binding, context, arrView);

        return arrView;

    }

    public void setRv(MainActivityBinding binding) {

        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(binding.rv.getContext()));

    }

    @Override
    public RvAdapter addRvAdapter(RvAdapter rvAdapter) { adapter = rvAdapter;return adapter; }

    @Override
    public int addArrSize(int arrSize) { arrViewSize = arrSize;return arrViewSize; }

    public void setBtnClick(MainActivityBinding binding){

        binding.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter.addItem(arrViewSize);

                Collections.sort(arrView, new Comparator<MainBean>() { // o1-o2小於 o2-o1大於 重新排序adapter裡的position
                    @Override
                    public int compare(MainBean o1, MainBean o2) {
                        int i = o1.getId() - o2.getId();
                        if(i == 0){
                            return o1.getId() - o2.getId();
                        }
                        return i;
                    }

                    @Override
                    public boolean equals(Object obj) {
                        return false;
                    }
                });

            }
        });

        binding.btnIncrease.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                adapter.addItem(arrViewSize);

                Collections.sort(arrView, new Comparator<MainBean>() {
                    @Override
                    public int compare(MainBean o1, MainBean o2) {
                        int i = o1.getId() - o2.getId();
                        if(i == 0){
                            return o1.getId() - o2.getId();
                        }
                        return i;
                    }

                    @Override
                    public boolean equals(Object obj) {
                        return false;
                    }
                });

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

}