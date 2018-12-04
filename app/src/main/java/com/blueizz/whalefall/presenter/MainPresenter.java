package com.blueizz.whalefall.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.blueizz.commom.mvp.SafeHandler;
import com.blueizz.commom.mvp.bean.Result;
import com.blueizz.commom.mvp.presenter.BasePresenter;
import com.blueizz.whalefall.model.MainModel;
import com.blueizz.whalefall.view.IMainView;

import java.util.List;

public class MainPresenter extends BasePresenter {
    private IMainView mView;
    private MainModel mModel;

    public MainPresenter(Context context, IMainView mView) {
        super(context);
        this.mView = mView;
        mModel = new MainModel(context, mHandler);
    }

    public void getData() {
        mModel.getData();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MainModel.SUCCESS_GET_DATA:
                List<String> data = (List<String>) ((Result) msg.obj).getObj();
                mView.updateData(data);
                break;
        }
        return false;
    }

}
