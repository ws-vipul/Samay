package com.vk.timemachine.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

import com.vk.timemachine.AlarmAlert;
import com.vk.timemachine.Constants.StringConstants;
import com.vk.timemachine.MainActivity;
import com.vk.timemachine.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MyBroadCastReceiver extends BroadcastReceiver {
    public static MediaPlayer mediaPlayer;
    private static final String CHANNEL_ID = "101";

    @Override
    public void onReceive(Context context, Intent intent) {
        mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();

        Intent sendUIMessage = new Intent(StringConstants.AlarmBroadCast);
        sendUIMessage.putExtra("alarm_activated", StringConstants.alarmActivated);
        context.sendBroadcast(sendUIMessage);

        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.samay_logo, null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();

        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MMM-yyyy hh:mm");
        String alert = simpleDateFormat.format(new Date());

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;

        Intent intentAlert = new Intent(context, AlarmAlert.class);
        intentAlert.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 02, intentAlert
        , PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(context)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.samay_logo)
                    .setContentText("Alarm Activated")
                    .setSubText(alert)
                    .setChannelId(CHANNEL_ID)
                    .setAutoCancel(false)
                    .addAction(R.drawable.ic_launcher_foreground, "View", pendingIntent)
                    .build();

             notificationManager.createNotificationChannel(new NotificationChannel(
                     CHANNEL_ID,
                     "Alarm Notification",
                     NotificationManager.IMPORTANCE_HIGH));
        } else {
            notification = new Notification.Builder(context)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.samay_logo)
                    .setContentText("Alarm Activated")
                    .setSubText(alert)
                    .setAutoCancel(false)
                    .addAction(R.drawable.ic_launcher_foreground, "View", pendingIntent)
                    .build();
        }

        notificationManager.notify(100, notification);

    }
}
