package com.example.mvvmrecycler.tools;

import android.content.Context;
import android.widget.Toast;

import com.example.mvvmrecycler.data.MainBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Function {

    public static void arrMainCompare(ArrayList<MainBean>arrayList){

        Collections.sort(arrayList, new Comparator<MainBean>() { // o1-o2小於 o2-o1大於
            @Override
            public int compare(MainBean o1, MainBean o2) {
                int i = o1.getId() - o2.getId();
                if(i == 0){
                    return o1.getId() - o2.getId();
                }
                return i;
            }
            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });

    }

    public static void setToast(Context context, String text, int duration) {

        Toast.makeText(context, text, duration).show();

    }

     public static String getBackgroundColor(String url){

             String color = "#"+url.replace("https://via.placeholder.com/150/", "").trim();

             if (color.length() == 6) { //顏色補字元

                 color = color + "0";

             } else if (color.length() == 5) {

                 color = color + "0" + "0";

             } else if (color.length() == 4) {

                 color = color + "0" + "0" + "0";

             }

             return color;

     }

     public static String getUrl(String url){

         url = url.replace("https://via.placeholder.com/", "").trim();

         StringBuilder sbUrl = new StringBuilder(url);

         url = sbUrl.replace(3, 10, "").toString().trim();
         url = url + " x " + url;

         return url;

     }
/*
    public boolean isMainLooper() {

        return  Looper.getMainLooper() == Looper.myLooper();

    }

    public boolean isMainThread() {

        return  Looper.getMainLooper().getThread() == Thread.currentThread();

    }

    public FloatingActionButton fabHide(FloatingActionButton fab) {

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fab.getLayoutParams();

        fab.animate().translationY(fab.getHeight()+layoutParams.bottomMargin).setInterpolator(new AccelerateInterpolator(3));

        return fab;

    }

    public FloatingActionButton fabShow(FloatingActionButton fab) {

        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));

        return fab;

    }  */

}
