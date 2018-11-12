package com.blueizz.reflection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by blueizz on 2018/11/11.
 */

public class ReflectionActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Class cls = Phone.class;
            Phone phone = (Phone) cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class cls = Phone.class;
            Constructor constructor = cls.getConstructor(String.class, float.class, boolean.class);
            Phone phone = (Phone) constructor.newInstance("android", 5.1f, false);
            Field field = cls.getDeclaredField("model");
            //抑制Java对其的检查
            field.setAccessible(true);
            String model = (String) field.get(phone);
            Toast.makeText(this, model, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void gotoReflectionDemo(Activity activity) {
        Intent intent = new Intent(activity, ReflectionActivity.class);
        activity.startActivity(intent);
    }
}
