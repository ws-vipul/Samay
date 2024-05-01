package com.vk.timemachine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.vk.timemachine.Constants.StringConstants;
import com.vk.timemachine.Utils.SharedService;
import com.vk.timemachine.services.MyBroadCastReceiver;

public class MainActivity extends AppCompatActivity {

    Button timeConverterBtn, timerBtn, stopWatchBtn, alarmBtn;
    ConstraintLayout mainActivity;

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
        }
    };
}