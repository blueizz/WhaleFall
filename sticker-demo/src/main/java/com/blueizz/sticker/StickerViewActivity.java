package com.blueizz.sticker;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blueizz.sticker.utils.BitmapUtils;
import com.blueizz.sticker.utils.FileUtils;

import java.io.File;

/**
 * 作者：ZhouYou
 * 日期：2016/8/25.
 */
public class StickerViewActivity extends Activity implements View.OnClickListener {

    // 贴纸布局控件
    private StickerLayout stickerLayout;
    // 压缩图片的任务线程
    private CompressTask task;
    private Thread t;

    private ANRReceiver mAnrReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_view);

        stickerLayout = findViewById(R.id.sticker_layout);
        stickerLayout.setBackgroundImage(R.drawable.bg_scene);
        stickerLayout.setZoomRes(R.drawable.ic_resize);
        stickerLayout.setRemoveRes(R.drawable.ic_remove);
        stickerLayout.setRotateRes(R.drawable.ic_rotate);

        findViewById(R.id.tv_add_sticker).setOnClickListener(this);
        findViewById(R.id.tv_generate_preview).setOnClickListener(this);
        findViewById(R.id.tv_get_preview).setOnClickListener(this);

        /**
         *模拟anr
         */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.blueizz.ANRReceiver");
        mAnrReceiver = new ANRReceiver();
        registerReceiver(mAnrReceiver, intentFilter);

        findViewById(R.id.tv_test_anr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.blueizz.ANRReceiver");
                sendBroadcast(intent);
            }
        });
    }

    public class ANRReceiver extends BroadcastReceiver {
        private static final String TAG = "ANRReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive intent=" + intent);
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "onReceive end");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_get_preview) {
            stickerLayout.getPreview();
        } else if (v.getId() == R.id.tv_add_sticker) {
            Intent intent = new Intent(this, StickerSelectorListActivity.class);
            startActivityForResult(intent, 200);
        } else if (v.getId() == R.id.tv_generate_preview) {
            Bitmap dstBitmap = stickerLayout.generateCombinedBitmap();
            task = new CompressTask(dstBitmap);
            t = new Thread(task);
            t.start();
        }
    }

    /**
     * 关于图片压缩
     */
    private class CompressTask implements Runnable {

        private Bitmap dstBitmap;

        public CompressTask(Bitmap dstBitmap) {
            this.dstBitmap = dstBitmap;
        }

        @Override
        public void run() {
            if (dstBitmap == null) {
                handler.sendEmptyMessage(0);
                return;
            }
            File successFile = FileUtils.getCacheFile(StickerViewActivity.this);
            if (BitmapUtils.saveBitmap(dstBitmap, successFile) && successFile != null && successFile.exists()) {
                handler.obtainMessage(1, successFile.toString()).sendToTarget();
            } else {
                handler.sendEmptyMessage(0);
            }
            BitmapUtils.recycle(dstBitmap);
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String path = (String) msg.obj;
                    Intent intent = new Intent(StickerViewActivity.this, PreviewActivity.class);
                    intent.putExtra("path", path);
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "压缩图片失败", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK && requestCode == 200) {
            int resource = data.getIntExtra("res", 0);
            stickerLayout.addSticker(resource);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(task);
        if (t != null) t = null;
        if (task != null) task = null;

        unregisterReceiver(mAnrReceiver);
    }
}
