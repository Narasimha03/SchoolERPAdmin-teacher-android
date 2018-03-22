package com.example.medianet.proschool;

/**
 * Created by JANI on 06-06-2017.
 */

public class Expense {
    private String head, date, name, amount;

    public Expense(String head, String date, String name, String amount) {
        this.head = head;
        this.date = date;
        this.name = name;
        this.amount = amount;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
