package com.example.mvvmrecycler.viewmodel;

import android.content.Context;
import android.os.Handler;
import android.widget.Button;

import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.adapter.RvAdapter;
import com.example.mvvmrecycler.datamodel.DynamicDataModel;
import com.example.mvvmrecycler.databinding.MainActivityBinding;
import com.example.mvvmrecycler.datamodel.StaticDataModel;
import com.example.mvvmrecycler.tools.Function;
import com.example.mvvmrecycler.view.MainActivity;

import java.util.ArrayList;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainViewModel extends ViewModel implements DynamicDataModel.GetArrayList, DynamicDataModel.GetAdapter {

    public ObservableField<String> ovfNumber;
    public ObservableBoolean isLoading;
    private ArrayList<MainBean> arrView = new ArrayList<>();
    public String strTitle, strUrl, strRefresh, strIncrease;
    private RvAdapter adapter;
    private int dataSize;
    private Function function;

    public void initView(){

        function = new Function();

        ovfNumber = new ObservableField<>();

        isLoading = new ObservableBoolean(false);

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

    public void getData(Handler handler, Runnable runDel, MainActivity activity, MainActivityBinding binding, Context context){

        if(arrView.size() > 0)
            arrView = new ArrayList<>();
        DynamicDataModel dynamicDataModel = new DynamicDataModel();
        dynamicDataModel.getData(handler, runDel, activity, binding, context, this, this, function);

    }

    public void setRv(RecyclerView recyclerView) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

    }

    public void setBtnClick(Handler handler, Runnable runDel, MainActivity mainActivity, MainActivityBinding binding, Context context, Button btnIncrease, Button btnRefresh){

        btnIncrease.setEnabled(false);
        btnRefresh.setEnabled(false);

        btnIncrease.setOnClickListener(v -> {

            adapter.addItem(dataSize);
            function.arrMainCompare(arrView);

        });

        btnIncrease.setOnLongClickListener(v -> {

            adapter.addItem(dataSize);
            function.arrMainCompare(arrView);

            return false;

        });

        btnRefresh.setOnClickListener(v -> {

            btnIncrease.setEnabled(false);
            btnRefresh.setEnabled(false);
            getData(handler, runDel, mainActivity, binding, context);

            //context.sendBroadcast(new Intent(FLAG_MAIN));

        });

        btnRefresh.setOnLongClickListener(v -> {

            btnIncrease.setEnabled(false);
            btnRefresh.setEnabled(false);
            getData(handler, runDel, mainActivity, binding, context);

            //context.sendBroadcast(new Intent(FLAG_MAIN));

            return false;

        });

    }

    @Override
    public void addArr(ArrayList<MainBean> arr) {
        arrView = arr;
        dataSize = arr.size();
    }

    @Override
    public void addRvAdapter(RvAdapter rvAdapter) {
        adapter = rvAdapter;
        dataSize = adapter.getItemCount();
    }

}