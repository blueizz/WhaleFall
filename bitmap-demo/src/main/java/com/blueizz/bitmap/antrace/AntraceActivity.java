package com.blueizz.bitmap.antrace;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blueizz.bitmap.PGM;
import com.blueizz.bitmap.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AntraceActivity extends Activity {
    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101;

    private PhotoView mPointImage;
    private Button mTracer;

    private Bitmap mPointMap;
    private int radius = 2;

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
                //                        Toast.makeText(AntraceActivity.this, s, Toast.LENGTH_SHORT).show();
                //                    }
                //                });
            }
        });
    }

    public void drawPoints(List<PointF> data) {
        mCanvas = new Canvas(mPointMap);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.living_coral));
        mPaint.setStrokeWidth(2 * radius);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        for (PointF point : data) {
            float cx = point.x * (2 * radius);
            float cy = point.y * (2 * radius);
            mCanvas.drawPoint(cx, cy, mPaint);
        }

        //        SvgPathParser parser = new SvgPathParser();
        //        try {
        //            android.graphics.Path path = parser.parsePath(getString(R.string.map));
        //            paint.setColor(Color.RED);
        //            canvas.drawPath(path, paint);
        //        } catch (Exception e) {
        //        }

        saveBitmap(mPointMap);

        String path = "amicro.pgm";
        int iw, ih;
        int[] pix;
        PGM pgm = new PGM(this);
        pgm.readPGMHeader(path);
        iw = pgm.getWidth();
        ih = pgm.getHeight();
        pix = pgm.readData(iw, ih, 5);
        Bitmap bitmap = Bitmap.createBitmap(iw, ih, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pix, 0, iw, 0, 0, iw, ih);
        mPointImage.setImageBitmap(bitmap);

    }

    private List<PointF> getData() {
        String jsonData = getString(R.string.point_data);
        List<PointF> data = JSON.parseArray(jsonData, PointF.class);
        return data;
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
        boolean hasPermission = checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE, String.format(
                        getString(R.string.storage_failure), "鲸落"));
        if (!hasPermission) {
            return;
        }
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

    /**
     * 动态申请权限
     *
     * @param permission
     * @param requestCode
     * @param tip
     * @return
     */
    public Boolean checkPermission(String permission, int requestCode, String tip) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(AntraceActivity.this,
                permission) != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true
            if (ActivityCompat.shouldShowRequestPermissionRationale(AntraceActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(AntraceActivity.this, tip, Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(AntraceActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveBitmap(mPointMap);
                } else {
                    Toast.makeText(AntraceActivity.this, String.format(
                            getString(R.string.storage_failure), "鲸落"), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
