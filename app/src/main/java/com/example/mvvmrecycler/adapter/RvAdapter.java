package com.example.mvvmrecycler.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mvvmrecycler.BR;
import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.R;
import com.example.mvvmrecycler.databinding.MainListItemBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.mvvmrecycler.adapter.RvAdapter.*;
import static com.example.mvvmrecycler.data.DBConstant.MAIN_ID;
import static com.example.mvvmrecycler.data.DBConstant.MSG;
import static com.example.mvvmrecycler.data.DBConstant.RV_ID;
import static com.example.mvvmrecycler.data.DBConstant.TABLE_NAME_MAIN;
import static com.example.mvvmrecycler.data.DBConstant.TABLE_NAME_RV;
import static com.example.mvvmrecycler.data.DBConstant.TAG;

public class RvAdapter extends RecyclerView.Adapter <ItemViewHolder> {


    private SharedPreferences sP;
    private SharedPreferences.Editor spEditor;
    private DBManager dbManager;
    private SQLiteDatabase db;
    private Context context;
    private ArrayList<MainBean> arrAdapter;
    private MainListItemBinding binding;
    private MainBean mainBean;
    private ArrayList<Integer> arrRv;
    private String strParamId, strQuestion;
    private Object[] arrObjParamId;
    private int intParamId;
    private String arrId;
    private ArrayList<String> strArrRv;

    public RvAdapter(ArrayList<MainBean> arrAdapter, Context context) {
        this.arrAdapter = arrAdapter;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.main_list_item, parent, false);

        dbManager = new DBManager();

        return new ItemViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        mainBean = arrAdapter.get(position);
        String name = mainBean.getName();
        holder.bindItem(mainBean);
        holder.mainListItemBinding.tvDelete.setText("刪除");
        holder.mainListItemBinding.cardContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "You clicked " + name, Toast.LENGTH_SHORT).show();

            }
        });

        holder.mainListItemBinding.cardContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(context, "You clicked " + name, Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        holder.mainListItemBinding.cardMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteItem(position);

            }
        });

        holder.mainListItemBinding.cardMenu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                deleteItem(position);

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrAdapter == null ? 0 : arrAdapter.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private MainListItemBinding mainListItemBinding;

        public ItemViewHolder(MainListItemBinding mainListItemBinding) {
            super(mainListItemBinding.getRoot());
            this.mainListItemBinding = mainListItemBinding;
        }

        public void bindItem(Object obj) {
            mainListItemBinding.setVariable(BR.item, obj);
            mainListItemBinding.executePendingBindings();
        }

    }

    public void deleteItem(int position) {  // position屬於遞減性質,傳值取Data ID

        if (arrAdapter.size() == 1) {

            Toast.makeText(context, " 最少顯示一筆資料, 謝謝 ", Toast.LENGTH_LONG).show();

        } else {

            strParamId = "(" + RV_ID + ")";
            strQuestion = "(?)";
            intParamId = arrAdapter.get(position).getId();
            arrObjParamId = new Object[]{intParamId};
            dbManager.insertSQLite(context, db, TABLE_NAME_RV, strParamId, strQuestion, arrObjParamId);

        }

        arrAdapter.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();

    }

    public void addItem(int arrActivitySize, ArrayList<MainBean> arrActivity) {

        if (arrAdapter.size() == arrActivitySize) { // 判斷adapter和activity裡的值

            Toast.makeText(context, " 已顯示最多資料, 謝謝 ", Toast.LENGTH_LONG).show();

        } else if (arrAdapter.size() < arrActivitySize) {

            Cursor cursor = null;
            ArrayList<Integer> arrRv = new ArrayList<>();
            dbManager.selectRvCursorList(context, cursor, db, arrRv, TABLE_NAME_RV);

            for (intParamId = 0; intParamId < arrRv.size(); intParamId++) {

                intParamId = arrRv.get(intParamId);
                Log.d(TAG, MSG + "intParamId " + String.valueOf(intParamId));
                Log.d(TAG, MSG + "arrRvSize " + String.valueOf(arrRv.size()));

                dbManager.selectMainInCursorList(context, db, cursor, TABLE_NAME_MAIN, arrAdapter, MAIN_ID, intParamId);
                mainBean = arrAdapter.get(getItemCount() - 1); // ItemCount比arrSize更不容易報錯
                binding.setVariable(BR.item, mainBean);
                binding.executePendingBindings();
                notifyItemInserted(intParamId - 1);
                arrObjParamId = new Object[]{intParamId};
                dbManager.deleteSQLite(context, db, TABLE_NAME_RV, strParamId, strQuestion, arrObjParamId);
                notifyDataSetChanged();

            }

        }
    }
}


 /*  sp屬於覆寫性質,使用時注意
     private SharedPreferences sP;
    private SharedPreferences.Editor spEditor;
    sP = context.getSharedPreferences("deladditem", MODE_PRIVATE);
    spEditor = sP.edit();
   */

 /*                 sp刪除
                        if (sP != null) {

                arrId = sP.getString("id", "");
                strArrRv = new ArrayList<String>(Arrays.asList(arrId.split(",")));
                Log.d(TAG, MSG + "arrid " + String.valueOf(arrId));

            }

            arrId = String.valueOf(arrAdapter.get(position).getId()).trim();
            strArrRv.add(arrId);
            Log.d(TAG, MSG + "strArrRv.size() " + String.valueOf(strArrRv.size()));
            arrId = ArrayList2String(strArrRv, arrId);
            Log.d(TAG, MSG + "arrid " + String.valueOf(arrId));
            spEditor.putString("id", arrId).apply();
            strArrRv.clear();
            arrAdapter.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();

/*              sp新增
                   if (sP != null) {

                arrId = sP.getString("id", "");
                strArrRv = new ArrayList<String>(Arrays.asList(arrId.split(",")));

                Log.d(TAG, MSG + "strArrRv.size() " + String.valueOf(strArrRv.size()));

                for (intParamId = 0; intParamId < strArrRv.size(); intParamId++) { //取出刪除的id
                    arrId = strArrRv.get(intParamId);
                    Log.d(TAG, MSG + "arrId " + String.valueOf(arrId));
                    intParamId = Integer.parseInt(strArrRv.get(intParamId));

                    int activityId;
                    for (activityId = 0; activityId < arrActivity.size(); activityId++) {
                        activityId = arrActivity.get(activityId).getId();

                        if (intParamId == activityId) { //比較Sp和Activity裡的Data ID

                            arrAdapter.add(new MainBean(activityId, arrActivity.get(activityId).getName(), arrActivity.get(activityId).getNumber(), arrActivity.get(activityId).getContent()));
                            mainBean = arrAdapter.get(getItemCount() - 1);
                            binding.setVariable(BR.item, mainBean);
                            binding.executePendingBindings();
                            notifyItemInserted(intParamId - 1);
                            notifyDataSetChanged();
                            strArrRv.remove(intParamId - 1);
                            spEditor.putString("id", strArrRv.toString()).apply();

                        }

                    }

                }

            }

            } */

/*public String ArrayList2String(ArrayList<String> arrayList, String result) {
        result = "";
        if (arrayList != null && arrayList.size() > 0) {
            for (String item : arrayList) {

                result += item + ",";

                if (result.length() == 2) {

                    result.replace(",", "");
                    result = result.substring(0, result.length() - 1);

                } else {

                    result = result.substring(0, result.length() - 1);

                }
            }
        }
        return result;
    }   */
