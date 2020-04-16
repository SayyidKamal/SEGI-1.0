package com.example.segi10.Model;

import com.rengwuxian.materialedittext.MaterialEditText;

public class User {

    private String id;
    private String name;
    private String ic_Passport;
    private String segiID;
    private String role;
    private String email;
    private String phoneNo;
    private String address;
    private String imageURL;
    private String status;
    private String search;
    private String intakeDate;

    public User(String id, String name, String ic_Passport, String segiID, String role, String email,
                String phoneNo, String address, String imageURL, String status, String search, String intakeDate) {
        this.id = id;
        this.name = name;
        this.ic_Passport = ic_Passport;
        this.segiID = segiID;
        this.role = role;
        this.email = email;
        this.phoneNo = phoneNo;
        this.address = address;
        this.imageURL = imageURL;
        this.status = status;
        this.search = search;
        this.intakeDate = intakeDate;
    }

    public User(String id, String name, String ic_Passport, String segiID, String email, String phoneNo, String address , String search) {
        this.id = id;
        this.name = name;
        this.ic_Passport = ic_Passport;
        this.segiID = segiID;
        this.email = email;
        this.phoneNo = phoneNo;
        this.address = address;
        this.search = search;
    }

    public User(String id, String imageURL, String status, String search) {
        this.id = id;
        this.imageURL = imageURL;
        this.status = status;
        this.search = search;
    }

    public User() {
    }

    public void setIntakeDate(String intakeDate) {
        this.intakeDate = intakeDate;
    }

    public String getIntakeDate() {
        return intakeDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIc_Passport() {
        return ic_Passport;
    }

    public void setIc_Passport(String ic_Passport) {
        this.ic_Passport = ic_Passport;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    public void setSegiID(String segiID) {
        this.segiID = segiID;
    }

    public String getSegiID() {
        return segiID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
