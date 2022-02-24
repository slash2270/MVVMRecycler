package com.example.mvvmrecycler.adapter;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.tools.Function;

import java.util.ArrayList;

import static com.example.mvvmrecycler.tools.Constant.RV_COLUMNS;
import static com.example.mvvmrecycler.tools.Constant.RV_QUESTIONS;
import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_RV;

public class SetDeleteItem {

    public void deleteItem(Handler handler, Runnable runnable, Context context, ArrayList<MainBean> arrAdapter, int position, DBManager dbManager, RvAdapter rvAdapter, Function function) {  // position屬於遞減性質,傳值取Data ID

        if (arrAdapter.size() == 1) {

            function.setToast(context, " 最少顯示一筆資料, 謝謝 ", Toast.LENGTH_SHORT);

        } else {

            runnable = () -> {

                int intParamId = arrAdapter.get(position).getId();
                Object[] arrObjParamId = new Object[]{intParamId};
                dbManager.insertLine(context, TABLE_NAME_RV, RV_COLUMNS, RV_QUESTIONS, arrObjParamId);
                //dbManager.closeDb();
                arrAdapter.remove(position);
                rvAdapter.notifyItemRemoved(position);
                rvAdapter.notifyDataSetChanged();

            };

            handler.post(runnable);

        }

    }

}