package kk.techbytecare.dailynotificationapi26.Reciever;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Random;

import kk.techbytecare.dailynotificationapi26.ClickedActivity;
import kk.techbytecare.dailynotificationapi26.Helper.NotificationHelper;
import kk.techbytecare.dailynotificationapi26.R;

public class Reciever extends BroadcastReceiver {

    NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sendNotificationAPI26(context);
        }
        else    {
            sendNotification(context);
        }
    }

    private void sendNotificationAPI26(Context context) {
        String title = "Notify App 26";
        String message = "Just to test 26";

        NotificationHelper helper;
        Notification.Builder builder;

        Intent notificationIntent = new Intent(context, ClickedActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        helper = new NotificationHelper(context);
        builder = helper.getNotification(title,message,defaultSoundUri,contentIntent,true);

        helper.getManager().notify(1,builder.build());
    }

    private void sendNotification(Context context) {
        String title = "Notify App";
        String message = "Just to test";

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent notificationIntent = new Intent(context, ClickedActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setSound(defaultSoundUri);

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,builder.build());
    }
}
