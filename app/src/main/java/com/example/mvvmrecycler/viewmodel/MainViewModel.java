package com.example.mvvmrecycler.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.mvvmrecycler.R;
import com.example.mvvmrecycler.data.DataBean;
import com.example.mvvmrecycler.adapter.RvAdapter;
import com.example.mvvmrecycler.datamodel.DataModel;
import com.example.mvvmrecycler.databinding.MainActivityBinding;

import java.util.ArrayList;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainViewModel extends ViewModel{

    public final ObservableField<String> ovfName = new ObservableField<>();
    public final ObservableField<String> ovfNumber = new ObservableField<>();
    public final ObservableField<String> ovfContent = new ObservableField<>();

    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private final DataModel dataModel = new DataModel();

    private ArrayList<DataBean> arrBean;

    private RvAdapter adapter;

    public String refresh, increase;

    private Context context;

    public void initView(Activity activity){

        context = activity.getApplicationContext();

    }

    public void setTitle(){

        isLoading.set(true);

        dataModel.retrieveData(new DataModel.dataCallBack() {
            @Override
            public void nameData(String name) {

                ovfName.set(name);

            }

            @Override
            public void numberData(String number) {

                ovfNumber.set(number);

            }

            @Override
            public void contentData(String content) {

                ovfContent.set(content);

            }
        });

        isLoading.set(false);

    }

    public void setBtn(MainActivityBinding binding){

        refresh = "刷新";
        increase = "增加";

   /*     binding.btnIncrease.setOnClickListener(this);
        binding.btnIncrease.setOnLongClickListener(this);
        binding.btnRefresh.setOnClickListener(this);
        binding.btnRefresh.setOnLongClickListener(this);  */

    }

    private ArrayList<DataBean> setData(){

        arrBean = new ArrayList<>();
        dataModel.setRvData(arrBean);

        return arrBean;

    }

    public void getData(MainActivityBinding binding) {

        binding.mainRv.setHasFixedSize(true);
        binding.mainRv.setLayoutManager(new LinearLayoutManager(binding.mainRv.getContext()));
        adapter = new RvAdapter(setData(), context);
        binding.mainRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
