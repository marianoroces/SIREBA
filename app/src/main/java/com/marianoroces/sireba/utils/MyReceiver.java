package com.marianoroces.sireba.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.marianoroces.sireba.R;
import com.marianoroces.sireba.activities.CheckReportsActivity;

public class MyReceiver extends BroadcastReceiver {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    public static final String REPORT_CREATED_EVENT = "com.marianoroces.sireba.REPORT_CREATED_EVENT";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equalsIgnoreCase(REPORT_CREATED_EVENT)){
            sendNotification(context, intent);
        }
    }

    private void sendNotification(Context context, Intent intent) {
        Intent destinyIntent = new Intent(context, CheckReportsActivity.class);
        destinyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent destinyPending = PendingIntent.getActivity(context, 0, destinyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Reporte creado")
                .setContentText("Click para ver reportes actuales")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(destinyPending)
                .setAutoCancel(true);
        Notification notification = builder.build();

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.notify(99, notification);
    }
}
