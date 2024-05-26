package com.vk.timemachine;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import com.vk.timemachine.adapter.TimeZoneAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeConverter extends Fragment {

    private static final String TAG = "TimeConverterFragment";
    private TimeZoneAdapter madapter;
    private View converterView;
    private AutoCompleteTextView timezoneFrom, timezoneTo;
    private EditText UTCTimeTextView, toTimeTimeTextView, inputDate, inputTime;
    private TextView textToTimeZone;
    private Button convertBtn, switchTimeZones;
    final Calendar calendar= Calendar.getInstance();

    private String fromTimeZoneId = "Asia/Calcutta", utcTimeZoneId = "UTC", toTimeZoneId = "Asia/Calcutta";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        converterView = inflater.inflate(R.layout.fragment_time_converter,
                container, false);

        initializeComponent();
        
        try {
            prefill();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        madapter = new TimeZoneAdapter();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_selectable_list_item, madapter.getTimeZones());

        timezoneFrom.setThreshold(1);
        timezoneFrom.setOnItemClickListener(timezoneFromItem);
        timezoneFrom.setAdapter(adapter);
        timezoneTo.setThreshold(1);
        timezoneTo.setOnItemClickListener(timezoneToItem);
        timezoneTo.setAdapter(adapter);
        
        inputDate.setOnClickListener(View -> 
                getDatePicker());

        inputTime.setOnClickListener(View ->
                getTimePicker());

        convertBtn.setOnClickListener(View -> {
            try {
                convertAndSetTime();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        switchTimeZones.setOnClickListener(View -> {
            String tempTimeZoneFrom = timezoneFrom.getText().toString();
            timezoneFrom.setText(timezoneTo.getText().toString());
            timezoneTo.setText(tempTimeZoneFrom);
            textToTimeZone.setText(getResources().getString(R.string.time_in)
                    + tempTimeZoneFrom);
            String tempFromZoneId = fromTimeZoneId;
            fromTimeZoneId = toTimeZoneId;
            toTimeZoneId = tempFromZoneId;
        });

        return converterView;
    }

    private void initializeComponent() {
        
        inputDate = converterView.findViewById(R.id.input_tc_date);
        inputTime = converterView.findViewById(R.id.input_tc_time);
        timezoneFrom = converterView.findViewById(R.id.timezoneFrom);
        timezoneTo = converterView.findViewById(R.id.timezoneTo);
        toTimeTimeTextView = converterView.findViewById(R.id.to_timezone_time);
        UTCTimeTextView = converterView.findViewById(R.id.to_utc_timezone);
        convertBtn = converterView.findViewById(R.id.convertBtn);
        textToTimeZone = converterView.findViewById(R.id.to_timezone);
        switchTimeZones = converterView.findViewById(R.id.switchBtn);

    }
    
    private void getDatePicker() {
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateDateLabel();
            }

        };

        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),
                        AlertDialog.THEME_HOLO_DARK,
                        date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void getTimePicker() {
        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour = String.valueOf(hourOfDay);
                String minutes = String.valueOf(minute);
                updateTimeLabel(hour, minutes);
            }
        };

        inputTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(),
                        AlertDialog.THEME_HOLO_DARK,
                        time,
                        calendar.get(Calendar.HOUR),
                        calendar.get(Calendar.MINUTE),
                        true).show();
            }
        });
    }

    private void updateDateLabel(){
        String myFormat="dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        inputDate.setText(dateFormat.format(calendar.getTime()));
    }
    private void updateTimeLabel(String hour, String minute){
        inputTime.setText(hour +":"+minute);
    }

    private void prefill() throws ParseException {
        convertAndSetTime();
    }

    private void convertAndSetTime() throws ParseException {
        String input= new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());

        if (!timezoneFrom.getText().toString().equals("")
                && !timezoneTo.getText().toString().equals("")
        && inputDate.getText().equals("") && inputTime.getText().equals("")) {

            input = inputDate.getText() + " " + inputTime.getText();
        } else {
            inputDate.setText(input.toString().split(" ")[0]);
            inputTime.setText(input.toString().split(" ")[1]);
        }

        SimpleDateFormat formatInput = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        formatInput.setTimeZone(TimeZone.getTimeZone(fromTimeZoneId));
        Date processInput = formatInput.parse(input);

        formatInput.setTimeZone(TimeZone.getTimeZone(utcTimeZoneId));
        UTCTimeTextView.setText(formatInput.format(processInput));

        formatInput.setTimeZone(TimeZone.getTimeZone(toTimeZoneId));
        toTimeTimeTextView.setText(formatInput.format(processInput));

    }

    AdapterView.OnItemClickListener timezoneFromItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            fromTimeZoneId = madapter.getTimeZoneId(parent.getItemAtPosition(position).toString());
        }
    };

        AdapterView.OnItemClickListener timezoneToItem = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toTimeZoneId = madapter.getTimeZoneId(parent.getItemAtPosition(position).toString());
                textToTimeZone.setText(getResources().getString(R.string.time_in)
                        + parent.getItemAtPosition(position).toString());
            }
        };

}