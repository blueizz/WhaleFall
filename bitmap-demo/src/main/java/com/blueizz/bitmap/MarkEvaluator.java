package com.blueizz.bitmap;

import android.animation.TypeEvaluator;

import com.blueizz.bitmap.antrace.PointInfo;

public class MarkEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        PointInfo start = (PointInfo) startValue;
        PointInfo end = (PointInfo) endValue;
        float x = start.getX() + fraction * (end.getX() - start.getX());
        float y = start.getY() + fraction * (end.getY() - start.getY());
        return new PointInfo(x, y);
    }
}
