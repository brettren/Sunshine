package com.brettren.android.sunshine.app;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

public class WeatherAlarmReceiver extends BroadcastReceiver {

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        int tmp = intent.getIntExtra(WeatherService.KEY_TEMP, -1);
        String city = intent.getStringExtra(WeatherService.KEY_CITY);
        Intent i = new Intent(context, MainActivity.class);
        int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
        int flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
        PendingIntent pIntent = PendingIntent.getActivity(context, requestID, i, flags);
        // Now we can attach this to the notification using setContentIntent
        Notification noti =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Sunshine Weather")
                        .setContentText(tmp + "° " + city)
                        .setContentIntent(pIntent)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setAutoCancel(true)
                        .build();

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, noti);
    }
}
