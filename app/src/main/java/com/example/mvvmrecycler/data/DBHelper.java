package com.example.mvvmrecycler.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String mainSQL = "create table " + DBConstant.TABLE_NAME_MAIN+
                "("
                +DBConstant.MAIN_ID + " Integer primary key ,"
                +DBConstant.MAIN_NAME + " varchar(20) ,"
                +DBConstant.MAIN_NUMBER + " varchar(20) ,"
                +DBConstant.MAIN_CONTENT + " varchar(200)" +
                ")";
        db.execSQL(mainSQL);

        String rvSQL = "create table " + DBConstant.TABLE_NAME_RV+
                "("
                +DBConstant.RV_ID + " Integer" +
                ")";
        db.execSQL(rvSQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
