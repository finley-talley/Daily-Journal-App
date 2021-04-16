package com.example.dailyjournalapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

public class NotificationService extends IntentService {
    private static final int NOTIFICATION_ID = 0;

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Daily Journal");
        builder.setContentText("Click to start today's journal entry!");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("fromNotif", "");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManagerCompat nm = NotificationManagerCompat.from(this);
        nm.notify(NOTIFICATION_ID, builder.build());
    }
}