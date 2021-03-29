package com.example.mvvmrecycler.datamodel;

import android.os.Handler;

import com.example.mvvmrecycler.data.DataBean;

import java.util.ArrayList;

public class DataModel {

   public ArrayList<DataBean> setRvData(ArrayList<DataBean> arrDataBean) {

    DataBean dataBean1 = new DataBean("白月光與硃砂痣", "1", "從前的歌謠 都在指尖繞\n" +
            "得不到的美好 總在心間撓\n" +
            "白飯粒無處拋 蚊子血也抹不掉",
            "刪除");

    DataBean dataBean2 = new DataBean("你的答案","2","也許世界就這樣\n" +
            "我也還在路上\n" +
            "沒有人能訴說\n" +
            "\n" +
            "也許我只能沉默\n" +
            "眼淚濕潤眼眶\n" +
            "可又不甘懦弱",
            "刪除");

    DataBean dataBean3 = new DataBean("飛鳥和蟬","3","你說青澀最搭初戀 如小雪落下海岸線\n" +
            "第五個季節某一天上演 我們有相遇的時間\n" +
            "你說空瓶適合許願 在風暖月光的地點\n" +
            "第十三月你就如期出現 海之角也不再遙遠",
            "刪除");

    arrDataBean.add(dataBean1);
    arrDataBean.add(dataBean2);
    arrDataBean.add(dataBean3);

    return arrDataBean;

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
