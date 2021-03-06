package com.blueizz.sticker.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class PointUtils {

    /**
     * 将matrix的点映射成坐标点
     *
     * @return
     */
    public static float[] getBitmapPoints(Bitmap bitmap, Matrix matrix) {
        float[] dst = new float[8];
        float[] src = new float[]{
                0, 0,
                bitmap.getWidth(), 0,
                0, bitmap.getHeight(),
                bitmap.getWidth(), bitmap.getHeight()
        };
        matrix.mapPoints(dst, src);
        return dst;
    }
}
