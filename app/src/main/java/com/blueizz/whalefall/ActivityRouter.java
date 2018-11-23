package com.blueizz.whalefall;

import android.app.Activity;

import com.blueizz.demo.annotation_demo.AnnotationActivity;
import com.blueizz.reflection.ReflectionActivity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ActivityRouter {

    private static final Map<String, Class<? extends Activity>> activityMap = new HashMap<>();

    static {
        activityMap.put("反射", ReflectionActivity.class);
        activityMap.put("注解", AnnotationActivity.class);
    }

    public static Map<String, Class<? extends Activity>> getActivityMap() {
        return activityMap;
    }
}
