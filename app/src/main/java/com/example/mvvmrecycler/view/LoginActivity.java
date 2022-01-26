package com.example.mvvmrecycler.view;
import static com.example.mvvmrecycler.tools.Constant.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mvvmrecycler.R;
import com.example.mvvmrecycler.data.DBManager;

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        LoginDb loginDb = new LoginDb();
        loginDb.delDb(getApplicationContext());

        LoginBtn addBtn = new LoginBtn();
        addBtn.setBtn(this);

    }

    @Override
    protected void onDestroy() {
        DBManager dbManager = new DBManager();
        dbManager.deleteDb(getApplicationContext(), DATABASE_NAME);
        super.onDestroy();
    }

}
