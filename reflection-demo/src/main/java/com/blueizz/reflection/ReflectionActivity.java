package com.blueizz.reflection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created by blueizz on 2018/11/11.
 */

public class ReflectionActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Class cls;
        //        try {
        //            cls = Class.forName("com.blueizz.reflection.Phone");
        //        } catch (ClassNotFoundException e) {
        //            e.printStackTrace();
        //        }
        //
        //        cls = Phone.class;

        Phone phone = new Phone();
        cls = phone.getClass();

        try {
            Field field = cls.getDeclaredField("model");
            //抑制Java对其的检查
            field.setAccessible(true);
            String model = (String) field.get(phone);
            Toast.makeText(this, model, Toast.LENGTH_SHORT).show();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void gotoReflectionDemo(Activity activity) {
        Intent intent = new Intent(activity, ReflectionActivity.class);
        activity.startActivity(intent);
    }
}
