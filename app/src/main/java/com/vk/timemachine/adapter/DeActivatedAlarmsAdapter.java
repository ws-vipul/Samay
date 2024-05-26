package com.vk.timemachine.adapter;

import android.content.Context;
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


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
                        Set<String> activeAlarm = new HashSet<>();
                        Set<String> deActivatedAlarm = new HashSet<>();
                        if (SharedService.getActiveAlarms(context) != null) {
                            activeAlarm  = SharedService.getActiveAlarms(context);
                        }
                        if (SharedService.getDeactivatedAlarms(context) != null) {
                            deActivatedAlarm = SharedService.getDeactivatedAlarms(context);
                        }
                        Iterator itr= deActivatedAlarm.iterator();

                        String updateAlarm = null;
                        while(itr.hasNext()) {
                            String verifier = itr.next().toString();
                            if(verifier.equalsIgnoreCase(alarmText.getText().toString())) {
                                updateAlarm = verifier;
                            }
                        }

                        if (updateAlarm != null) {
                            activeAlarm.add(updateAlarm);
                            SharedService.updateActiveAlarms(activeAlarm, context);
                            deActivatedAlarm.remove(updateAlarm);
                            SharedService.updateDeactivatedAlarms(deActivatedAlarm, context);
                            Alarm.alarmModelActiveList.clear();
                            Alarm.deactivatedAlarmList.clear();
                            Alarm.alarmModelActiveList.addAll(SharedService.getActiveAlarms(context));
                            Alarm.deactivatedAlarmList.addAll(SharedService.getDeactivatedAlarms(context));
                            Alarm.mAdapter.notifyDataSetChanged();
                            Alarm.mDeactivatedAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}
