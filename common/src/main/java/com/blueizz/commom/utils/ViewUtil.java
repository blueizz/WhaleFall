package com.blueizz.commom.utils;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class ViewUtil {

    /**
     * View防止重复点击
     *
     * @param mView
     * @param repeatedTime
     * @param listener
     */
    public static void preventRepeatedClick(@NonNull final View mView, int repeatedTime, @NonNull final View.OnClickListener listener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emitter.onNext("click");
                    }
                });
            }
        }).throttleFirst(repeatedTime, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        listener.onClick(mView);
                    }
                });
    }

    /**
     * View防止重复点击
     *
     * @param mView
     * @param listener
     */
    public static void preventRepeatedClick(@NonNull final View mView, @NonNull final View.OnClickListener listener) {
        preventRepeatedClick(mView, 1000, listener);
    }
}
