package com.example.medianet.proschool.teacher.teacherdashboard;

/**
 * Created by harish on 07-11-2017.
 */

public class DashboardCurriculumClass {

    private String ccPeriod;
    private String ccClass;

    public DashboardCurriculumClass() {
    }

    public DashboardCurriculumClass(String ccPeriod, String ccClass) {
        this.ccPeriod = ccPeriod;
        this.ccClass = ccClass;
    }

    public String getCcPeriod() {
        return ccPeriod;
    }

    public void setCcPeriod(String ccPeriod) {
        this.ccPeriod = ccPeriod;
    }

    public String getCcClass() {
        return ccClass;
    }

    public void setCcClass(String ccClass) {
        this.ccClass = ccClass;
    }

}
