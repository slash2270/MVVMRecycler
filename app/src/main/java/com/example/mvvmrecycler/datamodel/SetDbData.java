package com.example.mvvmrecycler.datamodel;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.data.MainBean;

import static com.example.mvvmrecycler.tools.Constant.HANDLER_MESSAGE;
import static com.example.mvvmrecycler.tools.Constant.MAIN_COLOR;
import static com.example.mvvmrecycler.tools.Constant.MAIN_ID;
import static com.example.mvvmrecycler.tools.Constant.MAIN_NUMBER;
import static com.example.mvvmrecycler.tools.Constant.MAIN_TITLE;
import static com.example.mvvmrecycler.tools.Constant.MAIN_URL;
import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_MAIN;

import java.util.ArrayList;

public class SetDbData {

    public void addDbData(Handler handler, Context context, ArrayList<MainBean> arrayList) { // 依需求選擇DB是否存取本機資料

        new Thread(() -> {

            Looper.prepare();

            for(int i = 0; i < arrayList.size(); i++){

                DBManager dbManager = new DBManager();
                Object[] objectArr = new Object[]{arrayList.get(i).id, arrayList.get(i).number, arrayList.get(i).title, arrayList.get(i).url, arrayList.get(i).color};
                String columns = "(" + MAIN_ID + ", " + MAIN_NUMBER + ", " + MAIN_TITLE + ", " + MAIN_URL + ", " + MAIN_COLOR + ")";
                String questions = "(?,?,?,?,?)";
                dbManager.insertSQLite(context, TABLE_NAME_MAIN, columns, questions, objectArr);

                if(i == arrayList.size() - 1){
                    Message message = new Message();
                    message.what = HANDLER_MESSAGE;
                    handler.sendMessage(message);
                }

            }

            Looper.loop();

        }).start();

    }

}