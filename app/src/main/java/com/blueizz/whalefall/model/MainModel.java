package com.blueizz.whalefall.model;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.blueizz.whalefall.ActivityRouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainModel {
    public static final int SUCCESS_GET_DATA = 0X001;
    private Handler mHandler;

    public MainModel(Handler handler) {
        this.mHandler = handler;
    }

    public void getData() {
        List<String> data = new ArrayList<>();
        Map<String, Class<? extends Activity>> activityMap = ActivityRouter.getActivityMap();
        for (String key : activityMap.keySet()) {
            data.add(key);
        }

        Message msg = mHandler.obtainMessage(SUCCESS_GET_DATA);
        msg.obj = data;
        mHandler.sendMessage(msg);
    }
}