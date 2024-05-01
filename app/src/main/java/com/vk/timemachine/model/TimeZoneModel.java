package com.vk.timemachine.model;

public class TimeZoneModel {

    private String timeZoneId;
    private String timeZone;

    public TimeZoneModel(String timeZoneId, String timeZone) {
        this.timeZoneId = timeZoneId;
        this.timeZone = timeZone;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
