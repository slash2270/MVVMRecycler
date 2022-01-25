package com.example.mvvmrecycler.adapter;

import android.content.Context;
import android.widget.Toast;
import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.tools.Function;
import java.util.ArrayList;

import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_RV;

public class SetDeleteItem {

    public void deleteItem(Context context, ArrayList<MainBean> arrAdapter, int position, DBManager dbManager, String strParamId, String strQuestion, RvAdapter rvAdapter) {  // position屬於遞減性質,傳值取Data ID

        if (arrAdapter.size() == 1) {

            Function.setToast(context, " 最少顯示一筆資料, 謝謝 ", Toast.LENGTH_SHORT);

        } else {

            int intParamId = arrAdapter.get(position).getId();
            Object[] arrObjParamId = new Object[]{intParamId};
            dbManager.insertSQLite(context, TABLE_NAME_RV, strParamId, strQuestion, arrObjParamId);
            arrAdapter.remove(position);
            rvAdapter.notifyItemRemoved(position);
            rvAdapter.notifyDataSetChanged();

        }

    }

}
