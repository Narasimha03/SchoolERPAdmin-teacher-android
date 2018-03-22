package com.example.medianet.proschool;

/**
 * Created by JANI on 09-05-2017.
 */

public class FeeMaster {
    String id, stdClass, name, amount;

    public FeeMaster(String id, String stdClass, String name, String amount) {
        this.id = id;
        this.stdClass = stdClass;
        this.name = name;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStdClass() {
        return stdClass;
    }

    public void setStdClass(String stdClass) {
        this.stdClass = stdClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
