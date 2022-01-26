package com.example.mvvmrecycler.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mvvmrecycler.BR;
import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.R;
import com.example.mvvmrecycler.data.RvBean;
import com.example.mvvmrecycler.databinding.MainListItemBinding;
import com.example.mvvmrecycler.tools.Function;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.mvvmrecycler.tools.Constant.MAIN_ID;
import static com.example.mvvmrecycler.tools.Constant.RV_COLUMNS;
import static com.example.mvvmrecycler.tools.Constant.RV_QUESTIONS;
import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_MAIN;
import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_RV;

public class RvAdapter extends RecyclerView.Adapter <ItemViewHolder>{

    private final Handler handler;
    private final Runnable runDelete;
    private final Activity activity;
    private final Context context;
    private ArrayList<MainBean> arrAdapter;
    public MainListItemBinding binding;
    private MainBean mainBean;
    private final DBManager dbManager = new DBManager();
    private final Function function;

    public RvAdapter(Activity activity, Context context, ArrayList<MainBean> arrAdapter, Function function, Handler handler, Runnable runDelete) {
        this.activity = activity;
        this.context = context;
        this.arrAdapter = arrAdapter;
        this.function = function;
        this.handler = handler;
        this.runDelete = runDelete;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.main_list_item, parent, false);

        return new ItemViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        AddBindViewHolder addBindViewHolder = new AddBindViewHolder();
        addBindViewHolder.setBindViewHolder(holder, activity, context, arrAdapter, position, dbManager, this, function);

    }

    public class AddBindViewHolder{

        public void setBindViewHolder(ItemViewHolder holder, Activity activity, Context context, ArrayList<MainBean> arrAdapter, int position, DBManager dbManager, RvAdapter rvAdapter, Function function){

            SetDeleteItem addDeleteItem = new SetDeleteItem();

            mainBean = arrAdapter.get(position);

            holder.bindItem(mainBean);
            holder.mainListItemBinding.llContent.setBackgroundColor(Color.parseColor(arrAdapter.get(position).getColor()));
            holder.mainListItemBinding.lldelete.setBackgroundColor(Color.parseColor(arrAdapter.get(position).getColor()));
            holder.mainListItemBinding.tvDelete.setText("刪除");
            holder.mainListItemBinding.llContent.setOnClickListener(v -> function.setToast(context, arrAdapter.get(position).getTitle(), Toast.LENGTH_SHORT));
            holder.mainListItemBinding.llContent.setOnLongClickListener(v -> { function.setToast(context, arrAdapter.get(position).getTitle(), Toast.LENGTH_SHORT); return false; });
            holder.mainListItemBinding.cardMenu.setOnClickListener(v -> addDeleteItem.deleteItem(handler, runDelete, context, arrAdapter, position, dbManager, rvAdapter, function));
            holder.mainListItemBinding.cardMenu.setOnLongClickListener(v -> { addDeleteItem.deleteItem(handler, runDelete, context, arrAdapter, position, dbManager, rvAdapter, function); return false; });

        }

    }

    @Override
    public int getItemCount() {
        return arrAdapter == null ? 0 : arrAdapter.size();
    }

    public void addItem(int arrActivitySize) {

            if (arrAdapter.size() < arrActivitySize) {

                Runnable runnable = () -> {

                dbManager.selectTable(context, TABLE_NAME_RV);
                ArrayList<RvBean> arrRv = dbManager.cursorRvList();

                int intParamId = arrRv.get(0).getPosition();

                dbManager.inSearch(context, TABLE_NAME_MAIN, MAIN_ID, intParamId);
                arrAdapter = dbManager.cursorMainList(arrAdapter);

                mainBean = arrAdapter.get(getItemCount() - 1); // ItemCount比arrSize更不容易報錯
                binding.setVariable(BR.item, mainBean);
                binding.executePendingBindings();
                notifyItemInserted(intParamId - 1); //adapter插入position位置
                Object[] arrObjParamId = new Object[]{intParamId};
                dbManager.deleteLine(context, TABLE_NAME_RV, RV_COLUMNS, RV_QUESTIONS, arrObjParamId);
                notifyDataSetChanged();

            };

                activity.runOnUiThread(runnable);

            } else { // 判斷adapter和activity裡的值

                function.setToast(context, " 已顯示最多資料, 謝謝 ", Toast.LENGTH_SHORT);

            }

    }

}