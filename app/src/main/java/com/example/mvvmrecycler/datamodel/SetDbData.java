package com.example.mvvmrecycler.datamodel;

import android.content.Context;
import android.os.Handler;

import com.example.mvvmrecycler.data.DBManager;

import static com.example.mvvmrecycler.tools.Constant.MAIN_COLOR;
import static com.example.mvvmrecycler.tools.Constant.MAIN_ID;
import static com.example.mvvmrecycler.tools.Constant.MAIN_NUMBER;
import static com.example.mvvmrecycler.tools.Constant.MAIN_TITLE;
import static com.example.mvvmrecycler.tools.Constant.MAIN_URL;
import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_MAIN;

public class SetDbData {

    public static void addDbData(Context context, int id, String number, String title, String url, String color){ //  依需求選擇DB是否存取本機資料

        Runnable runnable = () -> {

            DBManager dbManager = new DBManager();
            Object[] objectArr = new Object[]{id, number, title, url, color};
            String columns = "(" + MAIN_ID + ", " + MAIN_NUMBER + ", " + MAIN_TITLE + ", " + MAIN_URL + ", " + MAIN_COLOR + ")";
            String questions = "(?,?,?,?,?)";
            dbManager.insertSQLite(context, TABLE_NAME_MAIN, columns, questions, objectArr);

        };

        new Handler().postDelayed(runnable, 3000);

    }

}