package com.example.medianet.proschool.suresh.checkboxs;
/**
 * Created by USER on 11/27/2017.
 */

import java.util.ArrayList;

/**
 * Created by JANI on 08-05-2017.
 */

public class DemoCheckBox {


    String stdId, stdAdNo, stdName, stdClass, fatherName, stdDob, stdGender, category, mobile, rte, stdRollNo,demo,presentStatus,absentStatus ,onLeaveStatus;
    public boolean isDemoSelected;
    public boolean isPresentSelected;
    public boolean isAbsentSelected;
    public boolean isLeaveSelected;

    public boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }





    public DemoCheckBox(String stdId, String stdAdNo, String stdName, String stdClass, String fatherName,
                        String stdDob, String stdGender, String category, String mobile, String rte, String stdRollNo,String demo,String presentStatus,String absentStatus ,String onLeaveStatus) {
        this.stdId = stdId;
        this.stdAdNo = stdAdNo;
        this.stdName = stdName;
        this.stdClass = stdClass;
        this.fatherName = fatherName;
        this.stdDob = stdDob;
        this.stdGender = stdGender;
        this.category = category;
        this.mobile = mobile;
        this.rte = rte;
        this.stdRollNo = stdRollNo;
        this.demo=demo;
        this.presentStatus=presentStatus;
        this.absentStatus=absentStatus;
        this.onLeaveStatus=onLeaveStatus;
       // this.isSelected=isSelected;
    }


    public String getDemo() {
        return demo;
    }
    public String getPresentStatus() {
        return presentStatus;
    }

    public String getAbsentStatus() {
        return absentStatus;
    }

    public String getOnLeaveStatus() {
        return onLeaveStatus;
    }





    public String getStdRollNo() {
        return stdRollNo;
    }

    public String getStdId() {
        return  stdId;
    }


    public String getStdAdNo() {
        return stdAdNo;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }





    public boolean getDemoSelected() {
        return isDemoSelected;
    }

    public void setDemoSelected(boolean selected) {
        isDemoSelected = selected;
    }



    public void setPresentSelected(boolean selected) {
        isPresentSelected = selected;
    }


    public boolean getPresentSelected() {
        return isPresentSelected;
    }


    public boolean getAbsentSelected() {
        return isAbsentSelected;
    }


    public void setAbsentSelected(boolean selected) {
        isAbsentSelected = selected;
    }

    public boolean getLeaveSelected() {
        return isLeaveSelected;
    }

    public void setLeaveSelected(boolean selected) {
        isLeaveSelected = selected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public boolean getSelected() {
        return isSelected;
    }
}

