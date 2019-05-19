package com.blueizz.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.blueizz.view.custom.CircleView;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ViewActivity extends Activity {
    private LinearLayout mParent;
    private Button mAdd;
    private Button mGet;

    LruCache<String, CircleView> lru = new LruCache<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view);

        mParent = findViewById(R.id.ll_parent);
        mAdd = findViewById(R.id.add);
        mGet = findViewById(R.id.get);


        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircleView mView = new CircleView(ViewActivity.this);
                mView.setColor(Color.YELLOW);
                lru.put("first", mView);
                lru.put("sec1", mView);
                lru.put("sec2", mView);
                lru.put("sec3", mView);
            }
        });

        mGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircleView circleView = lru.get("first");
                if (circleView != null) {
                    mParent.addView(circleView);
                } else {
                    Toast.makeText(ViewActivity.this, "null", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /**
         * 创建Thread对象，调用start方法
         */
        MyThread myThread = new MyThread();
        myThread.start();


        MyCallable callable = new MyCallable();
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask, "任务");
        thread.start();

        try {
            //取消正在执行的或者正在排队的请求
            futureTask.cancel(true);
            //判断是否执行完
            futureTask.isDone();
            //获取线程执行的结果
            futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 继承Thread类，重写run方法
     */
    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
        }
    }

    /**
     * 实现Callable接口，重谢call方法
     */
    class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "执行的接口";
        }
    }
}

