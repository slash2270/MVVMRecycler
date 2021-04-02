package com.example.mvvmrecycler.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import static com.example.mvvmrecycler.data.DBConstant.DATABASE_NAME;
import static com.example.mvvmrecycler.data.DBConstant.DATABASE_VERSION;
import static com.example.mvvmrecycler.data.DBConstant.MAIN_CONTENT;
import static com.example.mvvmrecycler.data.DBConstant.MAIN_ID;
import static com.example.mvvmrecycler.data.DBConstant.MAIN_NAME;
import static com.example.mvvmrecycler.data.DBConstant.MAIN_NUMBER;
import static com.example.mvvmrecycler.data.DBConstant.MSG;
import static com.example.mvvmrecycler.data.DBConstant.TABLE_NAME_MAIN;
import static com.example.mvvmrecycler.data.DBConstant.TAG;

/**
 * DB語法有兩種請自行新增
 * 需注意DB和Helper必須每次都實體化
 * writable/readable和db/cursor都只能調用一種
 */

public class DBManager {

    private DBHelper helper;
    private ContentValues cv;
    private String sql;
    private int id;

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

        helper = getHelper(context);
        db = helper.getWritableDatabase();

        sql = "insert into " + tableName + " " + columns + " values " + questions;

        db.execSQL(sql, paramArgs);
        db.close();

    }

    public void insertAndroid(Context context, SQLiteDatabase db, String tableName, ContentValues cv, String key, String value){

        helper = getHelper(context);
        db = helper.getWritableDatabase();

        cv.put(key, value);

        db.insert(tableName, null, cv);
        db.close();

    }

    public void insertMain(Context context, SQLiteDatabase db, int id, String name, String number, String content){

        helper = getHelper(context);
        db = helper.getWritableDatabase();

        cv = new ContentValues();
        cv.put(MAIN_ID, id);
        cv.put(MAIN_NAME, name);
        cv.put(MAIN_NUMBER, number);
        cv.put(MAIN_CONTENT, content);

        db.insert(TABLE_NAME_MAIN, null, cv);
        db.close();

    }

    /**
     * 刪除資料
     */

    public void deleteAndroid(Context context, SQLiteDatabase db, String tableName, String columnClause, String[] paramArgs){

        helper = getHelper(context);
        db = helper.getWritableDatabase();

        columnClause = columnClause + " = ?";

        db.delete(tableName, columnClause, paramArgs);
        db.close();

    }

    public void deleteSQLite(Context context, SQLiteDatabase db, String tableName, String columns, String questions, Object[] paramArgs){

        helper = getHelper(context);
        db = helper.getWritableDatabase();

        sql = "delete from " + tableName + " where " + columns + " = " + questions;

        db.execSQL(sql,paramArgs);
        db.close();

    }

    /**
     * 搜尋Table
     */

    public Cursor selectAndroid(Context context, Cursor cursor, SQLiteDatabase db, String tableName){

        helper = getHelper(context);
        db = helper.getReadableDatabase();
        cursor = db.query(tableName, null, null, null, null, null, null);

        return cursor;

    }

    public Cursor selectSQLite(Context context, Cursor cursor, SQLiteDatabase db, String tableName){

        helper = getHelper(context);
        db = helper.getReadableDatabase();
        sql = "select * from " + tableName;
        cursor = db.rawQuery(sql, null);

        return cursor;

    }

    public Cursor selectRvCursorList(Context context, Cursor cursor, SQLiteDatabase db, ArrayList<Integer> arrayList, String tableName){

        helper = getHelper(context);
        db = helper.getReadableDatabase();
        sql = "select * from " + tableName;
        cursor = db.rawQuery(sql, null);

        cursorRvList(cursor, arrayList);

        return cursor;
    }

    /**
     * 搜尋 IN
     */

    public Cursor inSQLite(Context context, SQLiteDatabase db, Cursor cursor, String tableName, String columns, Object params){

        helper = getHelper(context);
        db = helper.getReadableDatabase();
        sql = "select * from " + tableName + " where " + columns + " in " + "("+ params +")";
        cursor = db.rawQuery(sql, null);

        return cursor;

    }

    public Cursor inAndroid(Context context, SQLiteDatabase db, Cursor cursor, String tableName, String columns, String[] paramArgs){

        helper = getHelper(context);
        db = helper.getReadableDatabase();
        columns = columns + " = ?";
        cursor = db.query(tableName, null, columns, paramArgs, null, null, null);

        return cursor;

    }

    public Cursor selectMainInCursorList(Context context, SQLiteDatabase db, Cursor cursor, String tableName, ArrayList<MainBean> arrayList, String columns, Object params){

        helper = getHelper(context);
        db = helper.getReadableDatabase();
        sql = "select * from " + tableName + " where " + columns + " in " + "("+ params +")";
        cursor = db.rawQuery(sql, null);
        cursorMainList(cursor, arrayList);

        Log.d(TAG, MSG + "IN " + sql);

        return cursor;

    }

    /**
     * Cursor轉ArrayList
     */

    public ArrayList<MainBean> cursorMainList(Cursor cursor, ArrayList<MainBean> arrayList){

        if (cursor != null) {

            while(cursor.moveToNext()) {

                id = cursor.getInt(cursor.getColumnIndex(DBConstant.MAIN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DBConstant.MAIN_NAME));
                String number = cursor.getString(cursor.getColumnIndex(DBConstant.MAIN_NUMBER));
                String content = cursor.getString(cursor.getColumnIndex(DBConstant.MAIN_CONTENT));

                cursor.moveToNext();

                arrayList.add(new MainBean(id, name, number, content)); //arr插入position位置

            }

        }

        cursor.close();

        return arrayList ;

    }

    public ArrayList<Integer> cursorRvList(Cursor cursor, ArrayList<Integer> arrayList){

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

}
