package com.example.mvvmrecycler.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.example.mvvmrecycler.R;
import com.example.mvvmrecycler.data.DBManager;

import static com.example.mvvmrecycler.data.DBConstant.DATABASE_NAME;

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        delDb();

        setBtn();

    }

    private void delDb(){

        DBManager dbManager = new DBManager();
        dbManager.deleteDb(getApplicationContext(), DATABASE_NAME);

    }

    private void setBtn(){

        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setGravity(Gravity.CENTER);
        btnLogin.setText("登入");
        btnLogin.setTextSize(20);
        btnLogin.setTextColor(Color.parseColor("#B4B4B4"));
        btnLogin.getPaint().setFakeBoldText(true);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

}