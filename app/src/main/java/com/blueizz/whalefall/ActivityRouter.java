package com.blueizz.whalefall;

import android.app.Activity;

import com.blueizz.bitmap.StrokeActivity;
import com.blueizz.collection.CollectionActivity;
import com.blueizz.bitmap.antrace.AntraceActivity;
import com.blueizz.reflection.ReflectionActivity;

import org.joor.Reflect;
import org.joor.ReflectException;

import java.util.LinkedHashMap;
import java.util.Map;

public class ActivityRouter {

    private static final Map<String, Class<? extends Activity>> activityMap = new LinkedHashMap<>();

    static {
        activityMap.put("反射", ReflectionActivity.class);

        Class annotationClass = null;
        try {
            annotationClass = Reflect.on("com.blueizz.annotation.AnnotationActivity").get();
        } catch (ReflectException e) {
            e.printStackTrace();
        }
        if (annotationClass != null) {
            activityMap.put("注解", annotationClass);
        }
        activityMap.put("Bitmap描边", StrokeActivity.class);
        activityMap.put("位图转矢量图", AntraceActivity.class);
        activityMap.put("集合框架", CollectionActivity.class);
    }

    public static Map<String, Class<? extends Activity>> getActivityMap() {
        return activityMap;
    }
}
