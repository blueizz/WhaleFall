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
import java.lang.reflect.Modifier;

/**
 * Created by blueizz on 2018/11/11.
 */

public class ReflectionActivity extends Activity {
    private final static String TAG = "ReflectionActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            //使用反射第一步:获取操作类Phone所对应的Class对象
            Class cls = Phone.class;
            Constructor constructor = cls.getConstructor(String.class,
                    float.class);
            //使用Phone类的class对象生成实例
            Phone phone = (Phone) constructor.newInstance("android", 5.5f);

            Field publicField = cls.getField("screenSize");
            float size = publicField.getFloat(phone);
            Log.i(TAG, "屏幕尺寸:" + size);

            Field declaredField = cls.getDeclaredField("platform");
            //获取私有属性的访问权限
            declaredField.setAccessible(true);
            String model = (String) declaredField.get(phone);
            Log.i(TAG, "平台:" + model);

            Field[] fields = cls.getFields();
            for (Field field : fields) {
                Log.i(TAG, field.getName() + ":" + field.get(phone));
            }

            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                Log.i(TAG, field.getName() + ":" + field.get(phone));
            }

            /**
             * Method对象常用方法
             */
            //获取public String call(String phoneNum)方法的Method对象
            Method callMethod = cls.getMethod("call", String.class);
            Log.i(TAG, "修饰符：" + Modifier.toString(callMethod.getModifiers()));
            Log.i(TAG, "返回值类型：" + callMethod.getReturnType());
            Log.i(TAG, "方法名称：" + callMethod.getName());
            Log.i(TAG, "参数类型列表(数组)：" + callMethod.getParameterTypes());
            //使用Method.invoke()调用方法
            String result = (String) callMethod.invoke(phone, "110");
            Log.i(TAG, "调用call后的运行结果：" + result);

            Method declaredMethod = cls.getDeclaredMethod("unlock");
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(phone);

            Method[] methods = cls.getMethods();
            for (Method method : methods) {
                Log.i(TAG, "public方法：" + method.getName());
            }

            Method[] declaredMethods = cls.getDeclaredMethods();
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
