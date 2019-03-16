package com.blueizz.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class FrontService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();


        Intent notificationIntent = new Intent(this, ServiceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification;

        //适配Android8.0通知栏增加了channel渠道式消息分发机制
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelId = "service"; // 渠道ID
            String channelName = "前台服务";//渠道名称
            String channelDescription = "这是一个前台服务"; // 渠道说明

            NotificationChannel channel = null;
            if (channel == null) {
                channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription(channelDescription);
                channel.enableLights(true);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.createNotificationChannel(channel);
            }
            //创建Notification需要将channelId传递进去
            notification = new Notification.Builder(this, channelId).
                    setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("新通知")
                    .setContentText("通知内容")
                    .setContentIntent(pendingIntent)
                    .build();
        } else {
            notification = new Notification.Builder(this).
                    setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("新通知")
                    .setContentText("通知内容")
                    .setContentIntent(pendingIntent)
                    .build();
        }

        startForeground(110, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
