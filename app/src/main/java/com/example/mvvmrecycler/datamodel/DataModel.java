package com.example.mvvmrecycler.datamodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.data.MainBean;

import java.util.ArrayList;

public class DataModel {

   private DBManager dbManager;

   private SQLiteDatabase db;

   public ArrayList<MainBean> setRvData(ArrayList<MainBean> arrMainBean, Context context) {

    DBManager dbManager = new DBManager();

    MainBean mainBean1 = new MainBean
            (1,"白月光與硃砂痣","1","從前的歌謠 都在指尖繞\n" +
            "得不到的美好 總在心間撓\n" +
            "白飯粒無處拋 蚊子血也抹不掉");

    MainBean mainBean2 = new MainBean
            (2,"你的答案","2","也許世界就這樣\n" +
            "我也還在路上\n" +
            "沒有人能訴說\n" +
            "\n" +
            "也許我只能沉默\n" +
            "眼淚濕潤眼眶\n" +
            "可又不甘懦弱");

    MainBean mainBean3 = new MainBean
            (3,"飛鳥和蟬","3","你說青澀最搭初戀 如小雪落下海岸線\n" +
            "第五個季節某一天上演 我們有相遇的時間\n" +
            "你說空瓶適合許願 在風暖月光的地點\n" +
            "第十三月你就如期出現 海之角也不再遙遠");

    //  依需求選擇DB是否存取本機資料
       dbManager.insertMain(context, db, mainBean1.getId(), mainBean1.getName(), mainBean1.getNumber(), mainBean1.getContent());
       dbManager.insertMain(context, db, mainBean2.getId(), mainBean2.getName(), mainBean2.getNumber(), mainBean2.getContent());
       dbManager.insertMain(context, db, mainBean3.getId(), mainBean3.getName(), mainBean3.getNumber(), mainBean3.getContent());

       arrMainBean.add(mainBean1);
       arrMainBean.add(mainBean2);
       arrMainBean.add(mainBean3);

    return arrMainBean;

    }

 public void retrieveData(final dataCallBack callback){

  new Handler().post(new Runnable() {
   @Override
   public void run() {

    callback.nameData("歌曲名稱");
    callback.numberData("歌曲排名");
    callback.contentData("歌詞內容");

         }

    });

 }

 public interface dataCallBack{

  void nameData(String name);
  void numberData(String number);
  void contentData(String content);

     }

}
