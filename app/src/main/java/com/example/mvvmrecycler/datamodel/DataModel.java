package com.example.mvvmrecycler.datamodel;

import android.app.Activity;
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

public class DataModel{

 private Function function = new Function();

 private DBManager dbManager = new DBManager();

 private SQLiteDatabase db;

 private RvAdapter rvAdapter;

 private String number, title , url, color;

 private int id;
 private Handler handler;
 private Runnable runnable;

 public void getData(GetAdapterSize getAdapterSize, MainActivityBinding binding, Activity activity, Context context, ArrayList<MainBean> arrView) {

  handler = new Handler();

  RequestQueue requestQueue = Volley.newRequestQueue(context);

  String apiUrl = "https://jsonplaceholder.typicode.com/photos";

  runnable = (new Runnable() {
   @Override
   public void run() {

    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
            new Response.Listener<JSONArray>() {

             @Override
             public void onResponse(final JSONArray response) {

              try {

               for (int i = 0; i < 100; i++) { // 資料長度可自由更改,最多5000筆

                JSONObject jsonObjContent = response.getJSONObject(i);

                number = jsonObjContent.get("id").toString().trim();
                title = jsonObjContent.get("title").toString().trim();
                url = jsonObjContent.get("thumbnailUrl").toString().trim();
                color = function.getBackgroudColor(color, url);
                url = function.getUrl(url);
                id = Integer.parseInt(number);

                arrView.add(new MainBean(id, number, title, url, color));

                //  依需求選擇DB是否存取本機資料
                dbManager.insertMain(context, id, number, title, url, color);

               }

              } catch (JSONException e) {
               e.printStackTrace();

              }

              rvAdapter = new RvAdapter(activity, arrView, context);
              binding.rv.setAdapter(rvAdapter);
              rvAdapter.notifyDataSetChanged();

              getAdapterSize.addArrSize(arrView.size());
              getAdapterSize.addRvAdapter(rvAdapter);

             }
            }, new Response.ErrorListener() {
     @Override
     public void onErrorResponse(VolleyError error) {

      function.setToast(activity, context, " 載入資料錯誤, 請檢查網路或聯絡資訊相關人員, 謝謝 ", Toast.LENGTH_SHORT);

     }
    });

    requestQueue.add(jsonArrayRequest);

   }
  });

  handler.post(runnable);

 }

 public interface GetAdapterSize{ // binding兩個model的值

  RvAdapter addRvAdapter(RvAdapter rvAdapter);

  int addArrSize(int arrSize);

 }

 public void setTextTitleBtn(Activity activity, SetTextTitleBtn setTextTitleBtn) {

  runnable = new Runnable() {
      @Override
      public void run() {

       setTextTitleBtn.number("Number");
       setTextTitleBtn.title("Title");
       setTextTitleBtn.url("Url");
       setTextTitleBtn.increase("增加");
       setTextTitleBtn.refresh("刷新");

      }
     };

    activity.runOnUiThread(runnable);

 }

 public interface SetTextTitleBtn {

  void number(String number);
  String title(String title);
  String url(String url);
  String increase(String increase);
  String refresh(String refresh);

 }

}