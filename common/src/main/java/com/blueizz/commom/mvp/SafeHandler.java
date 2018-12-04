package com.blueizz.commom.mvp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
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

    public SafeHandler(Looper looper, Callback callback) {
        super(looper, callback);
        this.isAlive = true;
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
            this.clearMsg(msg);
        }
    }

    public void clearMsg(Message msg) {
        msg.what = 0;
        msg.arg1 = 0;
        msg.arg2 = 0;
        msg.obj = null;
        msg.replyTo = null;
        msg.setTarget(null);
    }

    public void destroy() {
        isAlive = false;
    }
}
