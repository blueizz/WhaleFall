package com.blueizz.annotation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Method;

public class AnnotationActivity extends Activity {
    private static final String TAG = "AnnotationActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkMethod();
    }

    private void checkMethod() {
        NoBug noBug = new NoBug();
        Class cls = noBug.getClass();
        for (Method method : cls.getMethods()) {
            //判断是否标注了CheckAnnotation注解
            if (method.isAnnotationPresent(CheckAnnotation.class)) {
                //获取当前方法的注解对象
                CheckAnnotation checkAnnotation = method.getAnnotation(CheckAnnotation.class);
                //通过注解的isCheck属性判断是否测试当前方法
                if (checkAnnotation.isCheck()) {
                    try {
                        method.setAccessible(true);
                        method.invoke(noBug, new Object[]{});
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i(TAG, "Error：" + method.getName());
                    }
                }
            }
        }

    }
}
