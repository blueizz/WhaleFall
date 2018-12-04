package com.blueizz.commom.mvp.presenter;

import android.content.Context;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;

import com.blueizz.commom.mvp.SafeHandler;

public abstract class BasePresenter implements Callback {

    protected SafeHandler mHandler;

    public BasePresenter() {
        this.mHandler = new SafeHandler(Looper.getMainLooper(), this);
    }

    public BasePresenter(Context context) {
        this.mHandler = new SafeHandler(context, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    public void onDestroy() {
        mHandler.destroy();
    }

}
