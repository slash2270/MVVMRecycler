package com.example.mvvmrecycler.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mvvmrecycler.tools.Constant;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String mainSQL = "create table " + Constant.TABLE_NAME_MAIN+
                "("
                +Constant.MAIN_ID + " Integer primary key ,"
                +Constant.MAIN_NUMBER + " varchar(20) ,"
                +Constant.MAIN_TITLE + " varchar(200) ,"
                +Constant.MAIN_URL + " varchar(20) ,"
                +Constant.MAIN_COLOR + " varchar(20)" +
                ")";
        db.execSQL(mainSQL);

        String rvSQL = "create table " + Constant.TABLE_NAME_RV+
                "("
                +Constant.RV_ID + " Integer" +
                ")";
        db.execSQL(rvSQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

