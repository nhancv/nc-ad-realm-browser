package com.nhancv.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.nhancv.realmbowser.NRealmServer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private NRealmServer realmServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.activity_main_tv_msg);
        realmServer = new NRealmServer();
        Log.d(TAG, "Server address: " + realmServer.getServerAddress(this));
        textView.setText(realmServer.getServerAddress(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            realmServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        realmServer.stop();
    }
}
