package com.example.dailyjournalapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("MSG", "Receiving [AlarmReceiver]");
        Intent intent1 = new Intent(context, NotificationService.class);
        context.startService(intent1);
    }

}