package com.example.medianet.proschool;

/**
 * Created by JANI on 07-06-2017.
 */

public class MarksGrade {
    private String name, from, to;

    public MarksGrade(String name, String from, String to) {
        this.name = name;
        this.from = from;
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
