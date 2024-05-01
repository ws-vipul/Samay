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
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;

import com.vk.timemachine.Constants.StringConstants;
import com.vk.timemachine.MainActivity;
import com.vk.timemachine.R;


public class MyBroadCastReceiver extends BroadcastReceiver {
    private MediaPlayer mediaPlayer;
    private static final int CHANNEL_ID = 101;

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


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(new NotificationChannel(String.valueOf(CHANNEL_ID),"Activate Alarm", NotificationManager.IMPORTANCE_HIGH));

        Notification notification = new Notification();


    }
}
