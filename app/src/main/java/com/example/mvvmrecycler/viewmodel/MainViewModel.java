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

import static com.example.mvvmrecycler.tools.Constant.DATABASE_NAME;

public class MainViewModel extends ViewModel implements DataModel.GetAdapterSize{

    public ObservableField<String> ovfNumber;

    public ObservableBoolean isLoading;

    private Context context;

    private DBManager dbManager;

    private SQLiteDatabase db;

    private DataModel dataModel;

    private Runnable runIncrease, runRefresh;

    private Function function;

    private ArrayList<MainBean> arrView; //和arrAdapter屬於同步性質
    private RvAdapter adapter;
    public String strTitle, strUrl, strRefresh, strIncrease;
    private int arrViewSize;


    public void initView(Activity activity){

        context = activity.getApplicationContext();

        dataModel = new DataModel();
        dbManager = new DBManager();

        ovfNumber = new ObservableField<>();

        isLoading = new ObservableBoolean(false);

        function = new Function();

        arrView = new ArrayList<>();

    }

    public void setTitleBtn(Activity activity){

        isLoading.set(true);

        dataModel.setTextTitleBtn(activity, new DataModel.SetTextTitleBtn() {
            @Override
            public void number(String number) {

                ovfNumber.set(number);

            }

            @Override
            public String title(String title) {

                strTitle = title;

                return strTitle;

            }

            @Override
            public String url(String url) {

                strUrl = url;

                return strUrl;

            }

            @Override
            public String increase(String increase) {

                strIncrease = increase;

                return strIncrease;

            }

            @Override
            public String refresh(String refresh) {

                strRefresh = refresh;

                return strRefresh;

            }

        });

        isLoading.set(false);

    }

    public void getData(MainActivityBinding binding, Activity activity){

        arrView.clear();

        dataModel.getData(this, binding, activity, context, arrView);

    }

    public void setRv(MainActivityBinding binding) {

        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(binding.rv.getContext()));

    }

    @Override
    public RvAdapter addRvAdapter(RvAdapter rvAdapter) { adapter = rvAdapter;return adapter; }

    @Override
    public int addArrSize(int arrSize) { arrViewSize = arrSize;return arrViewSize; }

    public void setBtnClick(MainActivityBinding binding, Activity activity){

        runIncrease = (new Runnable() {
            @Override
            public void run(){

                binding.btnIncrease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        adapter.addItem(arrViewSize);

                        function.arrMainCompare(arrView);

                    }
                });

                binding.btnIncrease.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        adapter.addItem(arrViewSize);

                        function.arrMainCompare(arrView);

                        return false;
                    }
                });

            }
        });

        runRefresh = (new Runnable() {
            @Override
            public void run() {

                binding.btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dbManager.deleteDb(context, DATABASE_NAME);
                        getData(binding, activity);

                    }
                });

                binding.btnRefresh.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        dbManager.deleteDb(context, DATABASE_NAME);
                        getData(binding, activity);

                        return false;

                    }
                });

            }
        });

        new Thread(runIncrease).start();
        new Thread(runRefresh).start();

    }

}

