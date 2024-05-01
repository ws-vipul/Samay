package com.vk.timemachine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.timemachine.Constants.StringConstants;
import com.vk.timemachine.Utils.SharedService;
import com.vk.timemachine.services.TimerService;

import java.util.Locale;

public class Timer extends Fragment {
    private View timerView;

    private TextView timerCountTextView;
    private Button startBtn, stopBtn, resetBtn;
    private NumberPicker hrsPicker, minutesPicker, secondsPicker;
    LinearLayout timePicker;
    private int timerCount = 0;
    private Boolean isReset = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        timerView= inflater.inflate(R.layout.fragment_timer, container, false);

        initializeComponent();

        prefill();


        startBtn.setOnClickListener(View -> {
            int inputTimeCount = 0;
            if (isReset && SharedService.getTimerCount(this.getActivity()) == null) {
                int inputHrs = hrsPicker.getValue();
                int inputMinutes = minutesPicker.getValue();
                int inputSeconds = secondsPicker.getValue();
                if(inputHrs == 0 && inputMinutes == 0 && inputSeconds == 0) {
                  Toast.makeText(this.getActivity(), "Please Select Valid Time" +
                            ", Could Not Start Timer For Now",Toast.LENGTH_SHORT).show();
                } else {
                    inputTimeCount = inputSeconds + (inputMinutes * 60) + (inputHrs * 3600);
                    SharedService.updateTimerCount(String.valueOf(inputTimeCount),this.getActivity());
                }
            }

            if(inputTimeCount != 0 || !isReset || SharedService.getTimerCount(this.getActivity()) != null) {
                Intent startTimer = new Intent(Timer.this.getActivity(), TimerService.class);
                startTimer.setAction(StringConstants.START_FOREGROUND_SERVICE);
                Timer.this.getActivity().startForegroundService(startTimer);
                timePicker.setVisibility(android.view.View.GONE);
                timerCountTextView.setVisibility(android.view.View.VISIBLE);
                startBtn.setVisibility(android.view.View.GONE);
                stopBtn.setVisibility(android.view.View.VISIBLE);
                resetBtn.setEnabled(false);
            }
        });

        stopBtn.setOnClickListener(View -> {
            isReset = false;
            SharedService.updateIsTimerRunning("paused", this.getActivity());
            Intent stopTimer = new Intent(Timer.this.getActivity(), TimerService.class);
            stopTimer.setAction(StringConstants.STOP_FOREGROUND_SERVICE);
            Timer.this.getActivity().startForegroundService(stopTimer);
            stopBtn.setVisibility(android.view.View.GONE);
            startBtn.setVisibility(android.view.View.VISIBLE);
            if(timerCount != 0) {
                resetBtn.setEnabled(true);
            }
        });

        resetBtn.setOnClickListener(View -> {
            isReset = true;
            timerCount = 0;
            startBtn.setEnabled(true);
            SharedService.updateIsTimerRunning("false", this.getActivity());
            timePicker.setVisibility(android.view.View.VISIBLE);
            timerCountTextView.setVisibility(android.view.View.GONE);
            SharedService.updateTimerCount(null, this.getActivity());
            timerCountTextView.setText("00:00:00");
        });

        return timerView;
    }

    private void prefill() {

        hrsPicker.setMaxValue(99);
        minutesPicker.setMaxValue(59);
        secondsPicker.setMaxValue(59);

        if(SharedService.getTimerCount(Timer.this.getActivity())!= null) {

            if (SharedService.getIsTimerRunning(this.getActivity()) != null
                    && SharedService.getIsTimerRunning(this.getActivity())
                    .equalsIgnoreCase("true") || SharedService.getIsTimerRunning(this.getActivity())
                    .equalsIgnoreCase("paused")) {
                timePicker.setVisibility(View.GONE);
                timerCountTextView.setVisibility(View.VISIBLE);
                if(SharedService.getIsTimerRunning(this.getActivity())
                        .equalsIgnoreCase("paused")) {
                    stopBtn.setVisibility(View.GONE);
                    startBtn.setVisibility(View.VISIBLE);
                    resetBtn.setEnabled(true);
                } else {
                    startBtn.setVisibility(android.view.View.GONE);
                    stopBtn.setVisibility(View.VISIBLE);
                }
            }


            timerCount = Integer.parseInt(SharedService.getTimerCount(Timer.this.getActivity()));
            int hours = timerCount/3600;
            int minutes = (timerCount % 3600) / 60;
            int seconds = (timerCount % 60);

            String timerValue = String.format(Locale.getDefault(),
                    "%02d:%02d:%02d", hours, minutes, seconds);

            timerCountTextView.setText(timerValue);
        }
    }

    private void initializeComponent() {

        timerCountTextView = timerView.findViewById(R.id.timer);
        startBtn = timerView.findViewById(R.id.timer_start);
        resetBtn = timerView.findViewById(R.id.timer_reset);
        stopBtn = timerView.findViewById(R.id.timer_stop);
        hrsPicker = timerView.findViewById(R.id.time_picker_hrs);
        minutesPicker = timerView.findViewById(R.id.time_picker_minutes);
        secondsPicker = timerView.findViewById(R.id.time_picker_seconds);
        timePicker = timerView.findViewById(R.id.time_picker);

    }


    BroadcastReceiver mTimerCountReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getIntExtra(TimerService.COUNT, 0) >= 0) {
                updateUITimerCount(intent);
            }

        }
    };

    private void updateUITimerCount(Intent intent) {
        if(intent.getExtras() != null) {
            timerCount = intent.getIntExtra(TimerService.COUNT, 0);
            int hours = timerCount/3600;
            int minutes = (timerCount % 3600) / 60;
            int seconds = (timerCount % 60);

            String timerValue = String.format(Locale.getDefault(),
                    "%02d:%02d:%02d", hours, minutes, seconds);

            timerCountTextView.setText(timerValue);
            if(timerCount == 0) {
                resetBtn.setEnabled(true);
                stopBtn.setVisibility(View.GONE);
                startBtn.setVisibility(View.VISIBLE);
                startBtn.setEnabled(false);
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.getActivity()
                .registerReceiver(mTimerCountReceiver,
                new IntentFilter(StringConstants.ACTION_TIMER_COUNT_BROADCAST));

        if (SharedService.getIsTimerRunning(this.getActivity()) != null
                && SharedService.getIsTimerRunning(this.getActivity())
                .equalsIgnoreCase("true")) {
            prefill();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        this.getActivity().unregisterReceiver(mTimerCountReceiver);
    }
}
