package com.blueizz.whalefall;

import android.app.Activity;

import com.blueizz.animation.AnimationActivity;
import com.blueizz.bitmap.LayerDrawableActivity;
import com.blueizz.bitmap.StrokeActivity;
import com.blueizz.collection.CollectionActivity;
import com.blueizz.bitmap.BitmapActivity;
import com.blueizz.reflection.ReflectionActivity;
import com.blueizz.service.ServiceActivity;
import com.blueizz.share_demo.ShareActivity;
import com.blueizz.sticker.StickerViewActivity;
import com.blueizz.thread.ThreadActivity;
import com.blueizz.view.ViewActivity;

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
        activityMap.put("单图层绘制", BitmapActivity.class);
        activityMap.put("多图层绘制", LayerDrawableActivity.class);
        activityMap.put("集合框架", CollectionActivity.class);
        activityMap.put("Android贴纸", StickerViewActivity.class);
        activityMap.put("Service基本用法", ServiceActivity.class);
        activityMap.put("View的原理", ViewActivity.class);
        activityMap.put("动画", AnimationActivity.class);
        activityMap.put("线程", ThreadActivity.class);
        activityMap.put("分享", ShareActivity.class);
    }

    public static Map<String, Class<? extends Activity>> getActivityMap() {
        return activityMap;
    }
}
