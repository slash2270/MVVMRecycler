package com.example.mvvmrecycler.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.mvvmrecycler.adapter.RvAdapter.*;

public class RvAdapter extends RecyclerView.Adapter <ItemViewHolder>{

    private SharedPreferences sP;
    private SharedPreferences.Editor spEditor;
    private Context context;
    private ArrayList<DataBean> arrData;
    private MainListItemBinding binding;
    private DataBean dataBean;
    private int getPosition;

    public RvAdapter(ArrayList<DataBean> arrData, Context context) {
        this.arrData = arrData;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        sP = context.getSharedPreferences("deladditem", MODE_PRIVATE);

        spEditor = sP.edit();

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.main_list_item,parent,false);

        return new ItemViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        dataBean = arrData.get(position);
        String name = dataBean.getName();
        holder.bindItem(dataBean);
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
        return arrData == null ? 0 : arrData.size();
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

    public void addItem(int getArrSize){

        String name = "";

        if(arrData.size() == getArrSize){

            Toast.makeText(context, " 已顯示最多資料, 謝謝 ", Toast.LENGTH_LONG).show();

        }else if(arrData.size() < getArrSize){

            if(sP != null) {

                getPosition = sP.getInt("position", 0);
                arrData.add(getPosition, new DataBean(sP.getString("name", ""), sP.getString("number", ""), sP.getString("content", ""),"刪除"));
                name = arrData.get(getPosition).getName();
                dataBean = arrData.get(getItemCount() - 1);
                binding.setVariable(BR.item, dataBean);
                binding.executePendingBindings();
                notifyItemInserted(getPosition);
                //   spEditor.clear().apply();

            }

        }

        Log.d("TAG", " 增加 getPosition" + String.valueOf(getPosition));
        Log.d("TAG", " 增加 arrData Name" + name);
        Log.d("TAG", " 增加 arrData.size" + String.valueOf(arrData.size()));

    }

    public void deleteItem(int position){

        if (arrData.size() == 1){

            Toast.makeText(context, " 最少顯示一筆資料, 謝謝 ", Toast.LENGTH_LONG).show();

        }else {

            spEditor.putInt("position", position)
                    .putString("name", arrData.get(position).getName())
                    .putString("number", arrData.get(position).getNumber())
                    .putString("content", arrData.get(position).getContent()).apply();

            getPosition = position;
            arrData.remove(position);
            notifyItemRemoved(position);

        }

    }

}
