package com.example.medianet.proschool.suresh.academics.lessiontracker.modelclass;

import java.util.ArrayList;

public class Subjects{
    String subjectName;
    ArrayList<Chapters> ChildList;

    public String getSubjectName() {
        return subjectName;
    }

    public String setSubjectName(String subjectName) {
        this.subjectName = subjectName;
        return subjectName;
    }

    public ArrayList<Chapters> getChildList() {
        return ChildList;
    }

    public void setChildList(ArrayList<Chapters> childList) {
        ChildList = childList;
    }


    // Here Constuctor and Getter Setter
}
