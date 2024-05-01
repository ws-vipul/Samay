package com.vk.timemachine.model;

public class AlarmModel {

    String DateTime;
    Boolean isAlarmActive;


    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public AlarmModel(String dateTime, Boolean isAlarmActive) {
        DateTime = dateTime;
        this.isAlarmActive = isAlarmActive;
    }

    public Boolean getAlarmActive() {
        return isAlarmActive;
    }

    public void setAlarmActive(Boolean alarmActive) {
        isAlarmActive = alarmActive;
    }
}
