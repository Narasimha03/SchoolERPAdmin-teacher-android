package com.example.medianet.proschool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by JANI on 21-04-2017.
 */

public class ExpandableMenuDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> dashboard = new ArrayList<String>();

        List<String> studentInfo = new ArrayList<String>();
        studentInfo.add("Students");
        studentInfo.add("Student Admissions");
        studentInfo.add("Student Details");

        List<String> empManagement = new ArrayList<String>();
        empManagement.add("Employee");
        empManagement.add("Employee Details");
        empManagement.add("Employee Attendance");

        List<String> attendance = new ArrayList<String>();
        attendance.add("Student Attendance");
        attendance.add("Employee Attendance");
        attendance.add("Attendance Reports");

        List<String> academics = new ArrayList<String>();
        academics.add("Subjects");
        academics.add("Chapters");
        academics.add("Assign Subjects");

        List<String> assignments = new ArrayList<String>();

        List<String> examinations = new ArrayList<String>();
        examinations.add("Exam Schedule");
        examinations.add("Exam Papers");
        examinations.add("Evaluations");

        List<String> timeTable = new ArrayList<String>();
        timeTable.add("Class-wise");
        timeTable.add("Teacher-wise");
        timeTable.add("School Events");
        timeTable.add("Online NoticeBoard");

        List<String> library = new ArrayList<String>();
        library.add("Library Rules");
        library.add("Add Book");
        library.add("Book List");

        List<String> transport = new ArrayList<String>();
        transport.add("Add Station");
        transport.add("Add Vehicle");
        transport.add("Add Bus Route");
        transport.add("Route Geolocation");

        expandableListDetail.put("Dashboard", dashboard);
        expandableListDetail.put("Student Information", studentInfo);
        expandableListDetail.put("Employee Management", empManagement);
        expandableListDetail.put("Attendance", attendance);
        expandableListDetail.put("Academics", academics);
        expandableListDetail.put("Assignments", assignments);
        expandableListDetail.put("Examinations", examinations);
        expandableListDetail.put("Timetable", timeTable);
        expandableListDetail.put("Library", library);
        expandableListDetail.put("Transport", transport);

        return expandableListDetail;
    }
}
