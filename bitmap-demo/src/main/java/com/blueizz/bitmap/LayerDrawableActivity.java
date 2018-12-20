package com.blueizz.bitmap;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blueizz.bitmap.antrace.PointInfo;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class LayerDrawableActivity extends Activity {
    private PhotoView mLayerImage;

    private LayerDrawable mLayer;
    private Drawable pathDrawable;
    private Drawable markDrawable;
    private Bitmap mapBitmap;
    private Bitmap pathBitmap;
    private Bitmap markBitmap;

    private Canvas mCanvas;
    private Paint mPaint;
    private Path mPath;

    private Random mRandom;
    private Bitmap mMarkIcon;
    private ValueAnimator mAnimator;
    private float markX;//标记当前的x坐标
    private float markY;//标记当前的y坐标
    private int markWidth;
    private int markHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layer_drawable);
        mRandom = new Random();

        initView();
    }

    private void initView() {
        mLayerImage = findViewById(R.id.iv_layer);
        initDrawTool();
        initLayer();
    }

    /**
     * 初始化绘图工具
     */
    private void initDrawTool() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.living_coral));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10f);

        mPath = new Path();
        mPath.moveTo(markX, markY);
    }

    /**
     * 初始化图层
     */
    private void initLayer() {
        mapBitmap = Bitmap.createBitmap(1920, 1080, Bitmap.Config.ARGB_8888);
        pathBitmap = Bitmap.createBitmap(1920, 1080, Bitmap.Config.ARGB_8888);
        markBitmap = Bitmap.createBitmap(1920, 1080, Bitmap.Config.ARGB_8888);
        Drawable map = new BitmapDrawable(getResources(), mapBitmap);
        pathDrawable = new BitmapDrawable(getResources(), pathBitmap);
        markDrawable = new BitmapDrawable(getResources(), markBitmap);
        Drawable[] drawables = {map, pathDrawable, markDrawable};
        mLayer = new LayerDrawable(drawables);
        mLayerImage.setImageDrawable(mLayer);

        initMarkIcon();

        drawMap();
    }

    /**
     * 初始化标记图标
     */
    private void initMarkIcon() {
        mMarkIcon = getBitmapByDrawable(R.drawable.mark);
        markWidth = mMarkIcon.getWidth();
        markHeight = mMarkIcon.getHeight();
    }

    /**
     * 绘制地图
     */
    private void drawMap() {
        mCanvas = new Canvas(mapBitmap);
        mCanvas.drawBitmap(getBitmapByDrawable(R.drawable.west_lake), 0, 0, new Paint());
        //        mLayerImage.invalidateDrawable(mLayer);
        Observable.interval(0, 3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        int endX = mRandom.nextInt(1920 - markWidth);
                        int endY = mRandom.nextInt(1080 - markHeight);
                        mAnimator = ValueAnimator.ofObject(new MarkEvaluator(),
                                new PointInfo(markX, markY),
                                new PointInfo(endX, endY));
                        mAnimator.setDuration(2000);
                        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                PointInfo info = (PointInfo) animation.getAnimatedValue();
                                drawPath(info.getX(), info.getY());
                                drawMark(info.getX(), info.getY());
                                markX = info.getX();
                                markY = info.getY();
                            }
                        });
                        mAnimator.start();
                    }
                });
    }

    /**
     * 绘制路径
     *
     * @param x
     * @param y
     */
    private void drawPath(float x, float y) {
        mCanvas = new Canvas(pathBitmap);
        mPath.lineTo(x, y);
        mCanvas.drawPath(mPath, mPaint);
    }

    /**
     * 绘制标记
     *
     * @param x
     * @param y
     */
    private void drawMark(float x, float y) {
        mCanvas = new Canvas(markBitmap);
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mCanvas.drawBitmap(mMarkIcon, x, y, mPaint);
        markDrawable.invalidateSelf();
    }

    /**
     * 从Drawable文件夹下获取Bitmap
     *
     * @param resId
     * @return
     */
    private Bitmap getBitmapByDrawable(int resId) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resId);
        return bmp;
    }

    @Override
    protected void onDestroy() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
        super.onDestroy();
    }
}
