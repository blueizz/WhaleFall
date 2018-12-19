package com.blueizz.bitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blueizz.commom.utils.DensityUtil;
import com.github.chrisbanes.photoview.PhotoView;

public class LayerDrawableActivity extends Activity {
    private PhotoView mLayerImage;

    private LayerDrawable mLayer;
    private Drawable pathDrawable;
    private Canvas mCanvas;
    private Bitmap mapBitmap;
    private Bitmap pathBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layer_drawable);
        initView();
    }

    private void initView() {
        mLayerImage = findViewById(R.id.iv_layer);
        initBitmap();
    }

    private void initBitmap() {
        mapBitmap = Bitmap.createBitmap(1920, 1080, Bitmap.Config.ARGB_8888);
        pathBitmap = Bitmap.createBitmap(1920, 1080, Bitmap.Config.ARGB_8888);
        Drawable map = new BitmapDrawable(getResources(), mapBitmap);
        pathDrawable = new BitmapDrawable(getResources(), pathBitmap);
        Drawable[] drawables = {map, pathDrawable};
        mLayer = new LayerDrawable(drawables);
        mLayerImage.setImageDrawable(mLayer);

        drawMap();
    }

    private void drawMap() {
        mCanvas = new Canvas(mapBitmap);
        mCanvas.drawBitmap(getBitmapByDrawable(R.drawable.west_lake), 0, 0, new Paint());
        //        mLayerImage.invalidateDrawable(mLayer);
        drawMark();
    }

    private void drawMark() {
        mCanvas = new Canvas(pathBitmap);
        mCanvas.drawBitmap(getBitmapByDrawable(R.drawable.mark), 0, 0, new Paint());
        pathDrawable.invalidateSelf();
    }

    private Bitmap getBitmapByDrawable(int resId) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resId);
        return bmp;
    }


}
