package com.blueizz.bitmap;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class MarkEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        PointF start = (PointF) startValue;
        PointF end = (PointF) endValue;
        float x = start.x + fraction * (end.x - start.x);
        float y = start.y + fraction * (end.y - start.y);
        return new PointF(x, y);
    }
}
