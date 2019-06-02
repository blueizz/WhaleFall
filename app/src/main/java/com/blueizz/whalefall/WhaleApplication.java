package com.blueizz.whalefall;

import android.app.Application;
import android.content.Intent;

import com.blueizz.whalefall.activity.MainActivity;
import com.mob.MobSDK;
import com.squareup.leakcanary.LeakCanary;

public class WhaleApplication extends Application {

    private Thread.UncaughtExceptionHandler exceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            restartApp();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        MobSDK.init(this);

        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
    }

    /**
     * 重启app
     */
    private void restartApp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
