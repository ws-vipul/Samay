package com.vk.timemachine;

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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.vk.timemachine.adapter.TimeZoneAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeConverter extends Fragment {

    private static final String TAG = "TimeConverterFragment";

    private TimeZoneAdapter madapter;
    private View converterView;
    private AutoCompleteTextView timezoneFrom, timezoneTo;
    private EditText UTCTimeTextView, toTimeTimeTextView;
    private DatePicker converterDatePicker;
    private NumberPicker hrPicker, minPicker;
    private TextView textToTimeZone;
    private Button convertBtn, switchTimeZones;

    private String fromTimeZoneId = "Asia/Calcutta", utcTimeZoneId = "UTC", toTimeZoneId = "Asia/Calcutta";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        converterView = inflater.inflate(R.layout.fragment_time_converter,
                container, false);

        initializeComponent();
        hrPicker.setMaxValue(23);
        minPicker.setMaxValue(59);
        try {
            prefill();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        madapter = new TimeZoneAdapter();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_selectable_list_item, madapter.getTimeZones());

        timezoneFrom.setThreshold(1);
        timezoneFrom.setOnItemClickListener(timezoneFromItem);
        timezoneFrom.setAdapter(adapter);
        timezoneTo.setThreshold(1);
        timezoneTo.setOnItemClickListener(timezoneToItem);
        timezoneTo.setAdapter(adapter);

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
            textToTimeZone.setText("Time In " + tempTimeZoneFrom);
            String tempFromZoneId = fromTimeZoneId;
            fromTimeZoneId = toTimeZoneId;
            toTimeZoneId = tempFromZoneId;
        });

        return converterView;
    }

    private void initializeComponent() {


        converterDatePicker = converterView.findViewById(R.id.tc_input_date_picker);
        hrPicker = converterView.findViewById(R.id.tc_input_hr);
        minPicker = converterView.findViewById(R.id.tc_input_minute);
        timezoneFrom = converterView.findViewById(R.id.timezoneFrom);
        timezoneTo = converterView.findViewById(R.id.timezoneTo);
        toTimeTimeTextView = converterView.findViewById(R.id.to_timezone_time);
        UTCTimeTextView = converterView.findViewById(R.id.to_utc_timezone);
        convertBtn = converterView.findViewById(R.id.convertBtn);
        textToTimeZone = converterView.findViewById(R.id.to_timezone);
        switchTimeZones = converterView.findViewById(R.id.switchBtn);

    }

    private void prefill() throws ParseException {
        convertAndSetTime();
    }

    private void convertAndSetTime() throws ParseException {
        String input= new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());

        if (!timezoneFrom.getText().equals("") && !timezoneTo.getText().equals("")) {
            String month = converterDatePicker.getMonth() < 9 ? "0"
                    + (converterDatePicker.getMonth() + 1)
                    : String.valueOf((converterDatePicker.getMonth() + 1));
            String day = converterDatePicker.getDayOfMonth() <= 9 ? "0"
                    + converterDatePicker.getDayOfMonth()
                    : String.valueOf(converterDatePicker.getDayOfMonth());

            String date = day + "-" + month + "-" + converterDatePicker.getYear();

            String hr = hrPicker.getValue() <= 9 ? "0" + hrPicker.getValue()
                    : String.valueOf(hrPicker.getValue());
            String min = minPicker.getValue() <= 9 ? "0" + minPicker.getValue()
                    : String.valueOf(minPicker.getValue());

            input = date + " " + hr + ":" + min;

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
                textToTimeZone.setText("Time In " + parent.getItemAtPosition(position).toString());
            }
        };

}