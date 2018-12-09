package com.blueizz.bitmap.antrace;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blueizz.bitmap.R;
import com.blueizz.bitmap.antrace.bean.Path;
import com.blueizz.bitmap.antrace.bean.PointInfo;

import java.util.ArrayList;
import java.util.List;

public class AntracerActivity extends Activity {

    private ImageView mPointImage;
    private Button mTracer;

    private Bitmap mPointMap;
    private int radius = 2;

    private Bitmap mMomoMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_antracer);

        initView();
    }

    private void initView() {
        mPointImage = findViewById(R.id.iv_map);
        mTracer = findViewById(R.id.btn_tracer);

        mPointMap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        mMomoMap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);

        drawPoints(getData());

        mTracer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new ThresholdThread()).start();
            }
        });
    }

    public void drawPoints(List<PointInfo> data) {
        Canvas canvas = new Canvas(mPointMap);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#33CCFF"));
        paint.setStrokeWidth(2 * radius);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        for (PointInfo point : data) {
            float cx = point.getX() * (2 * radius);
            float cy = point.getY() * (2 * radius);
            canvas.drawPoint(cx, cy, paint);
        }
        mPointImage.setImageBitmap(mPointMap);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        int w = (int) (drawable.getIntrinsicWidth() * 0.3);
        int h = (int) (drawable.getIntrinsicHeight() * 0.3);
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);

        return bitmap;
    }

    private List<PointInfo> getData() {
        List<PointInfo> data = new ArrayList<>();
        data.add(new PointInfo(20, 20));
        data.add(new PointInfo(20, 21));

        data.add(new PointInfo(21, 20));
        data.add(new PointInfo(21, 21));
        data.add(new PointInfo(21, 22));
        data.add(new PointInfo(21, 23));

        data.add(new PointInfo(22, 20));
        data.add(new PointInfo(22, 23));
        data.add(new PointInfo(22, 24));
        data.add(new PointInfo(22, 25));

        data.add(new PointInfo(23, 20));
        data.add(new PointInfo(23, 25));
        data.add(new PointInfo(23, 26));
        data.add(new PointInfo(23, 27));

        data.add(new PointInfo(24, 20));
        data.add(new PointInfo(24, 21));
        data.add(new PointInfo(24, 22));
        data.add(new PointInfo(24, 23));

        return data;
    }


    private Handler thresholdHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            new Thread(new TraceThread()).start();
        }
    };

    class ThresholdThread implements Runnable {

        @Override
        public void run() {
            Utils.threshold(mPointMap, 127, mMomoMap);
            Message msg = thresholdHandler.obtainMessage(127, mMomoMap);
            thresholdHandler.sendMessage(msg);
        }
    }

    private Handler traceHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Path path = (Path) msg.obj;
        }

    };

    class TraceThread implements Runnable {

        @Override
        public void run() {
            Path path = Utils.traceImage(mMomoMap);
            Message msg = traceHandler.obtainMessage(0, path);
            traceHandler.sendMessage(msg);
        }
    }

    static {
        System.loadLibrary("antrace");
    }

}
