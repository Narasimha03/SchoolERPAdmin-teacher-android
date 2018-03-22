package com.example.medianet.proschool.suresh.feemodule;

/**
 * Created by USER on 11/13/2017.
 */

public class FeeType {
    private String className, feeCategory, feeType,id, feeAmount;



    public FeeType(String className, String feeType, String feeAmount) {
        this.className = className;
        this.feeType = feeType;
        this.feeAmount = feeAmount;
    }

    public String getFeeCategory() {
        return feeCategory;
    }

    public void setFeeCategory(String feeCategory) {
        this.feeCategory = feeCategory;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeType) {
        this.feeAmount = feeAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
