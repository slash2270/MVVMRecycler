package com.example.mvvmrecycler.view;

import static com.example.mvvmrecycler.tools.Constant.MESSAGE_WHAT_DATA;
import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_MAIN;
import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_RV;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmrecycler.data.DBHelper;
import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.databinding.MainActivityBinding;
import com.example.mvvmrecycler.viewmodel.MainViewModel;

public class Set {

    public void viewModel(MainActivity activity, MainViewModel model, Handler handler, Runnable runDel, MainActivityBinding binding, Context context, RecyclerView recyclerView, Button btnIncrease, Button btnRefresh){

        model.initView();
        model.setTitleBtn();
        model.setRv(recyclerView);

        new Thread(() -> { // work
            Looper.prepare();
            model.getData(handler, runDel, activity, binding, context);
            model.setBtnClick(handler, runDel, activity, binding, context, btnIncrease, btnRefresh);
            Looper.loop();
        }).start();

    }

    public void clearTable(Context context){

        DBManager dbManager = new DBManager();
        DBHelper dbHelper = dbManager.getHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbManager.deleteLines(TABLE_NAME_MAIN, database);
        dbManager.deleteLines(TABLE_NAME_RV, database);

    }

    public void destroy(Handler.Callback callbackData, Handler handler, Runnable runDel){

        handler.removeCallbacks(runDel);
        handler.removeMessages(MESSAGE_WHAT_DATA);
        handler.removeCallbacksAndMessages(callbackData);

    }

    public void alterDialog(Activity activity){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("已存入SQLite");
        alertDialog.setPositiveButton("確定", (dialog, which) -> dialog.dismiss());
        alertDialog.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        alertDialog.show();

    }

}
