package com.example.mvvmrecycler.datamodel;
import android.app.Activity;
import android.content.Context;
import com.example.mvvmrecycler.adapter.RvAdapter;
import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.databinding.MainActivityBinding;
import java.util.ArrayList;

public class AddAdapter {

    public RvAdapter setAdapter(Activity activity, Context context, MainActivityBinding binding, ArrayList<MainBean> arrView) {

        RvAdapter rvAdapter = new RvAdapter(activity, arrView, context);
        binding.rv.setAdapter(rvAdapter);
        rvAdapter.notifyDataSetChanged();

        return rvAdapter;

    }

}