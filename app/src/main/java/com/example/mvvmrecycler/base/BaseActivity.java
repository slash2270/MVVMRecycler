package com.example.mvvmrecycler.base;

import static com.example.mvvmrecycler.tools.Constant.LIFE_CYCLE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void setUpResumeComponent();

    @Override
    protected void onResume() {
        setUpResumeComponent();
        super.onResume();
        Log.e(LIFE_CYCLE,"onResume");
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            onCatchReceive(action, intent);
        }
    };

    protected void onCatchReceive(String action, Intent intent) {

    }

    protected void sendBroadcast(Intent... intents){
        for (Intent intent:
                intents) {
            sendBroadcast(intent);
        }
    }
    protected void sendBroadcast(String... actions){
        for (String act:
                actions) {
            sendBroadcast(new Intent(act));
        }
    }

    protected void registerReceiver(String... actions) {
        IntentFilter filter = new IntentFilter();
        for (String action : actions) {
            filter.addAction(action);
        }
        registerReceiver(receiver, filter);
    }

}
