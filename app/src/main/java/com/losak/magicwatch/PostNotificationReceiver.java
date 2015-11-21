package com.losak.magicwatch;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

public class PostNotificationReceiver extends BroadcastReceiver {
    public static final String CONTENT_KEY = "contentText";
    public static final String RECOGNIZED_TEXT = "recognizedText";

    public PostNotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent displayIntent = new Intent(context, NotificationActivity.class);
        String title = intent.getStringExtra(CONTENT_KEY);
        NotificationActivity.RECOGNIZED_TEXT = intent.getStringExtra(RECOGNIZED_TEXT);

        Notification.WearableExtender wearableExtender = new Notification.WearableExtender()
//                .setHintAvoidBackgroundClipping()
                .setBackground(BitmapFactory.decodeResource(context.getResources(), R.drawable.gradient_notify))
        .setDisplayIntent(PendingIntent.getActivity(context, 0, displayIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .extend(wearableExtender)
                .build();

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, notification);
    }
}