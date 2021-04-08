package com.example.mvvmrecycler.datamodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mvvmrecycler.adapter.RvAdapter;
import com.example.mvvmrecycler.data.DBManager;
import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.databinding.MainActivityBinding;
import com.example.mvvmrecycler.tools.Function;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataModel {

 private Function function = new Function();

 private DBManager dbManager = new DBManager();

 private SQLiteDatabase db;

 private RvAdapter rvAdapter;

 private String number, title , url, color;

 private int id;

 public ArrayList<MainBean> getData(GetAdapterSize getAdapterSize, MainActivityBinding binding, Context context, ArrayList<MainBean> arrView) {

  RequestQueue requestQueue = Volley.newRequestQueue(context);

  String apiUrl = "https://jsonplaceholder.typicode.com/photos";

  JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
          new Response.Listener<JSONArray>() {

           @Override
           public void onResponse(final JSONArray response) {

            try {

             for (int i = 0; i < 15; i++) {

              JSONObject jsonObjContent = response.getJSONObject(i);

              number = jsonObjContent.get("id").toString().trim();
              title = jsonObjContent.get("title").toString().trim();
              url = jsonObjContent.get("thumbnailUrl").toString().trim();
              color = function.getBackgroudColor(color, url);
              url = function.getUrl(url);
              id = Integer.parseInt(number);

              arrView.add(new MainBean(id, number, title, url, color));

              //  依需求選擇DB是否存取本機資料
              dbManager.insertMain(context, db, id, number, title, url, color);

             }

            } catch (JSONException e) {
             e.printStackTrace();

            }

            rvAdapter = new RvAdapter(arrView, context);
            binding.rv.setAdapter(rvAdapter);
            rvAdapter.notifyDataSetChanged();

            getAdapterSize.addArrSize(arrView.size());
            getAdapterSize.addRvAdapter(rvAdapter);

           }
          }, new Response.ErrorListener() {
   @Override
   public void onErrorResponse(VolleyError error) {

    function.setToast(context, " 載入資料錯誤, 請檢查網路或聯絡資訊相關人員, 謝謝 ", Toast.LENGTH_SHORT);

   }
  });

  requestQueue.add(jsonArrayRequest);

  return arrView;

 }

 public interface GetAdapterSize{ // binding兩個model的值

  RvAdapter addRvAdapter(RvAdapter rvAdapter);

  int addArrSize(int arrSize);

 }

 public void setTextTitleBtn(SetTextTitleBtn setTextTitleBtn) {

  new Handler().post(new Runnable() {
   @Override
   public void run() {

    setTextTitleBtn.number("Number");
    setTextTitleBtn.title("Title");
    setTextTitleBtn.url("Url");
    setTextTitleBtn.increase("增加");
    setTextTitleBtn.refresh("刷新");

   }

  });

 }

 public interface SetTextTitleBtn {

  void number(String number);
  void title(String title);
  void url(String url);
  void increase(String increase);
  void refresh(String refresh);

 }

}
