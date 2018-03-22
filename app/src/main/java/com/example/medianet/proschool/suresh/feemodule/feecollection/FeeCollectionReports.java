package com.example.medianet.proschool.suresh.feemodule.feecollection;

/**
 * Created by harish on 2/2/2018.
 */

class FeeCollectionReports {
    String stdName,totalFee,feePaid,discount,fine,balanceFee,due_date,feeType,paymentMode,feepaidDate;

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getFeepaidDate() {
        return feepaidDate;
    }

    public void setFeepaidDate(String feepaidDate) {
        this.feepaidDate = feepaidDate;
    }

    public FeeCollectionReports(String stdName, String feeType, String totalFee, String feePaid, String paymentMode, String discount, String fine, String due_date, String feepaidDate) {
        this.stdName = stdName;
        this.feeType = feeType;
        this.totalFee = totalFee;
        this.feePaid = feePaid;
        this.paymentMode = paymentMode;
        this.discount = discount;
        this.fine = fine;
        this.due_date = due_date;
        this.feepaidDate = feepaidDate;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public FeeCollectionReports(String stdName, String totalFee, String feePaid, String discount, String fine, String balanceFee, String due_date) {
        this.stdName = stdName;
        this.totalFee = totalFee;
        this.feePaid = feePaid;
        this.discount = discount;
        this.fine = fine;
        this.balanceFee = balanceFee;
        this.due_date = due_date;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getFeePaid() {
        return feePaid;
    }

    public void setFeePaid(String feePaid) {
        this.feePaid = feePaid;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public String getBalanceFee() {
        return balanceFee;
    }

    public void setBalanceFee(String balanceFee) {
        this.balanceFee = balanceFee;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
}
