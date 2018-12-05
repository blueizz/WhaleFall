package com.blueizz.bitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * 为Bitmap添加描边
 */
public class StrokeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stroke);

        ImageView imageView = findViewById(R.id.iv_whale);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        setDrawableStroke(imageView, paint);
    }

    /**
     * 绘制描边
     *
     * @param iv
     * @param paint
     */
    private void setDrawableStroke(ImageView iv, Paint paint) {
        BitmapDrawable bd = (BitmapDrawable) iv.getDrawable();
        Bitmap b = bd.getBitmap();
        Bitmap strokeBitmap = Bitmap.createBitmap(bd.getIntrinsicWidth(),
                bd.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(strokeBitmap);
        canvas.drawBitmap(b.extractAlpha(), -3, 0, paint);
        canvas.drawBitmap(b.extractAlpha(), 3, 0, paint);
        canvas.drawBitmap(b.extractAlpha(), 0, -3, paint);
        canvas.drawBitmap(b.extractAlpha(), 0, 3, paint);

        /**
         * 按住时显示描边，需要将ImageView的clickable设置为true
         */
        //        StateListDrawable sld = new StateListDrawable();
        //        sld.addState(new int[]{android.R.attr.state_pressed}, new BitmapDrawable(getResources(), strokeBitmap));
        iv.setBackground(new BitmapDrawable(getResources(), strokeBitmap));
    }
}
