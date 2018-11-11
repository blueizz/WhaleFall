package com.blueizz.reflection;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by blueizz on 2018/11/11.
 */

public class ReflectionActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Class cls;
        try {
            cls = Class.forName("com.blueizz.reflection.Phone");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cls = Phone.class;

        Phone phone = new Phone();
        cls = phone.getClass();
    }
}
