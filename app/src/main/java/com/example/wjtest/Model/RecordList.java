package com.example.wjtest.Model;

public class RecordList {
    private String activity, minutes, calories,points, userID, date;

    public RecordList(String activity, String minutes, String calories, String points, String userID, String date){
        this.activity = activity;
        this.minutes = minutes;
        this.calories = calories;
        this.points = points;
        this.userID = userID;
        this.date = date;

    }

    public RecordList(){

    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
