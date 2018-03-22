package com.example.medianet.proschool.suresh.academics.lessiontracker.modelclass;

public class LessionTracker
{
    private Subjects[] subjects;

    public Subjects[] getSubjects ()
    {
        return subjects;
    }

    public void setSubjects (Subjects[] subjects)
    {
        this.subjects = subjects;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [subjects = "+subjects+"]";
    }
}