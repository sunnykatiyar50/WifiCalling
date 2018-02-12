package com.sunnykatiyar.wificalling;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.sunnykatiyar.wificalling.MainActivity.activity;
import static com.sunnykatiyar.wificalling.MainActivity.context;

/**
 * Created by Sunny Katiyar on 11-02-2018.
 */

public class NotifyUser {
    int noti_id = 100;
    Intent resultIntent;
    NotificationManager notimgr;
    String new_msg;
    ServerObject connected_user;
    TaskStackBuilder stackBuilder;

    public void startNotification(String s1,ServerObject s) {
        this.new_msg=s1;
        this.connected_user=s;

        notimgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder noti_builder = new Notification.Builder(context).setContentTitle("Wifi Calling Service")
                .setContentTitle("Wifi Calling")
                .setContentText("New Message Arrived")
                .setSubText("Subtext:"+ new_msg)
                .setSmallIcon(R.drawable.ic_perm_phone_msg_black_18dp);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            noti_builder.setChannelId("SERVER_SERVICE");
        }

        resultIntent = new Intent(activity, MainActivity.class);
        stackBuilder = TaskStackBuilder.create(MainActivity.context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        noti_builder.setContentIntent(pendingIntent);
        notimgr.notify(noti_id, noti_builder.build());
    }

}