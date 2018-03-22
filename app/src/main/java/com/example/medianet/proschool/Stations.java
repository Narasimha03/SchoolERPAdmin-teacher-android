package com.example.medianet.proschool;

/**
 * Created by JANI on 03-06-2017.
 */

public class Stations {
    private String id, station_id, name, code, location, status;

    public Stations(String id, String station_id, String name, String code, String location, String status) {
        this.id = id;
        this.station_id = station_id;
        this.name = name;
        this.code = code;
        this.location = location;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
