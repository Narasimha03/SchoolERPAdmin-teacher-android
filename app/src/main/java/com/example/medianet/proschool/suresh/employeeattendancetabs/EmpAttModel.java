package com.example.medianet.proschool.suresh.employeeattendancetabs;
/**
 * Created by USER on 11/27/2017.
 */

import java.util.ArrayList;

/**
 * Created by JANI on 08-05-2017.
 */

public class EmpAttModel {

    String empId, empName, EmployeeType, empGender, attendanceStatus;

    public String getEmpId() {
        return empId;
    }

    public EmpAttModel(String empId, String empName, String employeeType, String empGender, String attendanceStatus) {
        this.empId = empId;
        this.empName = empName;
        EmployeeType = employeeType;
        this.empGender = empGender;
        this.attendanceStatus = attendanceStatus;
    }

    public void setEmpId(String empId) {

        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmployeeType() {
        return EmployeeType;
    }

    public void setEmployeeType(String employeeType) {
        EmployeeType = employeeType;
    }

    public String getEmpGender() {
        return empGender;
    }

    public void setEmpGender(String empGender) {
        this.empGender = empGender;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
}