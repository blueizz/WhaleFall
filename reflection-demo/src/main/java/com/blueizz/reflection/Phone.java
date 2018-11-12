package com.blueizz.reflection;

import android.util.Log;

/**
 * Created by blueizz on 2018/11/11.
 */

public class Phone {

    private String model = "iPhone";

    private float screenSize = 5.5f;

    public boolean isFullscreen = false;

    public void call() {
        Log.i("Phone", "call");
    }

    private void fingerUnlock() {
        Log.i("Phone", "fingerUnlock");
    }

}
