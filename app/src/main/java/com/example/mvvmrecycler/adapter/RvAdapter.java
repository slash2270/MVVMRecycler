package com.example.mvvmrecycler.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
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

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.mvvmrecycler.tools.Constant.MAIN_ID;
import static com.example.mvvmrecycler.tools.Constant.RV_ID;
import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_MAIN;
import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_RV;
import static com.example.mvvmrecycler.tools.Function.setToast;

public class RvAdapter extends RecyclerView.Adapter <ItemViewHolder> implements DBManager.addCursor {

    private DBManager dbManager;
    private final Activity activity;
    private final Context context;
    private final ArrayList<MainBean> arrAdapter;
    private MainListItemBinding binding;
    private MainBean mainBean;
    private ArrayList<Integer> arrRv;
    private final String strParamId = "(" + RV_ID + ")";
    private final String strQuestion = "(?)";
    private Object[] arrObjParamId;
    private int intParamId;
    private Runnable runnable;

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

        arrRv = new ArrayList<>();

        return new ItemViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        AddBindViewHolder addBindViewHolder = new AddBindViewHolder();
        addBindViewHolder.setBindViewHolder(holder,position);

    }

    public class AddBindViewHolder{

        public void setBindViewHolder(ItemViewHolder holder, int position){

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

                            setToast(activity, context, arrAdapter.get(position).getTitle(), Toast.LENGTH_SHORT);

                        }
                    });

                    holder.mainListItemBinding.llContent.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            setToast(activity, context, arrAdapter.get(position).getTitle(), Toast.LENGTH_SHORT);

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

    }

    @Override
    public int getItemCount() {
        return arrAdapter == null ? 0 : arrAdapter.size();
    }

    @Override
    public Cursor addCursor(Cursor adapterCursor) {
        return adapterCursor; }

    public void deleteItem(int position) {  // position屬於遞減性質,傳值取Data ID

        runnable = new Runnable() {
            @Override
            public void run() {

                if (arrAdapter.size() == 1) {

                    setToast(activity, context, " 最少顯示一筆資料, 謝謝 ", Toast.LENGTH_SHORT);

                } else {

                    intParamId = arrAdapter.get(position).getId();
                    arrObjParamId = new Object[]{intParamId};
                    dbManager.insertSQLite(context, TABLE_NAME_RV, strParamId, strQuestion, arrObjParamId);
                    arrAdapter.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();

                }

            }

        };

        new Thread(runnable).start();

    }

    public void addItem(int arrActivitySize) {

        if (arrAdapter.size() == arrActivitySize) { // 判斷adapter和activity裡的值

            setToast(activity, context, " 已顯示最多資料, 謝謝 ", Toast.LENGTH_SHORT);

        } else if (arrAdapter.size() < arrActivitySize) {

            dbManager.selectSQLite(context, TABLE_NAME_RV);
            dbManager.cursorRvList(arrRv);

            intParamId = arrRv.get(0);

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