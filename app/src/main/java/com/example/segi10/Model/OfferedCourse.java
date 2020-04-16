package com.example.segi10.Model;

public class OfferedCourse {
    String courseId,courseName,lecturerName,slot1, slot2;

    //,slot1Start,slot1End,slot1Class,slot2Start,slot2End,slot2Class

    public OfferedCourse() {
    }

    public OfferedCourse(String courseId, String lecturerName, String slot1) {
        this.courseId = courseId;
        this.lecturerName = lecturerName;
        this.slot1 = slot1;
    }

    public OfferedCourse(String courseId,String courseName, String lecturerName, String slot1, String slot2) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.lecturerName = lecturerName;
        this.slot1 = slot1;
        this.slot2 = slot2;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getSlot1() {
        return slot1;
    }

    public void setSlot1(String slot1) {
        this.slot1 = slot1;
    }

    public String getSlot2() {
        return slot2;
    }

    public void setSlot2(String slot2) {
        this.slot2 = slot2;
    }
}
