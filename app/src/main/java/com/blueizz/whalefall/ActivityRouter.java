package com.blueizz.whalefall;

import android.app.Activity;

import com.blueizz.bitmap.LayerDrawableActivity;
import com.blueizz.bitmap.StrokeActivity;
import com.blueizz.collection.CollectionActivity;
import com.blueizz.bitmap.BitmapActivity;
import com.blueizz.reflection.ReflectionActivity;
import com.blueizz.sticker.StickerViewActivity;

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
        activityMap.put("位图转矢量图", BitmapActivity.class);
        activityMap.put("多图层绘制", LayerDrawableActivity.class);
        activityMap.put("集合框架", CollectionActivity.class);
        activityMap.put("Android贴纸", StickerViewActivity.class);
    }

    public static Map<String, Class<? extends Activity>> getActivityMap() {
        return activityMap;
    }
}
