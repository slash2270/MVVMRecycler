package com.example.mvvmrecycler.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.example.mvvmrecycler.tools.Constant.DATABASE_NAME;
import static com.example.mvvmrecycler.tools.Constant.DATABASE_VERSION;

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

        db.setTransactionSuccessful();
        db.endTransaction();

    }

    public void insertLines(String tableName, String columns, String questions, Object[] paramArgs, SQLiteDatabase database){

        sql = new StringBuffer();
        sql.append("insert into ").append(tableName).append(" ").append(columns).append(" values ").append(questions);

        database.execSQL(sql.toString(), paramArgs);

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

    }

    public void deleteLines(String tableName, SQLiteDatabase database) {

        sql = new StringBuffer();
        sql.append("delete from ").append(tableName);

        database.execSQL(sql.toString());

    }

    public void deleteTable(Context context, String tableName) {

        getHelper(context);
        db = helper.getWritableDatabase();
        db.beginTransaction();

        sql = new StringBuffer();
        sql.append("drop table ").append(tableName);

        db.execSQL(sql.toString());

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

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {

                id = cursor.getInt(0);
                String title = cursor.getString(1);
                String url = cursor.getString(2);
                String color = cursor.getString(3);

                arrayList.add(new MainBean(id, title, url, color));

                cursor.moveToNext();

            }

            cursor.close();

        }

        return arrayList;

    }

    public ArrayList<RvBean> cursorRvList(){

        ArrayList<RvBean> arrayList = new ArrayList<>();

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {

                id = cursor.getInt(0);

                cursor.moveToNext();

                arrayList.add(new RvBean(id));

            }

            cursor.close();

        }

        return arrayList ;

    }

    /*

        public void insertAndroid(Context context, String tableName, ContentValues cv, String key, String value){

       getHelper(context);
            db = helper.getWritableDatabase();

            cv.put(key, value);

            db.insert(tableName, null, cv);

    }

        public void selectAndroid(Context context, String tableName){

        getHelper(context);
        db = helper.getReadableDatabase();
        cursor = db.query(tableName, null, null, null, null, null, null);

    }

    public void deleteAndroid(Context context, String tableName, String columnClause, String[] paramArgs){

        dbColumnClause = columnClause;

           getHelper(context);
            db = helper.getWritableDatabase();

            dbColumnClause = dbColumnClause + " = ?";

            db.delete(tableName, dbColumnClause, paramArgs);

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
