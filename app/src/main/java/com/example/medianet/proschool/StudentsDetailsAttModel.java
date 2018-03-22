package com.example.medianet.proschool; /**
 * Created by USER on 11/27/2017.
 */

import java.util.ArrayList;

/**
 * Created by JANI on 08-05-2017.
 */

public class StudentsDetailsAttModel {

    String stdId, stdAdNo, stdName, stdClass, fatherName, stdDob, stdGender, category, mobile, rte, stdRollNo,presentStatus,absentStatus ,onLeaveStatus,stdCls,stdAttendance,demo;


    private boolean isSelected;
    private boolean isDemoSelected;
    private boolean isAbsentSelected;
    private boolean isLeaveSelected;



    private boolean isSelectedPresent;
    private boolean isSelectedAbsent;
    private boolean isSelectedLeave;
    public String getPresentStatus() {
        return presentStatus;
    }

    public StudentsDetailsAttModel(String stdName, String stdAdNo, String stdRollNo, String stdCls, String stdGender, String stdDob, String stdAttendance) {
        this.stdName = stdName;
        this.stdAdNo = stdAdNo;
        this.stdRollNo = stdRollNo;
        this.stdCls = stdCls;
        this.stdGender = stdGender;
        this.stdDob = stdDob;
        this.stdAttendance=stdAttendance;
    }

    public StudentsDetailsAttModel(String stdId, String stdAdNo, String stdName, String stdClass, String fatherName,
                                   String stdDob, String stdGender, String category, String mobile, String rte, String stdRollNo, boolean isSelected, boolean isSelectedPresent, boolean isSelectedAbsent, String  demo,String presentStatus, String absentStatus , String onLeaveStatus) {
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
        this.isSelected = isSelected;
        this.isSelected=isSelectedPresent;
        this.isSelectedAbsent=isSelectedAbsent;
        this.demo=demo;
        this.presentStatus=presentStatus;
        this.absentStatus=absentStatus;
        this.onLeaveStatus=onLeaveStatus;
    }

    public String getStdCls() {
        return stdCls;

    }

    public void setStdCls(String stdCls) {
        this.stdCls = stdCls;
    }

    public String getStdAttendance() {
        return stdAttendance;
    }

    public void setStdAttendance(String stdAttendance) {
        this.stdAttendance = stdAttendance;
    }

    public void setPresentStatus(String presentStatus) {
        this.presentStatus = presentStatus;
    }

    public String getAbsentStatus() {
        return absentStatus;
    }

    public void setAbsentStatus(String absentStatus) {
        this.absentStatus = absentStatus;
    }

    public String getOnLeaveStatus() {
        return onLeaveStatus;
    }

    public void setOnLeaveStatus(String onLeaveStatus) {
        this.onLeaveStatus = onLeaveStatus;
    }



    public boolean isSelectedPresent() {
        return isSelectedPresent;
    }

    public void setSelectedPresent(boolean selectedPresent) {
        isSelectedPresent = selectedPresent;
    }

    public boolean isSelectedAbsent() {
        return isSelectedAbsent;
    }

    public void setSelectedAbsent(boolean selectedAbsent) {
        isSelectedAbsent = selectedAbsent;
    }

    public boolean isSelectedLeave() {
        return isSelectedLeave;
    }

    public void setSelectedLeave(boolean selectedLeave) {
        isSelectedLeave = selectedLeave;
    }



    ArrayList<StudentsDetailsAttModel> studentName;

    public ArrayList<StudentsDetailsAttModel> getStudentName() {
        return studentName;
    }

    public void setStudentName(ArrayList<StudentsDetailsAttModel> studentName) {
        this.studentName = studentName;
    }

    public StudentsDetailsAttModel() {
    }

    public boolean isSelected() {
        return isSelected;
    }
    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }



    public String getStdRollNo() {
        return stdRollNo;
    }

    public void setStdRollNo(String stdRollNo) {
        this.stdRollNo = stdRollNo;
    }

    public String getStdId() {
        return  stdId;
    }

    public void setStdId(String stdId) {
        this.stdId = stdId;
    }

    public String getStdAdNo() {
        return stdAdNo;
    }

    public void setStdAdNo(String stdAdNo) {
        this.stdAdNo = stdAdNo;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public String getStdClass() {
        return stdClass;
    }

    public void setStdClass(String stdClass) {
        this.stdClass = stdClass;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getStdDob() {
        return stdDob;
    }

    public void setStdDob(String stdDob) {
        this.stdDob = stdDob;
    }

    public String getStdGender() {
        return stdGender;
    }

    public void setStdGender(String stdGender) {
        this.stdGender = stdGender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRte() {
        return rte;
    }

    public void setRte(String rte) {
        this.rte = rte;
    }



    public boolean getDemoSelected() {
        return isDemoSelected;
    }

    public void setDemoSelected(boolean selected) {
        isDemoSelected = selected;
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

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }
}

