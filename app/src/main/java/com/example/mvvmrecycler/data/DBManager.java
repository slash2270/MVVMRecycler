package com.example.mvvmrecycler.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
 * SQLite是單個事務控制的，一次新增就是一次數據庫操作，一次事務。如果幾千次for循環操作，必然存在效率問題。
 */

public class DBManager{

    private DBHelper helper;
    private SQLiteDatabase db;
    private StringBuffer sql;
    private int id;
    private Cursor cursor;

    // private String dbColumnClause;

    /**
     * 取得SQLHelper
     */

    public DBHelper getHelper(Context context){

        if (helper == null){
            helper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        return helper;

    }

    /*
    public void openDB(Context context, String DBHath) {
        String path = context.getDatabasePath(DBHath).getPath();
        sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }*/

    /**
     *  關閉資料庫
     */

    public void closeDb(){
        db.close();
    }

    /**
     *  刪除資料庫
     */

    public void deleteDb(Context context, String dbname){

        context.deleteDatabase(dbname);

    }

    /**
     * 新增
     */

    public void insertLine(Context context, String tableName, String columns, String questions, Object[] paramArgs){

        getHelper(context);
        db = helper.getWritableDatabase();
        db.beginTransaction();

        sql = new StringBuffer();
        sql.append("insert into ").append(tableName).append(" ").append(columns).append(" values ").append(questions);

        db.execSQL(sql.toString(), paramArgs);
      //  db.close();

        db.setTransactionSuccessful();
        db.endTransaction();

    }

    public void insertLines(String tableName, String columns, String questions, Object[] paramArgs, SQLiteDatabase database){

        sql = new StringBuffer();
        sql.append("insert into ").append(tableName).append(" ").append(columns).append(" values ").append(questions);

        database.execSQL(sql.toString(), paramArgs);
      //  db.close();

    }

    /**
     * 刪除
     */

    public void deleteLine(Context context, String tableName, String columns, String questions, Object[] paramArgs){

        getHelper(context);
        db = helper.getWritableDatabase();

        sql = new StringBuffer();
        sql.append("delete from ").append(tableName).append(" where ").append(columns).append(" = ").append(questions);

        db.execSQL(sql.toString(), paramArgs);
     //   db.close();

    }

    public void deleteLines(String tableName, SQLiteDatabase database) {

        sql = new StringBuffer();
        sql.append("delete from ").append(tableName);

        database.execSQL(sql.toString());
        //   db.close();

    }

    public void deleteTable(Context context, String tableName) {

        getHelper(context);
        db = helper.getWritableDatabase();
        db.beginTransaction();

        sql = new StringBuffer();
        sql.append("drop table ").append(tableName);

        db.execSQL(sql.toString());
     //   db.close();

        db.setTransactionSuccessful();
        db.endTransaction();

    }

    /**
     * 搜尋Table
     */

    public void selectTable(Context context, String tableName){

        getHelper(context);
        db = helper.getReadableDatabase();
        db.beginTransaction();

        sql = new StringBuffer();
        sql.append("select * from ").append(tableName);

        cursor = db.rawQuery(sql.toString(), null);

        db.setTransactionSuccessful();
        db.endTransaction();

    }

    /**
     * 搜尋 IN
     */

    public void inSearch(Context context, String tableName, String columns, Object params){

        getHelper(context);
        db = helper.getReadableDatabase();
        db.beginTransaction();

        sql = new StringBuffer();
        sql.append("select * from ").append(tableName).append(" where ").append(columns).append(" in ").append("(").append(params).append(")");

        cursor = db.rawQuery(sql.toString(), null);

        db.setTransactionSuccessful();
        db.endTransaction();

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
