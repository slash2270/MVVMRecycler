package com.example.mvvmrecycler.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mvvmrecycler.BR;
import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.R;
import com.example.mvvmrecycler.databinding.MainListItemBinding;
import com.example.mvvmrecycler.tools.Function;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.mvvmrecycler.adapter.RvAdapter.*;
import static com.example.mvvmrecycler.data.DBConstant.MAIN_ID;
import static com.example.mvvmrecycler.data.DBConstant.RV_ID;
import static com.example.mvvmrecycler.data.DBConstant.TABLE_NAME_MAIN;
import static com.example.mvvmrecycler.data.DBConstant.TABLE_NAME_RV;

public class RvAdapter extends RecyclerView.Adapter <ItemViewHolder> implements DBManager.addCursor {

    private Function function;
    private DBManager dbManager;
    private SQLiteDatabase db;
    private Activity activity;
    private Context context;
    private ArrayList<MainBean> arrAdapter;
    private MainListItemBinding binding;
    private MainBean mainBean;
    private ArrayList<Integer> arrRv;
    private String strParamId, strQuestion;
    private Object[] arrObjParamId;
    private int intParamId;
    private Cursor cursor;
    private Runnable runnable;
    private Handler handler;

    public RvAdapter(Activity activity, ArrayList<MainBean> arrAdapter, Context context) {

        this.activity = activity;
        this.arrAdapter = arrAdapter;
        this.context = context;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.main_list_item, parent, false);

        dbManager = new DBManager();

        function = new Function();

        arrRv = new ArrayList<>();

        handler = new Handler();

        return new ItemViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        runnable = new Runnable() {
            @Override
            public void run() {

                mainBean = arrAdapter.get(position);

                holder.bindItem(mainBean);
                holder.mainListItemBinding.llContent.setBackgroundColor(Color.parseColor(arrAdapter.get(position).getColor()));
                holder.mainListItemBinding.lldelete.setBackgroundColor(Color.parseColor(arrAdapter.get(position).getColor()));
                holder.mainListItemBinding.tvDelete.setText("刪除");
                holder.mainListItemBinding.llContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        function.setToast(activity, context, arrAdapter.get(position).getTitle(), Toast.LENGTH_SHORT);

                    }
                });

                holder.mainListItemBinding.llContent.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        function.setToast(activity, context, arrAdapter.get(position).getTitle(), Toast.LENGTH_SHORT);

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
        };

        activity.runOnUiThread(runnable);

    }

    @Override
    public int getItemCount() {
        return arrAdapter == null ? 0 : arrAdapter.size();
    }

    @Override
    public Cursor addCursor(Cursor adapterCursor) { cursor = adapterCursor;return cursor; }

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

            function.setToast(activity, context, " 最少顯示一筆資料, 謝謝 ", Toast.LENGTH_SHORT);

        } else {

            strParamId = "(" + RV_ID + ")";
            strQuestion = "(?)";
            intParamId = arrAdapter.get(position).getId();
            arrObjParamId = new Object[]{intParamId};
            dbManager.insertSQLite(context, TABLE_NAME_RV, strParamId, strQuestion, arrObjParamId);
            arrAdapter.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();

        }

    }

    public void addItem(int arrActivitySize) {

        if (arrAdapter.size() == arrActivitySize) { // 判斷adapter和activity裡的值

            function.setToast(activity, context, " 已顯示最多資料, 謝謝 ", Toast.LENGTH_SHORT);

        } else if (arrAdapter.size() < arrActivitySize) {

            dbManager.selectSQLite(context, TABLE_NAME_RV);
            dbManager.cursorRvList(arrRv);

            for (intParamId = 0; intParamId < arrRv.size(); intParamId++) {

                intParamId = arrRv.get(intParamId);

                dbManager.inSQLite(context, TABLE_NAME_MAIN, MAIN_ID, intParamId);
                dbManager.cursorMainList(arrAdapter);

                mainBean = arrAdapter.get(getItemCount() - 1); // ItemCount比arrSize更不容易報錯
                binding.setVariable(BR.item, mainBean);
                binding.executePendingBindings();
                notifyItemInserted(intParamId - 1); //adapter插入position位置
                arrObjParamId = new Object[]{intParamId};
                dbManager.deleteSQLite(context, TABLE_NAME_RV, strParamId, strQuestion, arrObjParamId);
                notifyDataSetChanged();

            }
        }

    }

}