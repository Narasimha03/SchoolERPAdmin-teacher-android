package com.example.medianet.proschool.suresh.academics.lessiontracker;

/**
 * Created by harish on 2/27/2018.
 */

public class LessionTrackerModel {
    String  subjectName,chapterName,noTopics,completedTopics,remTopics,chapterCode;

    public LessionTrackerModel(String subjectName, String chapterName, String noTopics, String completedTopics, String remTopics, String chapterCode) {
        this.subjectName = subjectName;
        this.chapterName = chapterName;
        this.noTopics = noTopics;
        this.completedTopics = completedTopics;
        this.remTopics = remTopics;
        this.chapterCode = chapterCode;
    }

    public LessionTrackerModel() {
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getNoTopics() {
        return noTopics;
    }

    public void setNoTopics(String noTopics) {
        this.noTopics = noTopics;
    }

    public String getCompletedTopics() {
        return completedTopics;
    }

    public void setCompletedTopics(String completedTopics) {
        this.completedTopics = completedTopics;
    }

    public String getRemTopics() {
        return remTopics;
    }

    public void setRemTopics(String remTopics) {
        this.remTopics = remTopics;
    }

    public String getChapterCode() {
        return chapterCode;
    }

    public void setChapterCode(String chapterCode) {
        this.chapterCode = chapterCode;
    }


}
