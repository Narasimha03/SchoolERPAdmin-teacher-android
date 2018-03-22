package com.example.medianet.proschool;

/**
 * Created by JANI on 24-06-2017.
 */

public class Vehicles {
    private String id, code, name, school_id, status;

    public Vehicles() {
    }

    public Vehicles(String id, String code, String name, String school_id, String status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.school_id = school_id;

        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
