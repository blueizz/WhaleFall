package com.blueizz.thread;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class TestIntentService extends IntentService {
    private static final String TAG = "TestIntentService";

    public TestIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getStringExtra("task_action");
        SystemClock.sleep(3000);
        if ("com.blueizz.thread.TestIntentService".equals(action)) {
            Log.i(TAG, "onHandleIntent:" + Thread.currentThread().getName());
        }
    }

}
