package com.example.mvvmrecycler.data;

import static com.example.mvvmrecycler.tools.Constant.MAIN_COLOR;
import static com.example.mvvmrecycler.tools.Constant.MAIN_ID;
import static com.example.mvvmrecycler.tools.Constant.MAIN_NUMBER;
import static com.example.mvvmrecycler.tools.Constant.MAIN_TITLE;
import static com.example.mvvmrecycler.tools.Constant.MAIN_URL;
import static com.example.mvvmrecycler.tools.Constant.RV_ID;
import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_MAIN;
import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_RV;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String mainSQL = "create table " + TABLE_NAME_MAIN+
                "("
                +MAIN_ID + " Integer primary key ,"
                +MAIN_NUMBER + " varchar(20) ,"
                +MAIN_TITLE + " varchar(200) ,"
                +MAIN_URL + " varchar(20) ,"
                +MAIN_COLOR + " varchar(20)" +
                ")";
        db.execSQL(mainSQL);

        String rvSQL = "create table " + TABLE_NAME_RV+
                "("
                +RV_ID + " Integer" +
                ")";
        db.execSQL(rvSQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}