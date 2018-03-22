package com.example.medianet.proschool;

/**
 * Created by JANI on 03-06-2017.
 */

public class HostelRoom {
    private String no, hostel, type, nobed, cost;

    public HostelRoom(String no, String hostel, String type, String nobed, String cost) {
        this.no = no;
        this.hostel = hostel;
        this.type = type;
        this.nobed = nobed;
        this.cost = cost;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNobed() {
        return nobed;
    }

    public void setNobed(String nobed) {
        this.nobed = nobed;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
