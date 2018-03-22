package com.example.medianet.proschool.suresh.admindashboard;

/**
 * Created by harish on 07-11-2017.
 */

public class AdminSectionsClass {

    private String section;
    private String subOne;
    private String subTwo;
    private String subThree;
    private String subFour;
    private String subFive;
    private String subSix;
    private String subSeven;


    public AdminSectionsClass() {
    }

    public AdminSectionsClass(String section, String subOne, String subTwo, String subThree,
                              String subFour, String subFive, String subSix, String subSeven) {
        this.section = section;
        this.subOne = subOne;
        this.subTwo = subTwo;
        this.subThree = subThree;
        this.subFour = subFour;
        this.subFive = subFive;
        this.subSix = subSix;
        this.subSeven = subSeven;
    }


    public String getSubOne() {
        return subOne;
    }

    public void setSubOne(String subOne) {
        this.subOne = subOne;
    }

    public String getSubTwo() {
        return subTwo;
    }

    public void setSubTwo(String subTwo) {
        this.subTwo = subTwo;
    }

    public String getSubThree() {
        return subThree;
    }

    public void setSubThree(String subThree) {
        this.subThree = subThree;
    }

    public String getSubFour() {
        return subFour;
    }

    public void setSubFour(String subFour) {
        this.subFour = subFour;
    }

    public String getSubFive() {
        return subFive;
    }

    public void setSubFive(String subFive) {
        this.subFive = subFive;
    }

    public String getSubSix() {
        return subSix;
    }

    public void setSubSix(String subSix) {
        this.subSix = subSix;
    }

    public String getSubSeven() {
        return subSeven;
    }

    public void setSubSeven(String subSeven) {
        this.subSeven = subSeven;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
