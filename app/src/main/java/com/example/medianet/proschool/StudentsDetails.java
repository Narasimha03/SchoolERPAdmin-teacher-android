package com.example.medianet.proschool;

import java.util.ArrayList;

/**
 * Created by JANI on 08-05-2017.
 */

public class StudentsDetails {

    String stdId;
    String stdAdNo;
    String stdName;
    String stdClass;
    String fatherName;

    public String getImageDisplay() {
        return imageDisplay;
    }

    public void setImageDisplay(String imageDisplay) {
        this.imageDisplay = imageDisplay;
    }

    String stdDob;
    String stdGender;
    String category;
    String mobile;
    String rte;
    String stdRollNo;
    String imageDisplay;

    private boolean isSelected;
    String present="Present";

    ArrayList<StudentsDetails> studentName;

    public ArrayList<StudentsDetails> getStudentName() {
        return studentName;
    }

    public void setStudentName(ArrayList<StudentsDetails> studentName) {
        this.studentName = studentName;
    }

    public StudentsDetails() {
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

    public StudentsDetails(String stdId, String stdAdNo, String stdName, String stdClass, String fatherName,
                           String stdDob, String stdGender, String category, String mobile, String rte, String stdRollNo,boolean isSelected,String imageDisplay) {
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
        this.imageDisplay=imageDisplay;
    }

    public String getStdRollNo() {
        return stdRollNo;
    }

    public void setStdRollNo(String stdRollNo) {
        this.stdRollNo = stdRollNo;
    }

    public String getStdId() {
        return stdId;
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
}
