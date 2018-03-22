package com.example.medianet.proschool.suresh.studentprofile.backtasks;

/**
 * Created by harish on 12/27/2017.
 */

public class StudentProfileModel {

    String stdId;
    String stdAdNo;
    String stdName;
    String stdClass;
    String stdGender;
    String category;
    String mobile;
    String rte;
    String stdRollNo;
    String image;

    public StudentProfileModel(String stdId, String stdAdNo, String stdName, String stdClass, String fatherName, String stdDob, String stdGender, String category, String mobile, String rte, String stdRollNo, String image) {
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
        this.image = image;
    }

    String fatherName;
    String stdDob;

    public void setStdId(String stdId) {
        this.stdId = stdId;
    }

    public void setStdAdNo(String stdAdNo) {
        this.stdAdNo = stdAdNo;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public void setStdClass(String stdClass) {
        this.stdClass = stdClass;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setStdDob(String stdDob) {
        this.stdDob = stdDob;
    }

    public void setStdGender(String stdGender) {
        this.stdGender = stdGender;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setRte(String rte) {
        this.rte = rte;
    }

    public void setStdRollNo(String stdRollNo) {
        this.stdRollNo = stdRollNo;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
