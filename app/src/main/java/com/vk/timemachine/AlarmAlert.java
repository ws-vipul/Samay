package com.vk.timemachine;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vk.timemachine.services.MyBroadCastReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmAlert extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_stopped_alert);

        TextView alertTitle = findViewById(R.id.alertTitle);
        Button dismissBtn = findViewById(R.id.dismissBtn);

        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MMM-yyyy hh:mm");
        String alert = simpleDateFormat.format(new Date());
        alertTitle.setText(alert);

        dismissBtn.setOnClickListener(View -> {
            MyBroadCastReceiver.mediaPlayer.stop();
            this.finish();
        });
    }
}
