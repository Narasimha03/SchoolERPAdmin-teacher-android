package com.example.medianet.proschool.suresh.academics.lessiontracker.modelclass;

public class Chapters
{
    private String percent;

    private String remaining_topics;

    private String completed_topics;

    private String chapterName;

    private String no_of_topics;

    private String chapter_code;

    private String remaining_percent;

    public String getPercent ()
    {
        return percent;
    }

    public void setPercent (String percent)
    {
        this.percent = percent;
    }

    public String getRemaining_topics ()
    {
        return remaining_topics;
    }

    public void setRemaining_topics (String remaining_topics)
    {
        this.remaining_topics = remaining_topics;
    }

    public String getCompleted_topics ()
    {
        return completed_topics;
    }

    public void setCompleted_topics (String completed_topics)
    {
        this.completed_topics = completed_topics;
    }

    public String getChapterName ()
    {
        return chapterName;
    }

    public void setChapterName (String chapterName)
    {
        this.chapterName = chapterName;
    }

    public String getNo_of_topics ()
    {
        return no_of_topics;
    }

    public void setNo_of_topics (String no_of_topics)
    {
        this.no_of_topics = no_of_topics;
    }

    public String getChapter_code ()
    {
        return chapter_code;
    }

    public void setChapter_code (String chapter_code)
    {
        this.chapter_code = chapter_code;
    }

    public String getRemaining_percent ()
    {
        return remaining_percent;
    }

    public void setRemaining_percent (String remaining_percent)
    {
        this.remaining_percent = remaining_percent;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [percent = "+percent+", remaining_topics = "+remaining_topics+", completed_topics = "+completed_topics+", chapterName = "+chapterName+", no_of_topics = "+no_of_topics+", chapter_code = "+chapter_code+", remaining_percent = "+remaining_percent+"]";
    }
}