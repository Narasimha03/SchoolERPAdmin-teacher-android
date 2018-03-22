package com.example.medianet.proschool.checkattendance;

/**
 * Created by hardik on 9/1/17.
 */
public class AttSubModel {

    private boolean isSelected;
    private boolean isSelectedAbsent;

    private boolean isSelectedLeave;

    private String presentAll;
    private String attStatus;

    public void setAttStatus(String attStatus) {
        this.attStatus = attStatus;
    }

    public String getAttStatus() {

        return attStatus;
    }

    private  String studentId,admNo,stdName,classId,dob,gender,rollNo,category;

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public AttSubModel() {

    }

    public AttSubModel(String studentId, String admNo, String stdName, String classId, String dob, String gender,String rollNo,String category) {
        this.studentId = studentId;
        this.admNo = admNo;
        this.stdName = stdName;
        this.classId = classId;
        this.dob = dob;
        this.gender = gender;
        this.rollNo=rollNo;
        this.category=category;
    }

    public String getStudentId() {

        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAdmNo() {
        return admNo;
    }

    public void setAdmNo(String admNo) {
        this.admNo = admNo;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPresentAll() {
        return presentAll;
    }

    public void setPresentAll(String presentAll) {
        this.presentAll = presentAll;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public boolean getSelectedAbsent() {
        return isSelectedAbsent;
    }

    public boolean getSelectedLeave() {
        return isSelectedLeave;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setAbsentSelected(boolean selected) {
        isSelectedAbsent = selected;
    }

    public void setLeaveSelected(boolean selected) {
        isSelectedLeave = selected;
    }
}
