package com.vk.timemachine.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.vk.timemachine.Constants.StringConstants;
import com.vk.timemachine.R;
import com.vk.timemachine.Utils.SharedService;

public class TimerService extends Service {


    private static final String TAG = "TimerService";

    private static final String CHANNEL_ID = "TIMER_SERVICE_ID";
    public static final String COUNT = "count";

    private final IBinder binder = new TimerService.LocalBinder();
    private int count = 0;
    private Handler handler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getAction().equalsIgnoreCase(StringConstants.STOP_FOREGROUND_SERVICE) &&
        handler != null) {
            stopForeground(true);
            stopSelf();
        } else {
            if(SharedService.getTimerCount(TimerService.this) != null){
                count = Integer.parseInt(SharedService.getTimerCount(TimerService.this));
            }

            SharedService.updateIsTimerRunning("true", TimerService.this);
            handler = new Handler();
            handler.post(runnableTimer);

            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_ID,
                    NotificationManager.IMPORTANCE_LOW
            );

            getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
            Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentText("Timer Is Running")
                    .setContentTitle(String.valueOf(R.string.app_name))
                    .setSmallIcon(R.drawable.ic_launcher_background);

            startForeground(1001, notification.build());
        }

        return START_NOT_STICKY;
    }

    Runnable runnableTimer = new Runnable() {
        @Override
        public void run() {

            if(count == 0) {
                stopForeground(true);
                stopSelf();
                notifyUIToUpdate();
            } else {
                count--;
                SharedService.updateTimerCount(String.valueOf(count), TimerService.this);
                notifyUIToUpdate();
                Log.e(TAG, "Timer count: " + count);
                handler.postDelayed(this, 1000);
            }
        }
    };

    private void notifyUIToUpdate() {
        Intent intent = new Intent(StringConstants.ACTION_TIMER_COUNT_BROADCAST);
        intent.putExtra(COUNT, count);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnableTimer);
    }

    public class LocalBinder extends Binder {
        public TimerService getServiceInstance() {return TimerService.this; }
    }
}
