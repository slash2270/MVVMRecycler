package com.example.mvvmrecycler.view;

import android.content.Context;

import com.example.mvvmrecycler.data.DBManager;

import static com.example.mvvmrecycler.tools.Constant.DATABASE_NAME;

public class LoginDb {

    public void delDb(Context context){

        DBManager dbManager = new DBManager();
        dbManager.deleteDb(context, DATABASE_NAME);

    }

}
