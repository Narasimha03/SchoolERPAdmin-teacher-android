package com.example.medianet.proschool.suresh.examination;

/**
 * Created by harish on 2/23/2018.
 */

public class BulkMarksModel {


    public String getStudentId() {
        return studentId;
    }

    public BulkMarksModel(String studentId, String studentName, String studentMarks) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentMarks = studentMarks;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentMarks() {
        return studentMarks;
    }

    public void setStudentMarks(String studentMarks) {
        this.studentMarks = studentMarks;
    }

    String studentId,studentName,studentMarks;
}
