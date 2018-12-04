package com.blueizz.commom.mvp.model;

import android.content.Context;
import android.os.Message;

import com.blueizz.commom.mvp.SafeHandler;
import com.blueizz.commom.mvp.bean.Result;

public abstract class BaseModel implements IModel {

    protected Context mContext;

    protected SafeHandler mHandler;

    public BaseModel(Context ctx) {
        this(ctx, null);
    }

    public BaseModel(Context ctx, SafeHandler handler) {
        mHandler = handler;
        mContext = ctx;
    }

    /**
     * 发送成功结果
     *
     * @param what
     * @param obj
     */
    protected void resultSuccess(int what, Object obj) {
        if (mHandler != null) {
            Message message = mHandler.obtainMessage(what);
            message.obj = new Result(obj);
            mHandler.sendMessage(message);
        }
    }

    /**
     * 发送错误信息
     *
     * @param what
     * @param errorCode
     * @param error
     */
    protected void resultError(int what, String errorCode, String error) {
        if (mHandler != null) {
            Message message = mHandler.obtainMessage(what);
            message.obj = new Result(errorCode, error);
            mHandler.sendMessage(message);
        }
    }

}
