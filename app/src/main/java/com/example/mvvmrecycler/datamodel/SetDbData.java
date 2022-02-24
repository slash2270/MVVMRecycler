package com.example.mvvmrecycler.datamodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;

import com.example.mvvmrecycler.data.DBHelper;
import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.data.RvBean;

import static com.example.mvvmrecycler.tools.Constant.MESSAGE_WHAT_DATA;
import static com.example.mvvmrecycler.tools.Constant.MAIN_COLUMNS;
import static com.example.mvvmrecycler.tools.Constant.MAIN_QUESTIONS;
import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_MAIN;
import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_RV;

import java.util.ArrayList;

public class SetDbData {

    public void addDbData(Handler handler, Context context, ArrayList<MainBean> arrayList) { // 依需求選擇DB是否存取本機資料

        DBManager dbManager = new DBManager();
        DBHelper dbHelper = dbManager.getHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        clearTable(context, db, dbManager);

        insertLines(db, arrayList, dbManager, handler);

    }

    private void clearTable(Context context, SQLiteDatabase db, DBManager dbManager){

        db.beginTransaction();

        dbManager.selectTable(context, TABLE_NAME_MAIN);
        ArrayList<MainBean> listMain = dbManager.cursorMainList(new ArrayList<>());

        if(listMain.size() > 0){

            dbManager.deleteLines(TABLE_NAME_MAIN, db);

        }

        dbManager.selectTable(context, TABLE_NAME_RV);
        ArrayList<RvBean> listRv = dbManager.cursorRvList();

        if(listRv.size() > 0){

            dbManager.deleteLines(TABLE_NAME_RV, db);

        }

        db.setTransactionSuccessful();
        db.endTransaction();

    }

    private void insertLines(SQLiteDatabase db, ArrayList<MainBean> arrayList, DBManager dbManager, Handler handler){

        db.beginTransaction();

        for(int j = 0; j < arrayList.size(); j++){

            Object[] objectArr = new Object[]{arrayList.get(j).id, arrayList.get(j).title, arrayList.get(j).url, arrayList.get(j).color};
            dbManager.insertLines(TABLE_NAME_MAIN, MAIN_COLUMNS, MAIN_QUESTIONS, objectArr, db);

            if (j == arrayList.size() - 1) {
                Message message = new Message();
                message.what = MESSAGE_WHAT_DATA;
                handler.sendMessage(message);
            }

        }

        db.setTransactionSuccessful();
        db.endTransaction();

    }

}