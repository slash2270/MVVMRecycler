package com.example.mvvmrecycler.view;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import com.example.mvvmrecycler.R;
import com.example.mvvmrecycler.base.BaseActivity;
import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.viewmodel.MainViewModel;

import static com.example.mvvmrecycler.tools.Constant.DATABASE_NAME;
import static com.example.mvvmrecycler.tools.Constant.HANDLER_MESSAGE;
import static com.example.mvvmrecycler.tools.Constant.LIFE_CYCLE;
import static com.example.mvvmrecycler.tools.Constant.FLAG_MAIN;

public class MainActivity extends BaseActivity {

    private Handler handler;
    private Handler.Callback callback;
    private RecyclerView recyclerView;
    private Button btnIncrease, btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(LIFE_CYCLE,"onCreate");

        setCallback();

        com.example.mvvmrecycler.databinding.MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        initView();

        MainViewModel model = new MainViewModel();

        model.initView();
        model.setTitleBtn();
        model.setRv(recyclerView);
        model.getData(handler, this, binding, getApplicationContext());
        model.setBtnClick(handler, this, binding, getApplicationContext(), btnIncrease, btnRefresh);

        //Log.d(MSG + " create ", String.valueOf(arrayList.size()));

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
        super.onDestroy();
        handler.removeMessages(HANDLER_MESSAGE);
        handler.removeCallbacksAndMessages(callback);
        Log.e(LIFE_CYCLE,"onDestroy");
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

    public void setCallback(){

        callback = new HandlerCallBack();
        handler = new Handler(callback);

    }

    private class HandlerCallBack implements Handler.Callback{

        @Override
        public boolean handleMessage(@NonNull Message message) {

            if (message.what == HANDLER_MESSAGE) {
                showAlterDialog();
                btnIncrease.setEnabled(true);
                btnRefresh.setEnabled(true);
            }

            return true;
        }

        private void showAlterDialog(){

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("已下載完畢");
            alertDialog.setPositiveButton("確定", (dialog, which) -> dialog.dismiss());
            alertDialog.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
            alertDialog.show();

        }

    }

}