package com.example.wjtest;

public class User {
    public String name, email, gender, dateOfBirth, contactNo, points;
    public boolean isActive;

    public User(String name, String email, String gender, String dateOfBirth, String contactNo, String points, boolean isActive){
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.contactNo = contactNo;
        this.points = points;
        this.isActive = isActive;
    }

}
