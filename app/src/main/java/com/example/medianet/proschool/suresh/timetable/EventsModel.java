package com.example.medianet.proschool.suresh.timetable;

/**
 * Created by harish on 2/26/2018.
 */

public class EventsModel {
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public EventsModel(String eventName, String desc) {
        this.eventName = eventName;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    String eventName,desc;
}
