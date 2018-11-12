package com.blueizz.whalefall;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blueizz.reflection.ReflectionActivity;

public class MainActivity extends Activity {
    private Button mReflectionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setListener();
    }

    private void findViews() {
        mReflectionBtn = findViewById(R.id.btn_reflection);
    }

    private void setListener() {
        mReflectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReflectionActivity.gotoReflectionDemo(MainActivity.this);
            }
        });
    }
}
