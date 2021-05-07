package com.example.mvvmrecycler.datamodel;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import com.example.mvvmrecycler.adapter.RvAdapter;
import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.databinding.MainActivityBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.mvvmrecycler.tools.Function.getBackgroundColor;
import static com.example.mvvmrecycler.tools.Function.getUrl;
import static com.example.mvvmrecycler.tools.Function.httpConnectionGet;

public class DynamicDataModel extends AsyncTask<String, Void, String> {

    private final Context context;
    private final GetAdapterSize getAdapterSize;
    private final MainActivityBinding binding;
    private final Activity activity;
    private final ArrayList<MainBean> arrView;

    public DynamicDataModel(Context context, GetAdapterSize getAdapterSize, MainActivityBinding binding, Activity activity, ArrayList<MainBean> arrView) {
        this.context = context;
        this.getAdapterSize = getAdapterSize;
        this.binding = binding;
        this.activity = activity;
        this.arrView = arrView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // progressdialog
    }

    @Override
    protected String doInBackground(String... strings) {
        String apiUrl = "https://jsonplaceholder.typicode.com/photos";
        return httpConnectionGet(context, apiUrl);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        setData(s);

    }

    public void setData(String result) {

        try {

            JSONArray jsArrResponse = new JSONArray(result);

            int i;
            for (i = 0; i < 1000; i++) { // 資料長度可自由更改,最多5000筆

                JSONObject jsonObjContent = jsArrResponse.getJSONObject(i);

                String number = jsonObjContent.get("id").toString().trim();
                String title = jsonObjContent.get("title").toString().trim();
                String url = jsonObjContent.get("thumbnailUrl").toString().trim();
                String color = getBackgroundColor(url);
                url = getUrl(url);
                int id = Integer.parseInt(number);

                arrView.add(new MainBean(id, number, title, url, color));

                SetDbData.addDbData(context, id, number, title, url, color);

            }

        } catch (JSONException e) {
            e.printStackTrace();

            System.out.println(e.toString().trim());

        }

        AddAdapter addAdapter = new AddAdapter();
        RvAdapter rvAdapter = addAdapter.setAdapter(activity, context, binding, arrView);

        getAdapterSize.addArrSize(arrView.size());
        getAdapterSize.addRvAdapter(rvAdapter);

    }

    public interface GetAdapterSize { // binding兩個model的值

        void addRvAdapter(RvAdapter rvAdapter);

        void addArrSize(int arrSize);

    }

    /*
    public void setData(GetAdapterSize getAdapterSize, MainActivityBinding binding, Activity activity, Context context, ArrayList<MainBean> arrView) { // Volley

        DBManager dbManager = new DBManager();

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String apiUrl = "https://jsonplaceholder.typicode.com/photos";

        // 資料長度可自由更改,最多5000筆
        //  依需求選擇DB是否存取本機資料
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                response -> {

                    try {

                        for (i = 0; i < 5000; i++) { // 資料長度可自由更改,最多5000筆

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

                        System.out.println(e.toString().trim());

                    }

                    rvAdapter = AddAdapter.setAdapter(activity, context, binding, arrView);

                    getAdapterSize.addArrSize(arrView.size());
                    getAdapterSize.addRvAdapter(rvAdapter);

                }, error -> setToast(context, " 載入資料錯誤, 請檢查網路或聯絡資訊相關人員, 謝謝 ", Toast.LENGTH_SHORT));

        requestQueue.add(jsonArrayRequest);

    }*/

}