package com.blueizz.whalefall.presenter;

import android.os.Handler;
import android.os.Message;

import com.blueizz.whalefall.model.MainModel;
import com.blueizz.whalefall.view.IMainView;

import java.util.List;

public class MainPresenter {
    private IMainView mView;
    private MainModel mModel;

    public MainPresenter(IMainView mView) {
        this.mView = mView;
        mModel = new MainModel(mHandler);
    }

    public void getData() {
        mModel.getData();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MainModel.SUCCESS_GET_DATA:
                    List<String> data = (List<String>) msg.obj;
                    mView.updateData(data);
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
