package com.blueizz.reflection;

import android.util.Log;

/**
 * Created by blueizz on 2018/11/11.
 */

public class Phone {

    private String model = "iPhone";

    private float screenSize = 5.5f;

    public boolean isFullscreen = false;

    public Phone() {
    }

    public Phone(String model, float screenSize, boolean isFullscreen) {
        this.model = model;
        this.screenSize = screenSize;
        this.isFullscreen = isFullscreen;
    }

    public void call(String phoneNum) {
        Log.i("Phone", "call：" + phoneNum);
    }

    private void fingerUnlock() {
        Log.i("Phone", "fingerUnlock");
    }

}
