package com.example.mvvmrecycler.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mvvmrecycler.R;

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

}