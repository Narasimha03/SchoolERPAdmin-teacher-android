package com.example.medianet.proschool;

/**
 * Created by personal on 03-06-2017.
 */

public class Hostel {
    String name, type, address, intake;

    public Hostel(String name, String type, String address, String intake) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.intake = intake;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }
}
