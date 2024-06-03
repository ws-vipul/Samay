package com.vk.timemachine.model;


public class AlarmModel {

    private String Alarm;
    private Boolean isActive;
    private int requestCode;

    public AlarmModel(String alarm, Boolean isActive, int requestCode) {
        Alarm = alarm;
        this.isActive = isActive;
        this.requestCode = requestCode;
    }

    public String getAlarm() {
        return Alarm;
    }

    public void setAlarm(String alarm) {
        Alarm = alarm;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
