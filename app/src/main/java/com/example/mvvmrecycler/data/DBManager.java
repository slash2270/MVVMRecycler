package com.example.mvvmrecycler.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import com.example.mvvmrecycler.tools.AllConstant;

import java.util.ArrayList;

import static com.example.mvvmrecycler.data.DBConstant.DATABASE_NAME;
import static com.example.mvvmrecycler.data.DBConstant.DATABASE_VERSION;
import static com.example.mvvmrecycler.data.DBConstant.MAIN_COLOR;
import static com.example.mvvmrecycler.data.DBConstant.MAIN_ID;
import static com.example.mvvmrecycler.data.DBConstant.MAIN_NUMBER;
import static com.example.mvvmrecycler.data.DBConstant.MAIN_TITLE;
import static com.example.mvvmrecycler.data.DBConstant.MAIN_URL;
import static com.example.mvvmrecycler.data.DBConstant.TABLE_NAME_MAIN;

/**
 * DB語法有兩種請自行新增
 * 需注意DB和Helper必須每次都實體化
 * writable/readable和db/cursor都只能調用一種
 */

public class DBManager{

    private DBHelper helper;
    private ContentValues cv;
    private String sql;
    private int id;
    private Cursor cursor;

    /**
     * 取得SQLHelper
     */

    public DBHelper getHelper(Context context){

        if (helper == null){
            helper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        return helper;

    }

    /**
     * 設置DB路徑
     */
/*
    public void openDB(Context context, String DBHath) {
        String path = context.getDatabasePath(DBHath).getPath();
        sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }*/

    /**
     *  刪除資料庫
     */

    public void deleteDb(Context context, String dbname) {
        context.deleteDatabase(dbname);
    }

    /**
     * 新增Data
     */

    public void insertSQLite(Context context, SQLiteDatabase db, String tableName, String columns, String questions, Object[] paramArgs){

        getHelper(context);
        db = helper.getWritableDatabase();

        sql = "insert into " + tableName + " " + columns + " values " + questions;

        db.execSQL(sql, paramArgs);
        db.close();

    }

    public void insertAndroid(Context context, SQLiteDatabase db, String tableName, ContentValues cv, String key, String value){

        getHelper(context);
        db = helper.getWritableDatabase();

        cv.put(key, value);

        db.insert(tableName, null, cv);
        db.close();

    }

    public void insertMain(Context context, SQLiteDatabase db, int id, String number, String title, String url, String color){

        getHelper(context);
        db = helper.getWritableDatabase();

        cv = new ContentValues();
        cv.put(MAIN_ID, id);
        cv.put(MAIN_NUMBER, number);
        cv.put(MAIN_TITLE, title);
        cv.put(MAIN_URL, url);
        cv.put(MAIN_COLOR, color);

        db.insert(TABLE_NAME_MAIN, null, cv);
        db.close();

    }

    /**
     * 刪除資料
     */

    public void deleteAndroid(Context context, SQLiteDatabase db, String tableName, String columnClause, String[] paramArgs){

        getHelper(context);
        db = helper.getWritableDatabase();

        columnClause = columnClause + " = ?";

        db.delete(tableName, columnClause, paramArgs);
        db.close();

    }

    public void deleteSQLite(Context context, SQLiteDatabase db, String tableName, String columns, String questions, Object[] paramArgs){

        getHelper(context);
        db = helper.getWritableDatabase();

        sql = "delete from " + tableName + " where " + columns + " = " + questions;

        db.execSQL(sql,paramArgs);
        db.close();

    }

    /**
     * 搜尋Table
     */

    public Cursor selectAndroid(Context context, SQLiteDatabase db, String tableName){

        getHelper(context);
        db = helper.getReadableDatabase();
        cursor = db.query(tableName, null, null, null, null, null, null);

        return cursor;

    }

    public Cursor selectSQLite(Context context, SQLiteDatabase db, String tableName){

        getHelper(context);
        db = helper.getReadableDatabase();
        sql = "select * from " + tableName;
        cursor = db.rawQuery(sql, null);

        return cursor;

    }

    /**
     * 搜尋 IN
     */

    public Cursor inSQLite(Context context, SQLiteDatabase db, String tableName, String columns, Object params){

        getHelper(context);
        db = helper.getReadableDatabase();
        sql = "select * from " + tableName + " where " + columns + " in " + "("+ params +")";
        cursor = db.rawQuery(sql, null);

        return cursor;

    }

    public Cursor inAndroid(Context context, SQLiteDatabase db, String tableName, String columns, String[] paramArgs){

        getHelper(context);
        db = helper.getReadableDatabase();
        columns = columns + " = ?";
        cursor = db.query(tableName, null, columns, paramArgs, null, null, null);

        return cursor;

    }

    /**
     * Cursor轉ArrayList
     */

    public ArrayList<MainBean> cursorMainList(ArrayList<MainBean> arrayList){

        String number;
        String title;
        String url;
        String color;

        if (cursor.moveToFirst()) {

            for (int i = 0; i < cursor.getCount(); i++) {

                id = cursor.getInt(cursor.getColumnIndex(DBConstant.MAIN_ID));
                number = cursor.getString(cursor.getColumnIndex(MAIN_NUMBER));
                title = cursor.getString(cursor.getColumnIndex(MAIN_TITLE));
                url = cursor.getString(cursor.getColumnIndex(DBConstant.MAIN_URL));
                color = cursor.getString(cursor.getColumnIndex(DBConstant.MAIN_COLOR));

                arrayList.add(new MainBean(id, number, title, url, color));

                cursor.moveToNext();

            }

        }

        cursor.close();

        return arrayList;

    }

    public ArrayList<Integer> cursorRvList(ArrayList<Integer> arrayList){

        if (cursor.moveToFirst()) {

            for (int i = 0; i < cursor.getCount(); i++) {

                id = cursor.getInt(cursor.getColumnIndex(DBConstant.RV_ID));

                cursor.moveToNext();

                arrayList.add(id);

            }

        }

        cursor.close();

        return arrayList ;

    }

    public Cursor addCursor(Cursor cursor){

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                addCursor(cursor);

            }
        });

        return cursor;

    }

    public interface addCursor{

        Cursor addCursor(Cursor adapterCursor);

    }

}

