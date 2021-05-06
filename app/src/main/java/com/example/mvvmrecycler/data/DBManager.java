package com.example.mvvmrecycler.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import java.util.ArrayList;

import static com.example.mvvmrecycler.tools.Constant.DATABASE_NAME;
import static com.example.mvvmrecycler.tools.Constant.DATABASE_VERSION;
import static com.example.mvvmrecycler.tools.Constant.MAIN_COLOR;
import static com.example.mvvmrecycler.tools.Constant.MAIN_ID;
import static com.example.mvvmrecycler.tools.Constant.MAIN_NUMBER;
import static com.example.mvvmrecycler.tools.Constant.MAIN_TITLE;
import static com.example.mvvmrecycler.tools.Constant.MAIN_URL;
import static com.example.mvvmrecycler.tools.Constant.RV_ID;

/**
 * DB語法有兩種請自行新增
 * 需注意DB和Helper必須每次都實體化
 * writable/readable和db/cursor都只能調用一種
 * Cursor必須在主線程
 */

public class DBManager{

    private DBHelper helper;
    private SQLiteDatabase db;
    private String sql;
    private int id;
    private Cursor cursor;
    private final Handler handler = new Handler();
    private Runnable runnable;
    // private String dbColumnClause;

    /**
     * 取得SQLHelper
     */

    public void getHelper(Context context){

        handler.removeCallbacks(runnable);

        if (helper == null){
            helper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

    }

    /*
    public void openDB(Context context, String DBHath) {
        String path = context.getDatabasePath(DBHath).getPath();
        sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }*/

    /**
     *  刪除資料庫
     */

    public void deleteDb(Context context, String dbname) {

        runnable = () -> context.deleteDatabase(dbname);

        handler.post(runnable);

    }

    /**
     * 新增Data
     */

    public void insertSQLite(Context context, String tableName, String columns, String questions, Object[] paramArgs){

        runnable = () -> {

            getHelper(context);
            db = helper.getWritableDatabase();

            sql = "insert into " + tableName + " " + columns + " values " + questions;

            db.execSQL(sql, paramArgs);
            db.close();

        };

        handler.post(runnable);

    }

    /**
     * 刪除資料
     */

    public void deleteSQLite(Context context, String tableName, String columns, String questions, Object[] paramArgs){

        runnable = () -> {

            getHelper(context);
            db = helper.getWritableDatabase();

            sql = "delete from " + tableName + " where " + columns + " = " + questions;

            db.execSQL(sql,paramArgs);
            db.close();

        };

        handler.post(runnable);

    }

    /**
     * 搜尋Table
     */

    public void selectSQLite(Context context, String tableName){

        getHelper(context);
        db = helper.getReadableDatabase();
        sql = "select * from " + tableName;
        cursor = db.rawQuery(sql, null);

    }

    /**
     * 搜尋 IN
     */

    public void inSQLite(Context context, String tableName, String columns, Object params){

        getHelper(context);
        db = helper.getReadableDatabase();
        sql = "select * from " + tableName + " where " + columns + " in " + "("+ params +")";
        cursor = db.rawQuery(sql, null);

    }

    /**
     * Cursor轉ArrayList
     */

    public ArrayList<MainBean> cursorMainList(ArrayList<MainBean> arrayList){

        if (cursor.moveToFirst()) {

            for (int i = 0; i < cursor.getCount(); i++) {

                id = cursor.getInt(cursor.getColumnIndex(MAIN_ID));
                String number = cursor.getString(cursor.getColumnIndex(MAIN_NUMBER));
                String title = cursor.getString(cursor.getColumnIndex(MAIN_TITLE));
                String url = cursor.getString(cursor.getColumnIndex(MAIN_URL));
                String color = cursor.getString(cursor.getColumnIndex(MAIN_COLOR));

                arrayList.add(new MainBean(id, number, title, url, color));

                cursor.moveToNext();

            }

        }

        cursor.close(); // 先開後關

        return arrayList;

    }

    public ArrayList<RvBean> cursorRvList(){

        ArrayList<RvBean> arrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            for (int i = 0; i < cursor.getCount(); i++) {

                id = cursor.getInt(cursor.getColumnIndex(RV_ID));

                cursor.moveToNext();

                arrayList.add(new RvBean(id));

            }

        }

        cursor.close();

        return arrayList ;

    }

    /*

        public void insertAndroid(Context context, String tableName, ContentValues cv, String key, String value){

        runnable = () -> {

            handler.post(runnable);

            getHelper(context);
            db = helper.getWritableDatabase();

            cv.put(key, value);

            db.insert(tableName, null, cv);
            db.close();

        };

        handler.post(runnable);

    }

        public void selectAndroid(Context context, String tableName){

        getHelper(context);
        db = helper.getReadableDatabase();
        cursor = db.query(tableName, null, null, null, null, null, null);

    }

    public void deleteAndroid(Context context, String tableName, String columnClause, String[] paramArgs){

        dbColumnClause = columnClause;

        runnable = () -> {

            getHelper(context);
            db = helper.getWritableDatabase();

            dbColumnClause = dbColumnClause + " = ?";

            db.delete(tableName, dbColumnClause, paramArgs);
            db.close();

        };

        handler.post(runnable);

    }

    public void inAndroid(Context context, String tableName, String columns, String[] paramArgs){

        String dbColumns = columns;

        getHelper(context);
        db = helper.getReadableDatabase();
        dbColumns = dbColumns + " = ?";
        cursor = db.query(tableName, null, dbColumns, paramArgs, null, null, null);

    }

     */

}