package com.vk.timemachine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.timemachine.Constants.StringConstants;
import com.vk.timemachine.Utils.SharedService;
import com.vk.timemachine.services.MyBroadCastReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button timeConverterBtn, timerBtn, stopWatchBtn, alarmBtn;
    ConstraintLayout mainActivity;

    private static final int permissionRequest = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializecomponents();

        timeConverterBtn.setOnClickListener(View -> {
            getFragment(StringConstants.TIME_CONVERTER_FRAGMENT);
        });

        timerBtn.setOnClickListener(View -> {
            getFragment(StringConstants.TIMER_FRAGMENT);
        });

        stopWatchBtn.setOnClickListener(View -> {
            getFragment(StringConstants.STOPWATCH_FRAGMENT);
        });

        alarmBtn.setOnClickListener(View -> {
            getFragment(StringConstants.ALARM_FRAGMENT);
        });

        checkForNotificationPermission();

    }

    private void checkForNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            requestPermissions(new String[] {Manifest.permission.POST_NOTIFICATIONS }, permissionRequest);
        }
    }

    private void initializecomponents() {

        timeConverterBtn = findViewById(R.id.timeConverterBtn);
        timerBtn = findViewById(R.id.timerBtn);
        stopWatchBtn = findViewById(R.id.stopWatchBtn);
        alarmBtn = findViewById(R.id.alarmBtn);
        mainActivity= findViewById(R.id.mainActivity);

    }

    private void getFragment(String fragmentName) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        switch (fragmentName) {
            case StringConstants.TIME_CONVERTER_FRAGMENT:
                SharedService.updateLastFragment(StringConstants.TIME_CONVERTER_FRAGMENT,
                        this);
                transaction.replace(R.id.midFragmentContainerView, new TimeConverter());
                transaction.commit();
                break;

            case StringConstants.TIMER_FRAGMENT:
                SharedService.updateLastFragment(StringConstants.TIMER_FRAGMENT,
                        this);
                transaction.replace(R.id.midFragmentContainerView, new Timer());
                transaction.commit();
                break;
            case StringConstants.STOPWATCH_FRAGMENT:
                SharedService.updateLastFragment(StringConstants.STOPWATCH_FRAGMENT,
                        this);
                transaction.replace(R.id.midFragmentContainerView, new StopWatch());
                transaction.commit();
                break;
            case StringConstants.ALARM_FRAGMENT:
                SharedService.updateLastFragment(StringConstants.ALARM_FRAGMENT,
                        this);
                transaction.replace(R.id.midFragmentContainerView, new Alarm());
                transaction.commit();
                break;
            default:
                SharedService.updateLastFragment(StringConstants.TIME_CONVERTER_FRAGMENT,
                        this);
                transaction.replace(R.id.midFragmentContainerView, new TimeConverter());
                transaction.commit();
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(StringConstants.AlarmBroadCast));
        if (SharedService.getLastFragment(this) != null) {
            getFragment(SharedService.getLastFragment(this));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView= inflater.inflate(R.layout.service_stopped_alert, null);
            PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAtLocation(mainActivity, Gravity.CENTER,0,0);
            popupView.setElevation(200);

            Button dismissBtn = popupView.findViewById(R.id.dismissBtn);
            TextView alertTitle = popupView.findViewById(R.id.alertTitle);
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MMM-yyyy hh:mm");
            String alert = simpleDateFormat.format(new Date());
            alertTitle.setText(alert);

            dismissBtn.setOnClickListener(View -> {
                MyBroadCastReceiver.mediaPlayer.stop();
                popupWindow.dismiss();
            });

        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == permissionRequest) {
            if ( grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Allowed Notification Permission For Alarm", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Denied Notification Permission For Alarm", Toast.LENGTH_SHORT).show();
            }
        }
    }
}