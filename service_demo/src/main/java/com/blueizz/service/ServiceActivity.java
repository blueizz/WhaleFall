package com.blueizz.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class ServiceActivity extends Activity {

    private Button startService;
    private Button stopService;
    private Button bindService;
    private Button unbindService;
    private Button startDownload;

    private Button startFrontService;
    private Button stopFrontService;

    private TestService.TestBinder testBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            testBinder = (TestService.TestBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_service);
        findView();
        setListener();
    }

    private void findView() {
        startService = findViewById(R.id.btn_start_service);
        stopService = findViewById(R.id.btn_stop_service);
        bindService = findViewById(R.id.btn_bind_service);
        unbindService = findViewById(R.id.btn_unbind_service);
        startDownload = findViewById(R.id.btn_start_download);

        startFrontService = findViewById(R.id.btn_start_front_service);
        stopFrontService = findViewById(R.id.btn_stop_front_service);
    }

    private void setListener() {
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, TestService.class);
                startService(intent);
            }
        });

        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, TestService.class);
                stopService(intent);
            }
        });

        bindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, TestService.class);
                bindService(intent, connection, BIND_AUTO_CREATE);
            }
        });

        unbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(connection);
            }
        });

        startDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testBinder != null)
                    testBinder.startDownload();
            }
        });

        startFrontService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, FrontService.class);
                startService(intent);
            }
        });

        stopFrontService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, FrontService.class);
                stopService(intent);
            }
        });
    }
}
