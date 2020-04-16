package com.example.segi10.Model;

public class Course {

    private String faculty, code , name, creditHours, specialty;

    public Course(){
    }

    public Course(String faculty, String code, String name, String credits, String specialty) {
        this.faculty = faculty;
        this.code = code;
        this.name = name;
        creditHours = credits;
        this.specialty = specialty;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
        this.creditHours = creditHours;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}


