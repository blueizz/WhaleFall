package com.blueizz.annotation;

import android.util.Log;

public class NoBug {
    private static final String TAG = "NoBug";

    @CheckAnnotation
    public void addition() {
        Log.i(TAG, "1+1 = " + (1 + 1));
    }

    @CheckAnnotation
    public void substruction() {
        Log.i(TAG, "1-1 = " + (1 - 1));
    }

    @CheckAnnotation(isCheck = true)
    public void multiplication() {
        Log.i(TAG, "1×1 = " + (1 * 1));
    }

    @CheckAnnotation(isCheck = true)
    public void division() {
        Log.i(TAG, "8÷0 = " + (8 / 0));
    }
}
