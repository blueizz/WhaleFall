package com.blueizz.whalefall;

import android.app.Activity;

import com.blueizz.bitmap.StrokeActivity;
import com.blueizz.collection.CollectionActivity;
import com.blueizz.reflection.ReflectionActivity;

import java.util.HashMap;
import java.util.Map;

public class ActivityRouter {

    private static final Map<String, Class<? extends Activity>> activityMap = new HashMap<>();

    static {
        activityMap.put("反射", ReflectionActivity.class);
        Class annotationClass = getClass("com.blueizz.annotation.AnnotationActivity");
        if (annotationClass != null) {
            activityMap.put("注解", annotationClass);
        }
        activityMap.put("Bitmap描边", StrokeActivity.class);
        activityMap.put("集合框架", CollectionActivity.class);
    }

    public static Map<String, Class<? extends Activity>> getActivityMap() {
        return activityMap;
    }

    private static Class getClass(String className) {
        Class cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cls;
    }
}
