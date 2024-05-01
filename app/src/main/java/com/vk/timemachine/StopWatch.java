package com.vk.timemachine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vk.timemachine.Constants.StringConstants;
import com.vk.timemachine.Utils.SharedService;
import com.vk.timemachine.services.StopWatchService;
import com.vk.timemachine.services.TimerService;

import java.util.Locale;


public class StopWatch extends Fragment {

    private static final String TAG = "StopWatchActivity";

    private View stopwatchView;
    private TextView stopwatchCountTextView;
    private Button startBtn, stopBtn, resetBtn;

    private int stopWatchCount = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        stopwatchView = inflater.inflate(R.layout.fragment_stop_watch,
                container, false);

        initializeComponent();

        startBtn.setOnClickListener(View -> {
            Intent startStopWatch = new Intent(StopWatch.this.getActivity(), StopWatchService.class);
            startStopWatch.setAction(StringConstants.START_FOREGROUND_SERVICE);
            StopWatch.this.getActivity().startForegroundService(startStopWatch);
            startBtn.setVisibility(android.view.View.GONE);
            stopBtn.setVisibility(android.view.View.VISIBLE);
            resetBtn.setEnabled(false);
        });

        stopBtn.setOnClickListener(View -> {
            Intent stopStopWatch = new Intent(StopWatch.this.getActivity(), StopWatchService.class);
            stopStopWatch.setAction(StringConstants.STOP_FOREGROUND_SERVICE);
            StopWatch.this.getActivity().startForegroundService(stopStopWatch);
            stopBtn.setVisibility(android.view.View.GONE);
            startBtn.setVisibility(android.view.View.VISIBLE);
            if(stopWatchCount != 0) {
                resetBtn.setEnabled(true);
            }
        });

        resetBtn.setOnClickListener(View -> {
            stopWatchCount = 0;
            SharedService.updateStopWatchCount(null, this.getActivity());
            stopwatchCountTextView.setText("00:00:00");
        });


        return stopwatchView;
    }

    private void prefill() {
        if(SharedService.getTimerCount(StopWatch.this.getActivity())!= null) {

            if (SharedService.getIsStopwatchRunning(this.getActivity()) != null
                    && SharedService.getIsStopwatchRunning(this.getActivity())
                    .equalsIgnoreCase("true")) {
                startBtn.setVisibility(android.view.View.GONE);
                stopBtn.setVisibility(View.VISIBLE);
            }

            stopWatchCount = Integer.parseInt(SharedService.getStopwatchCount(StopWatch.this.getActivity()));
            int hours = stopWatchCount/3600;
            int minutes = (stopWatchCount % 3600) / 60;
            int seconds = (stopWatchCount % 60);


            String stopwatchValue = String.format(Locale.getDefault(),
                    "%02d:%02d:%02d", hours, minutes, seconds);

            stopwatchCountTextView.setText(stopwatchValue);
        }
    }


    private void initializeComponent() {

        stopwatchCountTextView = stopwatchView.findViewById(R.id.stopwatch);
        startBtn = stopwatchView.findViewById(R.id.stopwatch_start);
        resetBtn = stopwatchView.findViewById(R.id.stopwatch_reset);
        stopBtn = stopwatchView.findViewById(R.id.stopwatch_stop);

    }


    BroadcastReceiver mStopWatchCountReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getIntExtra(StopWatchService.COUNT, 1) != 0) {
                updateUITimerCount(intent);
            }

        }
    };

    private void updateUITimerCount(Intent intent) {
        if(intent.getExtras() != null) {
            stopWatchCount = intent.getIntExtra(TimerService.COUNT, 1);
            int hours = stopWatchCount/3600;
            int minutes = (stopWatchCount % 3600) / 60;
            int seconds = (stopWatchCount % 60);

            String timerValue = String.format(Locale.getDefault(),
                    "%02d:%02d:%02d", hours, minutes, seconds);

            stopwatchCountTextView.setText(timerValue);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.getActivity()
                .registerReceiver(mStopWatchCountReceiver,
                        new IntentFilter(StringConstants.ACTION_STOPWATCH_COUNT_BROADCAST));
        if(SharedService.getIsStopwatchRunning(this.getActivity()) != null
                && SharedService.getIsStopwatchRunning(StopWatch.this.getActivity())
                .equalsIgnoreCase("true")) {
            prefill();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        this.getActivity().unregisterReceiver(mStopWatchCountReceiver);
    }
}