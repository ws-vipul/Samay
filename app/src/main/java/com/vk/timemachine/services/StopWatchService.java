package com.vk.timemachine.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.vk.timemachine.Constants.StringConstants;
import com.vk.timemachine.R;
import com.vk.timemachine.Utils.SharedService;

public class StopWatchService extends Service {


    private static final String TAG = "StopWatchService";
    private static final String CHANNEL_ID = "StopWatch_SERVICE_ID";
    public static final String COUNT = "count";

    private final IBinder binder = new StopWatchService.LocalBinder();
    private int count = 0;
    private Handler handler;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return binder; }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getAction().equalsIgnoreCase(StringConstants.STOP_FOREGROUND_SERVICE) &&
                handler != null) {
            SharedService.updateIsStopWatchRunning("false", StopWatchService.this);
            stopForeground(true);
            stopSelf();
        } else {

            if(SharedService.getStopwatchCount(StopWatchService.this) != null){
                count = Integer.parseInt(SharedService
                        .getStopwatchCount(StopWatchService.this));
            }

            SharedService.updateIsStopWatchRunning("true", StopWatchService.this);
            handler = new Handler();
            handler.post(runnableStopWatch);

            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_ID,
                    NotificationManager.IMPORTANCE_LOW
            );

            getSystemService(NotificationManager.class)
                    .createNotificationChannel(notificationChannel);
            Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentText("StopWatch Is Running")
                    .setContentTitle(String.valueOf(R.string.app_name))
                    .setSmallIcon(R.drawable.ic_launcher_background);

            startForeground(1003, notification.build());
        }

        return START_NOT_STICKY;
    }

    Runnable runnableStopWatch = new Runnable() {
        @Override
        public void run() {

            count++;
            SharedService.updateStopWatchCount(String.valueOf(count),
                    StopWatchService.this);
            notifyUIToUpdate();
            Log.e(TAG, "Stopwatch count: " + count);
            handler.postDelayed(this, 100);

        }
    };

    private void notifyUIToUpdate() {
        Intent intent = new Intent(StringConstants.ACTION_STOPWATCH_COUNT_BROADCAST);
        intent.putExtra(COUNT, count);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedService.updateIsStopWatchRunning("false", this);
        handler.removeCallbacks(runnableStopWatch);
    }

    public class LocalBinder extends Binder {
        public StopWatchService getServiceInstance() {return StopWatchService.this; }
    }
}
