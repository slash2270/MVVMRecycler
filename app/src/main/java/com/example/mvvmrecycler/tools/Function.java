package com.example.mvvmrecycler.tools;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Function {

    public void setToast(Context context, String text, int duration){

        Toast.makeText(context, text, duration).show();

    }

     public String getBackgroudColor(String color,String url){

             color = "#"+url.replace("https://via.placeholder.com/150/", "").trim();

             if (color.length() == 6) { //顏色補字元

                 color = color + "0";

             } else if (color.length() == 5) {

                 color = color + "0" + "0";

             } else if (color.length() == 4) {

                 color = color + "0" + "0" + "0";

             }

             return color;

     }

     public String getUrl(String url){

         url = url.replace("https://via.placeholder.com/", "").trim();

         StringBuffer sbThumUrl = new StringBuffer(url);

         url = sbThumUrl.replace(3, 10, "").toString().trim();
         url = url + " x " + url;

         return url;

     }

    public void fabHide(FloatingActionButton fab) {

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fab.getLayoutParams();

        fab.animate().translationY(fab.getHeight()+layoutParams.bottomMargin).setInterpolator(new AccelerateInterpolator(3));

    }

    public void fabShow(FloatingActionButton fab) {

        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));

    }

}
