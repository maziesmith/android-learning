package com.example.elina.netty_lab4;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.elina.netty_lab4.network.client.client.Client;
import com.example.elina.netty_lab4.network.client.server.Server;

public class MainActivity extends AppCompatActivity {

    private Button buttonStart, buttonStop, buttonSend;
    private TextView textViewMssg, textViewCnt;
    private EditText editText;
    MyMainReceiver myMainReceiver;
    Intent myIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        startServer();
        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        textViewCnt = (TextView) findViewById(R.id.textViewCnt);
        textViewMssg = (TextView) findViewById(R.id.textViewMessage);
        editText = (EditText) findViewById(R.id.editText);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msgToService = editText.getText().toString();

                Intent intent = new Intent();
                intent.setAction(MyService.ACTION_MSG_TO_SERVICE);
                intent.putExtra(MyService.KEY_MSG_TO_SERVICE, msgToService);
                sendBroadcast(intent);
            }
        });
    }

    private void startService() {
        myIntent = new Intent(MainActivity.this, MyService.class);
        startService(myIntent);
    }

    private void stopService() {
        if (myIntent != null) {
            stopService(myIntent);
        }
        myIntent = null;
    }

    @Override
    protected void onStart() {
        myMainReceiver = new MyMainReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService.ACTION_UPDATE_CNT);
        intentFilter.addAction(MyService.ACTION_UPDATE_MSG);
        registerReceiver(myMainReceiver, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(myMainReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService();
    }

    private class MyMainReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MyService.ACTION_UPDATE_CNT)) {
                int int_from_service = intent.getIntExtra(MyService.KEY_INT_FROM_SERVICE, 0);
                textViewCnt.setText(String.valueOf(int_from_service));
            } else if (action.equals(MyService.ACTION_UPDATE_MSG)) {
                String string_from_service = intent.getStringExtra(MyService.KEY_STRING_FROM_SERVICE);
                textViewMssg.append("\n" +String.valueOf(string_from_service));
            }
        }
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.INTERNET},
                    1
            );
        }
    }

    private void startServer() {
        Intent intent = new Intent(this, ServerService.class);
        startService(intent);
    }
}

