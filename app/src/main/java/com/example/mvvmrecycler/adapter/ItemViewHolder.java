package com.example.mvvmrecycler.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmrecycler.BR;
import com.example.mvvmrecycler.databinding.MainListItemBinding;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public MainListItemBinding mainListItemBinding;

    public ItemViewHolder(MainListItemBinding mainListItemBinding) {
        super(mainListItemBinding.getRoot());
        this.mainListItemBinding = mainListItemBinding;
    }

    public void bindItem(Object obj) {
        mainListItemBinding.setVariable(BR.item, obj);
        mainListItemBinding.executePendingBindings();
    }

}
