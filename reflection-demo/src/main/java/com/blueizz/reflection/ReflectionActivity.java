package com.blueizz.reflection;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by blueizz on 2018/11/11.
 */

public class ReflectionActivity extends Activity {
    private final static String TAG = "ReflectionActivity";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Class cls = Phone.class;
            Constructor constructor = cls.getConstructor(String.class,
                    float.class, boolean.class);
            Phone phone = (Phone) constructor.newInstance("android", 5.1f, false);

            Field publicField = cls.getField("isFullscreen");
            boolean isFullscreen = publicField.getBoolean(phone);
            Log.i(TAG, "isFullscreen:" + isFullscreen);

            Field declaredField = cls.getDeclaredField("model");
            //获取私有属性的访问权限，抑制Java对其的检查。
            declaredField.setAccessible(true);
            String model = (String) declaredField.get(phone);
            Log.i(TAG, "model:" + model);

            Field[] fields = cls.getFields();
            for (Field field : fields) {
                Log.i(TAG, field.getName() + ":" + field.get(phone));
            }

            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                Log.i(TAG, field.getName() + ":" + field.get(phone));
            }

            Method publicMethod = cls.getMethod("call", String.class);
            publicMethod.invoke(phone, "15800001111");

            Method declaredMethod = cls.getDeclaredMethod("fingerUnlock");
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(phone);

            Method[] methods = cls.getMethods();
            for (Method method : methods) {
                Log.i(TAG, "Method Name：" + method.getName());
            }

            Method[] declaredMethods = cls.getMethods();
            for (Method method : declaredMethods) {
                method.setAccessible(true);
                Log.i(TAG, "Method Name：" + method.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void gotoReflectionDemo(Activity activity) {
        Intent intent = new Intent(activity, ReflectionActivity.class);
        activity.startActivity(intent);
    }
}
