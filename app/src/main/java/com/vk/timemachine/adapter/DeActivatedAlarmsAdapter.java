package com.vk.timemachine.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vk.timemachine.Alarm;
import com.vk.timemachine.R;
import com.vk.timemachine.Utils.SharedService;
import com.vk.timemachine.model.AlarmModel;
import com.vk.timemachine.services.MyBroadCastReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class DeActivatedAlarmsAdapter extends RecyclerView.Adapter<DeActivatedAlarmsAdapter
        .DeaactivatedAlarmViewHolder> {

    private final Context context;
    private List<String> alarmDeactivatedList;

    public DeActivatedAlarmsAdapter(Context context, List<String> alarmDeactivatedList){
        this.alarmDeactivatedList = alarmDeactivatedList;
        this.context = context;
    }

    @NonNull
    @Override
    public DeActivatedAlarmsAdapter.DeaactivatedAlarmViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.set_alarm_row, parent, false);

        return new DeActivatedAlarmsAdapter.DeaactivatedAlarmViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(
            @NonNull DeActivatedAlarmsAdapter.DeaactivatedAlarmViewHolder holder, int position) {
        holder.alarmText.setText(alarmDeactivatedList.get(position));
        holder.alarmSwitch.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return alarmDeactivatedList.size();
    }


    public static class DeaactivatedAlarmViewHolder extends RecyclerView.ViewHolder {

        TextView alarmText;
        Switch alarmSwitch;

        public DeaactivatedAlarmViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            alarmText = itemView.findViewById(R.id.alarm_text);
            alarmSwitch = itemView.findViewById(R.id.alarm_switch);

            alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Set<AlarmModel> alarmModels = new HashSet<>();
                        if (SharedService.getAlarms(context) != null) {
                            alarmModels  = SharedService.getAlarms(context);
                        }

                        Iterator itr= alarmModels.iterator();

                        AlarmModel updateAlarm = null;
                        while(itr.hasNext()) {
                            AlarmModel verifier = (AlarmModel) itr.next();
                            if(verifier.getAlarm().equalsIgnoreCase(alarmText.getText().toString())) {
                                updateAlarm = verifier;
                            }
                        }

                        if (updateAlarm != null) {
                            alarmModels.remove(updateAlarm);
                            updateAlarm.setIsActive(true);
                            alarmModels.add(updateAlarm);

                            SharedService.updateAlarms(alarmModels, context);
                            Alarm.activeAlarms.clear();
                            Alarm.deactivatedAlarms.clear();

                            Alarm.activeAlarms.addAll(SharedService.getAlarms(context).stream()
                                    .filter(alarmModel -> alarmModel.getIsActive().equals(true))
                                    .map(alarmModel -> alarmModel.getAlarm())
                                    .collect(Collectors.toList()));
                            Alarm.deactivatedAlarms.addAll(SharedService.getAlarms(context).stream()
                                    .filter(alarmModel -> alarmModel.getIsActive().equals(false))
                                    .map(alarmModel -> alarmModel.getAlarm())
                                    .collect(Collectors.toList()));

                            Alarm.mAdapter.notifyDataSetChanged();
                            Alarm.mDeactivatedAdapter.notifyDataSetChanged();
                        }
                        activateAlarm(alarmText.getText().toString());
                    }
                }
            });
        }

        private void activateAlarm(String alarm) {
            Set<AlarmModel> alarmModels = SharedService.getAlarms(itemView.getContext());
            Iterator itr = alarmModels.iterator();
            while (itr.hasNext()) {
                AlarmModel alarmModel = (AlarmModel) itr.next();
                if (alarmModel.getAlarm().equalsIgnoreCase(alarm)) {

                    long scheduleAfter = 0;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    try {
                        Date systemDate= new Date();
                        Date inputDateTime = sdf.parse(alarmModel.getAlarm());
                        Date currDateTime = sdf.parse(sdf.format(systemDate));
                        if(inputDateTime.compareTo(currDateTime) != -1
                                || inputDateTime.compareTo(currDateTime) != 0 ) {

                            long waitFor = inputDateTime.getTime() - currDateTime.getTime();
                            scheduleAfter = currDateTime.getTime() + waitFor;
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    Intent intent = new Intent(itemView.getContext(), MyBroadCastReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(itemView.getContext(),
                            alarmModel.getRequestCode(),
                            intent,
                            PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) itemView.getContext()
                            .getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, (scheduleAfter), pendingIntent);
                }
            }
        }
    }
}
