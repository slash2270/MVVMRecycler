package com.example.mvvmrecycler.adapter;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.data.MainBean;

import java.util.ArrayList;

import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_RV;
import static com.example.mvvmrecycler.tools.Function.setToast;

public class SetDeleteItem {

    public void deleteItem(Activity activity, Context context, ArrayList<MainBean> arrAdapter, int position, int intParamId, Object[] arrObjParamId, DBManager dbManager
            , String strParamId, String strQuestion, RvAdapter adapter) {  // position屬於遞減性質,傳值取Data ID

        if (arrAdapter.size() == 1) {

            setToast(activity, context, " 最少顯示一筆資料, 謝謝 ", Toast.LENGTH_SHORT);

        } else {

            intParamId = arrAdapter.get(position).getId();
            arrObjParamId = new Object[]{intParamId};
            dbManager.insertSQLite(context, TABLE_NAME_RV, strParamId, strQuestion, arrObjParamId);
            arrAdapter.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyDataSetChanged();

        }

    }

}