package com.sabbir.admin.demoproject.model;

import android.graphics.Bitmap;

/**
 * Created by Admin on 1/21/2018.
 */

public class UserData {

    private String userName;
    private String userEmail;
    private String userPhn;
    private String userAge;
    private Bitmap userImg;

    public UserData(String userName,String userPhn, String userEmail, String userAge, Bitmap userImg) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhn = userPhn;
        this.userAge = userAge;
        this.userImg = userImg;
    }

    public UserData(String userName, String userEmail, String userPhn, String userAge) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhn = userPhn;
        this.userAge = userAge;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhn() {
        return userPhn;
    }

    public void setUserPhn(String userPhn) {
        this.userPhn = userPhn;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public Bitmap getUserImg() {
        return userImg;
    }

    public void setUserImg(Bitmap userImg) {
        this.userImg = userImg;
    }
}
