package com.vk.timemachine;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vk.timemachine.Utils.SharedService;
import com.vk.timemachine.adapter.DeActivatedAlarmsAdapter;
import com.vk.timemachine.adapter.SetAlarmAdapter;
import com.vk.timemachine.services.MyBroadCastReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class Alarm extends Fragment {

    private final static  String TAG = "AlarmFragment";

    private View alarmView;
    private RecyclerView activeAlarmRecyclerView, deactivatedAlarmRecyclerView;
    private FloatingActionButton addAlarmBtn;
    private PopupWindow popupWindow;
    private ScrollView alarmFragment;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button cancelBtn, nextBtn, setAlarmBtn;
    public static List<String> alarmModelActiveList = new ArrayList();
    public static List<String> deactivatedAlarmList = new ArrayList();
    private String date, time;
    public static SetAlarmAdapter mAdapter;

    public static DeActivatedAlarmsAdapter mDeactivatedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        alarmView = inflater.inflate(R.layout.fragment_alarm, container, false);

        initializeComponent();

        if (SharedService.getActiveAlarms(this.getActivity()) != null) {
            alarmModelActiveList.clear();
            alarmModelActiveList.addAll(SharedService.getActiveAlarms(this.getActivity()));
        }

        if (SharedService.getDeactivatedAlarms(this.getActivity()) != null) {
            deactivatedAlarmList.clear();
            deactivatedAlarmList.addAll(SharedService.getDeactivatedAlarms(this.getActivity()));
        }

        mAdapter = new SetAlarmAdapter(this.getActivity(), alarmModelActiveList);
        activeAlarmRecyclerView.setAdapter(mAdapter);
        activeAlarmRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        mDeactivatedAdapter = new DeActivatedAlarmsAdapter(this.getActivity(), deactivatedAlarmList);
        deactivatedAlarmRecyclerView.setAdapter(mDeactivatedAdapter);
        deactivatedAlarmRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        addAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlarmPopupWindow(view);
            }
        });


        // Inflate the layout for this fragment
        return alarmView;
    }

    private  void initializeComponent() {
        deactivatedAlarmRecyclerView = alarmView.findViewById(R.id.mAlarmDeactivateRecyclerView);
        activeAlarmRecyclerView = alarmView.findViewById(R.id.mAlarmActiveRecyclerView);
        addAlarmBtn = alarmView.findViewById(R.id.add_alarm_btn);
        alarmFragment = alarmView.findViewById(R.id.alarm_fragment);
    }

    private void openAlarmPopupWindow(View view) {
        LayoutInflater inflater = (LayoutInflater) alarmView.getContext()
                .getSystemService(alarmView.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.set_alarm_form, null);

        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);

        //initializing popUp Components
        datePicker = popupView.findViewById(R.id.alarm_input_date);
        timePicker= popupView.findViewById(R.id.alarm_input_time);
        nextBtn = popupView.findViewById(R.id.alarm_next_btn);
        cancelBtn = popupView.findViewById(R.id.alarm_cancel_btn);
        setAlarmBtn= popupView.findViewById(R.id.alarm_set_btn);

        popupWindow.showAtLocation(alarmFragment, Gravity.CENTER,0,0);


        cancelBtn.setOnClickListener(View -> popupWindow.dismiss());

        nextBtn.setOnClickListener(View -> {
            String month = datePicker.getMonth() < 9 ? "0" + (datePicker.getMonth()+1)
                    : String.valueOf((datePicker.getMonth()+1));
            String day = datePicker.getDayOfMonth() <= 9 ? "0" + datePicker.getDayOfMonth()
                    : String.valueOf(datePicker.getDayOfMonth());

            date = day +"-"+ month +"-"+ datePicker.getYear();

            nextBtn.setVisibility(View.GONE);
                    datePicker.setVisibility(View.GONE);
                    timePicker.setVisibility(View.VISIBLE);
                    setAlarmBtn.setVisibility(View.VISIBLE);
                });

        setAlarmBtn.setOnClickListener(View-> {

            String hr = timePicker.getHour() <= 9 ? "0" + timePicker.getHour()
                    : String.valueOf(timePicker.getHour());
            String min = timePicker.getMinute() <= 9 ? "0" + timePicker.getMinute()
                    : String.valueOf(timePicker.getMinute());
            time = hr +":"+ min+":00";
            String userInput = date+" "+time;
            popupWindow.dismiss();

            long scheduleAfter = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            try {
                Date systemDate= new Date();
                Date inputDateTime = sdf.parse(userInput);
                Date currDateTime = sdf.parse(sdf.format(systemDate));
                if(inputDateTime.compareTo(currDateTime) != -1
                        || inputDateTime.compareTo(currDateTime) != 0 ) {

                    long waitFor = inputDateTime.getTime() - currDateTime.getTime();
                    scheduleAfter = currDateTime.getTime() + waitFor;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            alarmModelActiveList.add(userInput);
            SharedService.updateActiveAlarms(new HashSet<>(alarmModelActiveList),
                    this.getActivity());

            mAdapter.notifyDataSetChanged();

            Intent intent = new Intent(this.getActivity(), MyBroadCastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getActivity(),
                    100,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE);

            if (scheduleAfter != 0) {
                AlarmManager alarmManager= (AlarmManager) getContext()
                        .getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, (scheduleAfter), pendingIntent);
            }

        });

    }

    private void cancelAlarm() {
        Intent intent = new Intent(this.getActivity(), MyBroadCastReceiver.class);
        intent.setAction("SomeAction");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 100, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        if(pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
    }


}