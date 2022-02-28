package com.example.firebasecrud.model;

public class CallList {
    private String userID;
    private String userName;
    private String date;
    private String profile;
    private String callType;

    public CallList() {
    }

    public CallList(String userID, String userName, String date, String profile, String callType) {
        this.userID = userID;
        this.userName = userName;
        this.date = date;
        this.profile = profile;
        this.callType = callType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }
}
