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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SetAlarmAdapter extends RecyclerView.Adapter<SetAlarmAdapter.ActiveAlarmViewHolder> {

    private final Context context;
    private final List<String> alarmActiveModelList;


    public SetAlarmAdapter(Context context, List<String> alarmModelList){
        this.alarmActiveModelList = alarmModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public SetAlarmAdapter.ActiveAlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.set_alarm_row, parent, false);

        return new SetAlarmAdapter.ActiveAlarmViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull SetAlarmAdapter.ActiveAlarmViewHolder holder, int position) {

        holder.alarmText.setText(alarmActiveModelList.get(position));
        holder.alarmSwitch.setChecked(true);
    }

    @Override
    public int getItemCount() {
        return alarmActiveModelList.size();
    }


    public static class ActiveAlarmViewHolder extends RecyclerView.ViewHolder {

        private TextView alarmText;
        private Switch alarmSwitch;
        private Context context;

        public ActiveAlarmViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            alarmText = itemView.findViewById(R.id.alarm_text);
            alarmSwitch = itemView.findViewById(R.id.alarm_switch);
            this.context = context;

            alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
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
                                break;
                            }
                        }

                        if (updateAlarm != null) {
                            alarmModels.remove(updateAlarm);
                            updateAlarm.setIsActive(false);
                            alarmModels.add(updateAlarm);
                            SharedService.updateAlarms(alarmModels, context);

                            Alarm.activeAlarms.clear();
                            Alarm.deactivatedAlarms.clear();
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
                        cancelAlarm(alarmText.getText().toString());
                    }
                }
            });
        }

        private void cancelAlarm(String alarm) {
            Set<AlarmModel> alarmModels = SharedService.getAlarms(context);
            Iterator itr = alarmModels.iterator();
            while (itr.hasNext()) {
                AlarmModel alarmModel = (AlarmModel) itr.next();
                if (alarmModel.getAlarm().equalsIgnoreCase(alarm)) {

                    Intent intent = new Intent(context, MyBroadCastReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                            alarmModel.getRequestCode(),
                            intent,
                            PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) itemView.getContext()
                            .getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                }
            }
        }
    }

}
