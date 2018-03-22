package com.example.medianet.proschool.teacher.teacherdashboard;

/**
 * Created by harish on 07-11-2017.
 */

public class DashboardAssignmentClass {

    private String subjectText;
    private String assignmentText;

    public DashboardAssignmentClass() {
    }

    public DashboardAssignmentClass(String subjectText, String assignmentText) {
        this.subjectText = subjectText;
        this.assignmentText = assignmentText;
    }

    public String getSubjectText() {
        return subjectText;
    }

    public void setSubjectText(String subjectText) {
        this.assignmentText = subjectText;
    }

    public String getAssignmentText() {
        return assignmentText;
    }

    public void setAssignmentText(String asText) {
        this.assignmentText = asText;
    }

}
