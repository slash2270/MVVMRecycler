package com.example.mvvmrecycler.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mvvmrecycler.BR;
import com.example.mvvmrecycler.data.DataBean;
import com.example.mvvmrecycler.R;
import com.example.mvvmrecycler.databinding.MainListItemBinding;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import static com.example.mvvmrecycler.adapter.RvAdapter.*;

public class RvAdapter extends RecyclerView.Adapter <ItemViewHolder>{

    private Context context;
    private ArrayList<DataBean> arrDataBean;
    private MainListItemBinding binding;
    private DataBean dataBean;
    private int getPosition;

    public RvAdapter(ArrayList<DataBean> arrDataBean, Context context) {
        this.arrDataBean = arrDataBean;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.main_list_item,parent,false);

        return new ItemViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        getPosition = position;

        dataBean = arrDataBean.get(position);
        holder.bindItem(dataBean);
        holder.mainListItemBinding.cardContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "You clicked " + dataBean.getName(), Toast.LENGTH_LONG).show();

            }
        });

        holder.mainListItemBinding.cardContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(context, "You clicked " + dataBean.getName(), Toast.LENGTH_LONG).show();

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
        return arrDataBean == null ? 0 : arrDataBean.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

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

    public void addItem(){

        if(dataBean.getName().length() == arrDataBean.size()){

            Toast.makeText(context, " 已顯示最多筆資料, 故無法增加, 請見諒 ", Toast.LENGTH_LONG).show();

        }else {

            arrDataBean.add(getPosition, dataBean);
            notifyItemInserted(getPosition); //增加Item

        }

        Log.d("TAG", "增加" + String.valueOf(dataBean.getName().length()));
        Log.d("TAG", "增加" + String.valueOf(arrDataBean.size()));

        notifyDataSetChanged();
    }

    public void deleteItem(int position){

        if (arrDataBean.size() == 1){

            Toast.makeText(context, " 最少顯示一筆資料, 故無法刪除, 請見諒 ", Toast.LENGTH_LONG).show();

        }else {

            arrDataBean.remove(position);
            notifyItemRemoved(position); //刪除Item

        }

        notifyDataSetChanged();

    }

}
