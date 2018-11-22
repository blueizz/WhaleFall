package com.blueizz.whalefall.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.blueizz.whalefall.SafeHandler;
import com.blueizz.whalefall.model.MainModel;
import com.blueizz.whalefall.view.IMainView;

import java.util.List;

public class MainPresenter implements Handler.Callback {
    private SafeHandler mHandler;
    private IMainView mView;
    private MainModel mModel;

    public MainPresenter(Context context, IMainView mView) {
        mHandler = new SafeHandler(context, this);
        this.mView = mView;
        mModel = new MainModel(mHandler);
    }

    public void getData() {
        mModel.getData();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MainModel.SUCCESS_GET_DATA:
                List<String> data = (List<String>) msg.obj;
                mView.updateData(data);
                break;
        }
        return false;
    }

    public void onDestroy() {
        mHandler.onDestroy();
    }
}
