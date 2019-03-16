package com.blueizz.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TestService extends Service {
    private static final String TAG = "TestService";

    private TestBinder mBinder = new TestBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "service onCreate...,Thread:" + Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "service onStartCommand...");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinder = null;
        Log.i(TAG, "service onDestroy...");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "service onBind...");
        //这里需要将Binder实例返回
        return mBinder;
    }

    class TestBinder extends Binder {

        public void startDownload() {
            Log.i(TAG, "startDownload(),Thread:" + Thread.currentThread().getName());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "startDownload() executed...,Thread:" + Thread.currentThread().getName());
                }
            }).start();

        }
    }
}
