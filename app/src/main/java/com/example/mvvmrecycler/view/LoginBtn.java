package com.example.mvvmrecycler.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.example.mvvmrecycler.R;

public class LoginBtn {

    public void setBtn(Activity activity){

        Button btnLogin = activity.findViewById(R.id.btnLogin);

        btnLogin.setGravity(Gravity.CENTER);
        btnLogin.setText("登入");
        btnLogin.setTextSize(20);
        btnLogin.setTextColor(Color.parseColor("#B4B4B4"));
        btnLogin.getPaint().setFakeBoldText(true);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);

            }
        });

    }


}
