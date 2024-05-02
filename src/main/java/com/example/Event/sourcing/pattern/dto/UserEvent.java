package com.example.Event.sourcing.pattern.dto;

public class UserEvent {
    private String id;
    private String user_id;
    private String event_type;
    private String event_data;

    private String time_stamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getEvent_data() {
        return event_data;
    }

    public void setEvent_data(String event_data) {
        this.event_data = event_data;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

}
