package com.example.medianet.proschool;

/**
 * Created by JANI on 07-06-2017.
 */

public class Teacher {
    private String name, email, dob, phone;

    public Teacher(String name, String email, String dob, String phone) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
