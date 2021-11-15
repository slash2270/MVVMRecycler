package com.example.mvvmrecycler.datamodel;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mvvmrecycler.adapter.RvAdapter;
import com.example.mvvmrecycler.data.MainBean;
import com.example.mvvmrecycler.databinding.MainActivityBinding;
import com.example.mvvmrecycler.tools.Function;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import static com.example.mvvmrecycler.tools.Function.getBackgroundColor;
import static com.example.mvvmrecycler.tools.Function.getUrl;

public class DynamicDataModel {

    public void getData(Activity activity, Context context, MainActivityBinding binding, ArrayList<MainBean> arrView, GetAdapterSize getAdapterSize) { // Volley

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String apiUrl = "https://jsonplaceholder.typicode.com/photos";

        // 資料長度可自由更改,最多5000筆
        //  依需求選擇DB是否存取本機資料
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                response -> {

                    try {

                        for (int i = 0; i < response.length(); i++) { // 資料長度可自由更改,最多5000筆

                            JSONObject jsonObjContent = response.getJSONObject(i);

                            String number = jsonObjContent.get("id").toString().trim();
                            String title = jsonObjContent.get("title").toString().trim();
                            String url = jsonObjContent.get("thumbnailUrl").toString().trim();
                            String color = getBackgroundColor(url);
                            url = getUrl(url);
                            int id = Integer.parseInt(number);

                            arrView.add(new MainBean(id, number, title, url, color));

                            //  依需求選擇DB是否存取本機資料
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

                }, error -> Function.setToast(context, " 載入資料錯誤, 請檢查網路或聯絡資訊相關人員, 謝謝 ", Toast.LENGTH_SHORT));

        requestQueue.add(jsonArrayRequest);

    }

    public interface GetAdapterSize { // binding兩個model的值

        void addRvAdapter(RvAdapter rvAdapter);

        void addArrSize(int arrSize);

    }

    /*extends AsyncTask<String, Void, String> {

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

    }*/
}