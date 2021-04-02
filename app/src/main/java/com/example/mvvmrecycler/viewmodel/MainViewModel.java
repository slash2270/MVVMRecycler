package com.example.mvvmrecycler.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.adapter.RvAdapter;
import com.example.mvvmrecycler.datamodel.DataModel;
import com.example.mvvmrecycler.databinding.MainActivityBinding;

import java.util.ArrayList;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import static android.content.Context.MODE_PRIVATE;
import static com.example.mvvmrecycler.data.DBConstant.DATABASE_NAME;
import static com.example.mvvmrecycler.data.DBConstant.MSG;
import static com.example.mvvmrecycler.data.DBConstant.TAG;

public class MainViewModel extends ViewModel{

    public final ObservableField<String> ovfName = new ObservableField<>();
    public final ObservableField<String> ovfNumber = new ObservableField<>();
    public final ObservableField<String> ovfContent = new ObservableField<>();
    public final ObservableBoolean isLoading = new ObservableBoolean(false);

    private final DataModel dataModel = new DataModel();

    private ArrayList<MainBean> arrView;
    private RvAdapter adapter;

    public String refresh, increase;

    private Context context;

    SharedPreferences sP;
    SharedPreferences.Editor spEditor;

    private int arrActivitySize;

    public void initView(Activity activity){

        context = activity.getApplicationContext();

        sP = context.getSharedPreferences("DelAddItem", MODE_PRIVATE);
        spEditor = sP.edit();

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

        DBManager dbManager = new DBManager();

        refresh = "刷新";
        increase = "復原";

        binding.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter.addItem(arrActivitySize, arrView);

            }
        });

        binding.btnIncrease.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                adapter.addItem(arrActivitySize, arrView);

                return false;
            }
        });

        binding.btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spEditor.clear().apply();
                dbManager.deleteDb(context, DATABASE_NAME);
                getData(binding);

            }
        });

        binding.btnRefresh.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                spEditor.clear().apply();
                dbManager.deleteDb(context, DATABASE_NAME);
                getData(binding);

                return false;
            }
        });

    }

    private ArrayList<MainBean> setData(){

        arrView = new ArrayList<>();
        dataModel.setRvData(arrView, context);

        return arrView;

    }

    public void getData(MainActivityBinding binding) {

        binding.mainRv.setHasFixedSize(true);
        binding.mainRv.setLayoutManager(new LinearLayoutManager(binding.mainRv.getContext()));
        adapter = new RvAdapter(setData(), context);
        binding.mainRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        arrActivitySize = adapter.getItemCount();

        Log.d(TAG, MSG + "getArrSize " + String.valueOf(arrActivitySize));

    }

}
