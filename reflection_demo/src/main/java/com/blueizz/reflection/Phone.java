package com.blueizz.reflection;

/**
 * Created by blueizz on 2018/11/11.
 */

public class Phone {

    private String platform;
    public float screenSize;

    public Phone(String platform, float screenSize) {
        this.platform = platform;
        this.screenSize = screenSize;
    }

    public String call(String phoneNum) {
        return phoneNum;
    }

    private void unlock() {
    }

}
