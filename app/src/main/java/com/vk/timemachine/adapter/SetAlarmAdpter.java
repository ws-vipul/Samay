package com.vk.timemachine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vk.timemachine.R;
import com.vk.timemachine.model.AlarmModel;
import java.util.List;

public class SetAlarmAdpter extends RecyclerView.Adapter<SetAlarmAdpter.MyViewHolder> {

    private Context context;
    private List<AlarmModel> alarmModelList;

    public SetAlarmAdpter(Context context, List<AlarmModel> alarmModelList){
        this.alarmModelList = alarmModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public SetAlarmAdpter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.set_alarm_row, parent, false);

        return new SetAlarmAdpter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetAlarmAdpter.MyViewHolder holder, int position) {

        holder.alarmText.setText(alarmModelList.get(position).getDateTime());
        holder.alarmSwitch.setChecked(alarmModelList.get(position).getAlarmActive());
    }

    @Override
    public int getItemCount() {
        return alarmModelList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView alarmText;
        Switch alarmSwitch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            alarmText = itemView.findViewById(R.id.alarm_text);
            alarmSwitch = itemView.findViewById(R.id.alarm_switch);

        }
    }

}
