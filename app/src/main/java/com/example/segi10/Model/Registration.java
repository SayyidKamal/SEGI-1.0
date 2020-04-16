package com.example.segi10.Model;

public class Registration {

    private String regID;
    private String studID;
    private String regDate;
    private String courseCode;
    private String mentorID;
    private String mentorApproval;
    private String bursaryID;
    private String bursaryApproval;
    private String ioID;
    private String ioApproval;

    public Registration(String regID, String studID, String regDate, String courseCode, String mentorID, String mentorApproval, String bursaryID, String bursaryApproval, String ioID, String ioApproval) {
        this.regID = regID;
        this.studID = studID;
        this.regDate = regDate;
        this.courseCode = courseCode;
        this.mentorID = mentorID;
        this.mentorApproval = mentorApproval;
        this.bursaryID = bursaryID;
        this.bursaryApproval = bursaryApproval;
        this.ioID = ioID;
        this.ioApproval = ioApproval;
    }

    public Registration(String regID, String studID, String regDate, String courseCode) {
        this.regID = regID;
        this.studID = studID;
        this.regDate = regDate;
        this.courseCode = courseCode;
    }

    public String getRegID() {
        return regID;
    }

    public void setRegID(String regID) {
        this.regID = regID;
    }

    public String getStudID() {
        return studID;
    }

    public void setStudID(String studID) {
        this.studID = studID;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getMentorID() {
        return mentorID;
    }

    public void setMentorID(String mentorID) {
        this.mentorID = mentorID;
    }

    public String getMentorApproval() {
        return mentorApproval;
    }

    public void setMentorApproval(String mentorApproval) {
        this.mentorApproval = mentorApproval;
    }

    public String getBursaryID() {
        return bursaryID;
    }

    public void setBursaryID(String bursaryID) {
        this.bursaryID = bursaryID;
    }

    public String getBursaryApproval() {
        return bursaryApproval;
    }

    public void setBursaryApproval(String bursaryApproval) {
        this.bursaryApproval = bursaryApproval;
    }

    public String getIoID() {
        return ioID;
    }

    public void setIoID(String ioID) {
        this.ioID = ioID;
    }

    public String getIoApproval() {
        return ioApproval;
    }

    public void setIoApproval(String ioApproval) {
        this.ioApproval = ioApproval;
    }

    public Registration() {
    }

}


