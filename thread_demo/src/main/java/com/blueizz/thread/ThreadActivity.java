package com.blueizz.thread;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadActivity extends Activity {
    private Button mBtnAsyncTask;
    private Button mBtnHandlerThread;
    private Button mBtnIntentService;
    private Button mBtnThreadPoolExecutor;

    private HandlerThread mThread;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thread);


        init();
        findView();
        setListener();
    }

    private void init() {
        mThread = new HandlerThread("HandlerThread");
        mThread.start();
        mHandler = new Handler(mThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(ThreadActivity.this, "线程：" + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
            }
        };
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void findView() {
        mBtnAsyncTask = findViewById(R.id.btn_AsyncTask);
        mBtnHandlerThread = findViewById(R.id.btn_HandlerTread);
        mBtnIntentService = findViewById(R.id.btn_IntentService);
        mBtnThreadPoolExecutor = findViewById(R.id.btn_ThreadPoolExecutor);
    }

    private void setListener() {
        mBtnAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TestAsyncTask().execute("");
            }
        });


        mBtnHandlerThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.sendEmptyMessage(0);
            }
        });

        mBtnIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThreadActivity.this, TestIntentService.class);
                intent.putExtra("task_action", "com.blueizz.thread.TestIntentService");
                startService(intent);
            }
        });

        mBtnThreadPoolExecutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable command = new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(3000);
                    }
                };

                /**
                 *只有核心线程，且线程数量固定，不会被回收
                 */
                ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
                fixedThreadPool.execute(command);

                /**
                 * 只有非核心线程，且线程数量无限多
                 * 超时时长为60秒，适合执行大量的耗时较少的任务
                 */
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
                cachedThreadPool.execute(command);

                /**
                 * 包含核心线程和非核心线程，核心线程数量固定，非核心线程数量无限多
                 * 用于执行定时任务和具有固定周期的重复任务
                 */
                ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
                //2000ms后执行command
                scheduledThreadPool.schedule(command, 2000, TimeUnit.MILLISECONDS);
                //延迟10ms后，每隔1000ms执行一次command
                scheduledThreadPool.scheduleAtFixedRate(command, 10, 1000, TimeUnit.MILLISECONDS);

                /**
                 * 只有一个核心线程
                 * 统一所有的外界任务到一个线程中，这使得再这些任务之间不需要处理线程同步的问题
                 */
                ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
                singleThreadPool.execute(command);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mThread.quit();
    }
}
