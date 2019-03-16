package com.blueizz.whalefall.model;

import android.app.Activity;
import android.content.Context;

import com.blueizz.commom.mvp.SafeHandler;
import com.blueizz.commom.mvp.model.BaseModel;
import com.blueizz.whalefall.ActivityRouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainModel extends BaseModel {
    public static final int SUCCESS_GET_DATA = 0X001;

    public MainModel(Context ctx, SafeHandler handler) {
        super(ctx, handler);
    }

    public void getData() {
        List<String> data = new ArrayList<>();
        Map<String, Class<? extends Activity>> activityMap = ActivityRouter.getActivityMap();
        for (Map.Entry<String, Class<? extends Activity>> entry : activityMap.entrySet()) {
            data.add(entry.getKey());
        }
        resultSuccess(SUCCESS_GET_DATA, data);
    }

    @Override
    public void onDestroy() {
    }
}
