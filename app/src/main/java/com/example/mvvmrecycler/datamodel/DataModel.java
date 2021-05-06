package com.example.mvvmrecycler.datamodel;

import android.app.Activity;
import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.mvvmrecycler.tools.Constant.MAIN_COLOR;
import static com.example.mvvmrecycler.tools.Constant.MAIN_ID;
import static com.example.mvvmrecycler.tools.Constant.MAIN_NUMBER;
import static com.example.mvvmrecycler.tools.Constant.MAIN_TITLE;
import static com.example.mvvmrecycler.tools.Constant.MAIN_URL;
import static com.example.mvvmrecycler.tools.Constant.TABLE_NAME_MAIN;
import static com.example.mvvmrecycler.tools.Function.getBackgroundColor;
import static com.example.mvvmrecycler.tools.Function.getUrl;
import static com.example.mvvmrecycler.tools.Function.setToast;

public class DataModel{

 private final DBManager dbManager = new DBManager();
 private String number, title , url, color;
 private int id, i;

 private RvAdapter rvAdapter;

 public void setData(GetAdapterSize getAdapterSize, MainActivityBinding binding, Activity activity, Context context, ArrayList<MainBean> arrView) {

  RequestQueue requestQueue = Volley.newRequestQueue(context);

  String apiUrl = "https://jsonplaceholder.typicode.com/photos";

  // 資料長度可自由更改,最多5000筆
  //  依需求選擇DB是否存取本機資料
  Runnable runnable = (new Runnable() {
   @Override
   public void run() {

    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
            new Response.Listener<JSONArray>() {

             @Override
             public void onResponse(final JSONArray response) {

              try {

               for (i = 0; i < 100; i++) { // 資料長度可自由更改,最多5000筆

                JSONObject jsonObjContent = response.getJSONObject(i);

                number = jsonObjContent.get("id").toString().trim();
                title = jsonObjContent.get("title").toString().trim();
                url = jsonObjContent.get("thumbnailUrl").toString().trim();
                color = getBackgroundColor(url);
                url = getUrl(url);
                id = Integer.parseInt(number);

                arrView.add(new MainBean(id, number, title, url, color));

                //  依需求選擇DB是否存取本機資料
                Object[] objectArr = new Object[]{id, number, title, url, color};
                String columns = "(" + MAIN_ID + ", " + MAIN_NUMBER + ", " + MAIN_TITLE + ", " + MAIN_URL + ", " + MAIN_COLOR + ")";
                String questions = "(?,?,?,?,?)";
                dbManager.insertSQLite(context, TABLE_NAME_MAIN, columns, questions, objectArr);

               }

              } catch (JSONException e) {
               e.printStackTrace();

              }

              rvAdapter = AddAdapter.setAdapter(activity, context, binding, arrView);

              getAdapterSize.addArrSize(arrView.size());
              getAdapterSize.addRvAdapter(rvAdapter);

             }
            }, new Response.ErrorListener() {
     @Override
     public void onErrorResponse(VolleyError error) {

      setToast(activity, context, " 載入資料錯誤, 請檢查網路或聯絡資訊相關人員, 謝謝 ", Toast.LENGTH_SHORT);

     }
    });

    requestQueue.add(jsonArrayRequest);

   }
  });

  new Thread(runnable).start();

 }

 public static class AddAdapter{

  public static RvAdapter setAdapter(Activity activity, Context context, MainActivityBinding binding, ArrayList<MainBean> arrView){

   RvAdapter rvAdapter = new RvAdapter(activity, arrView, context);
   binding.rv.setAdapter(rvAdapter);
   rvAdapter.notifyDataSetChanged();

   return rvAdapter;

  }

 }


 public interface GetAdapterSize{ // binding兩個model的值

  RvAdapter addRvAdapter(RvAdapter rvAdapter);

  int addArrSize(int arrSize);

 }

 public void setTextTitleBtn(SetTextTitleBtn setTextTitleBtn) {

       setTextTitleBtn.number("Number");
       setTextTitleBtn.title("Title");
       setTextTitleBtn.url("Url");
       setTextTitleBtn.increase("增加");
       setTextTitleBtn.refresh("刷新");

     };


 public interface SetTextTitleBtn {

  void number(String number);
  String title(String title);
  String url(String url);
  String increase(String increase);
  String refresh(String refresh);

 }

}