package com.example.mvvmrecycler.datamodel;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.example.mvvmrecycler.adapter.RvAdapter;
import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.databinding.MainActivityBinding;
import com.example.mvvmrecycler.tools.Function;

import java.util.ArrayList;

public class AddAdapter {

    public RvAdapter setAdapter(Activity activity, Context context, MainActivityBinding binding, ArrayList<MainBean> arrView, Function function, Handler handler, Runnable runDel) {

        RvAdapter rvAdapter = new RvAdapter(activity, context, arrView, function, handler, runDel);
        binding.rv.setAdapter(rvAdapter);
        rvAdapter.notifyDataSetChanged();

        return rvAdapter;

    }

}