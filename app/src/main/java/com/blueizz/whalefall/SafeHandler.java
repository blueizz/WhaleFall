package com.blueizz.whalefall;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class SafeHandler extends Handler {
    private boolean isAlive;
    private WeakReference<Context> mActivity;

    public SafeHandler(Callback callback) {
        super(callback);
        isAlive = true;
    }

    public SafeHandler(Context context, Callback callback) {
        this(callback);
        this.mActivity = new WeakReference<>(context);
    }

    @Override
    public void dispatchMessage(Message msg) {
        if (this.isAlive) {
            if (this.mActivity != null) {
                if (mActivity.get() != null) {
                    super.dispatchMessage(msg);
                } else {
                    super.dispatchMessage(msg);
                }
            }
        }
    }

    public void onDestroy() {
        isAlive = false;
    }
}
