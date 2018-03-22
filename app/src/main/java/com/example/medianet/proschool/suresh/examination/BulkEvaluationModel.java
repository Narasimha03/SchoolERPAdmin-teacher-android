package com.example.medianet.proschool.suresh.examination;

/**
 * Created by harish on 2/5/2018.
 */

public class BulkEvaluationModel {
    public BulkEvaluationModel(String stdName, String maxMarks, String stdId) {
        this.stdName = stdName;
        this.maxMarks = maxMarks;
        this.stdId = stdId;
    }

    public BulkEvaluationModel() {
    }

    String stdName,maxMarks,stdId,readEdValue;

    public String setReadEdValue(String readEdValue) {
        this.readEdValue = readEdValue;
        return readEdValue;
    }

    public String getReadEdValue() {

        return readEdValue;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public String getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(String maxMarks) {
        this.maxMarks = maxMarks;
    }

    public String getStdId() {
        return stdId;
    }

    public void setStdId(String stdId) {
        this.stdId = stdId;
    }
}
