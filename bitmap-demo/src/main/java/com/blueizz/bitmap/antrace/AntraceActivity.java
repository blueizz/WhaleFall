package com.blueizz.bitmap.antrace;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.blueizz.bitmap.R;
import com.blueizz.bitmap.antrace.bean.PointInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AntraceActivity extends Activity {
    private static int REQUEST_PERMISSION = 133;

    private ImageView mPointImage;
    private Button mTracer;

    private Bitmap mPointMap;
    private int radius = 4;

    private Bitmap mThresholdMap;

    private Canvas mCanvas;
    private Paint mPaint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_antrace);

        initView();
    }

    private void initView() {
        mPointImage = findViewById(R.id.iv_map);
        mTracer = findViewById(R.id.btn_tracer);

        mPointMap = Bitmap.createBitmap(1080, 1080, Bitmap.Config.ARGB_8888);
        mThresholdMap = Bitmap.createBitmap(1080, 1080, Bitmap.Config.ARGB_8888);

        drawPoints(getData());

        mTracer.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                Observable.just(1)
                        .observeOn(Schedulers.newThread())
                        .doOnNext(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) {
                                Utils.threshold(mPointMap, 127, mThresholdMap);
                            }
                        })
                        .doOnNext(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) {
                                Utils.traceImage(mThresholdMap);
                                String svgFile = tempSvgFile();
                                Utils.saveSVG(svgFile, mThresholdMap.getWidth(), mThresholdMap.getHeight());
                            }
                        }).subscribe();

                /**
                 * RxJava将第一个操作的返回值传递给下一个操作
                 */
                //                Observable.fromCallable(new Callable<String>() {
                //                    @Override
                //                    public String call() {
                //                        return "hello world";
                //                    }
                //                }).subscribe(new Consumer<String>() {
                //                    @Override
                //                    public void accept(String s) {
                //                        Toast.makeText(AntracerActivity.this, s, Toast.LENGTH_SHORT).show();
                //                    }
                //                });
            }
        });

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(
                AntraceActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(AntraceActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
            return;
        }
    }

    public void drawPoints(List<PointInfo> data) {
        mCanvas = new Canvas(mPointMap);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#33CCFF"));
        mPaint.setStrokeWidth(2 * radius);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        for (PointInfo point : data) {
            float cx = point.getX() * (2 * radius);
            float cy = point.getY() * (2 * radius);
            mCanvas.drawPoint(cx, cy, mPaint);
        }

        //        SvgPathParser parser = new SvgPathParser();
        //        try {
        //            android.graphics.Path path = parser.parsePath(getString(R.string.map));
        //            paint.setColor(Color.RED);
        //            canvas.drawPath(path, paint);
        //        } catch (Exception e) {
        //        }

        //        saveBitmap(mPointMap);
        mPointImage.setImageBitmap(mPointMap);

    }

    private List<PointInfo> getData() {
        String jsonData = getString(R.string.point_data);
        List<PointInfo> data = JSON.parseArray(jsonData, PointInfo.class);
        return data;
    }

    static {
        System.loadLibrary("antrace");
    }

    /**
     * SVG缓存路径
     * /Android/data/com.blueizz.whalefall/cache/temp_svg.svg
     *
     * @return
     */
    public String tempSvgFile() {
        return this.getExternalCacheDir().toString() + "/temp_svg.svg";
    }

    /**
     * 保存bitmap
     *
     * @param bitmap
     */
    public void saveBitmap(Bitmap bitmap) {
        File file = new File(this.getExternalCacheDir().toString() + "/temp_bitmap.jpg");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
