package com.example.medianet.proschool;

/**
 * Created by JANI on 03-06-2017.
 */

public class Routes {
    private String id, route_id, school_id, title, code, station, pickup_time, drop_time;

    public Routes(String id, String route_id, String school_id, String title, String code, String station,
                  String pickup_time, String drop_time) {
        this.id = id;
        this.route_id = route_id;
        this.school_id = school_id;
        this.title = title;
        this.code = code;
        this.station = station;
        this.pickup_time = pickup_time;
        this.drop_time = drop_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }

    public String getDrop_time() {
        return drop_time;
    }

    public void setDrop_time(String drop_time) {
        this.drop_time = drop_time;
    }
}
