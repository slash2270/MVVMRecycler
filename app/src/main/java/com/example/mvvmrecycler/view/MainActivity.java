package com.example.mvvmrecycler.view;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import com.example.mvvmrecycler.R;
import com.example.mvvmrecycler.base.BaseActivity;
import com.example.mvvmrecycler.viewmodel.MainViewModel;

import static com.example.mvvmrecycler.tools.Constant.MESSAGE_WHAT_DATA;
import static com.example.mvvmrecycler.tools.Constant.LIFE_CYCLE;
import static com.example.mvvmrecycler.tools.Constant.FLAG_MAIN;

public class MainActivity extends BaseActivity {

    private Handler handler;
    private Handler.Callback callbackData;
    private Runnable runDel;
    private RecyclerView recyclerView;
    private Button btnIncrease, btnRefresh;
    private Set set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(LIFE_CYCLE,"onCreate");

        set = new Set();

        setCallback();

        com.example.mvvmrecycler.databinding.MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        initView();

        MainViewModel model = new MainViewModel();

        set.viewModel(this, model, handler, runDel, binding, getApplicationContext(), recyclerView, btnIncrease, btnRefresh);

        binding.setModel(model);

    }

    private void initView(){

        recyclerView = findViewById(R.id.rv);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnRefresh = findViewById(R.id.btnRefresh);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(LIFE_CYCLE,"onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(LIFE_CYCLE,"onReStart");
    }

    @Override
    protected void setUpResumeComponent() {
        registerReceiver(FLAG_MAIN);
    }

    @Override
    protected void onCatchReceive(String action, Intent intent) {
        if (action.equals(FLAG_MAIN))
        super.onCatchReceive(action, intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(LIFE_CYCLE,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(LIFE_CYCLE,"onStop");
    }

    @Override
    protected void onDestroy() {
        set.clearTable(getApplicationContext());
        set.destroy(callbackData, handler, runDel);
        Log.e(LIFE_CYCLE,"onDestroy");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        set.clearTable(getApplicationContext());

    }

    public void setCallback(){

        callbackData = new HandlerCallBack();
        handler = new Handler(callbackData);

    }

    public class HandlerCallBack implements Handler.Callback{

        @Override
        public boolean handleMessage(@NonNull Message message) {

            if (message.what == MESSAGE_WHAT_DATA) {
                set.alterDialog(MainActivity.this);
                btnIncrease.setEnabled(true);
                btnRefresh.setEnabled(true);
            }

            return true;
        }

    }

}