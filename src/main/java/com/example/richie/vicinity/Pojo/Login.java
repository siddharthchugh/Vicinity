package com.example.richie.vicinity.Pojo;

/**
 * Created by Richie on 28-05-2016.
 */
public class Login {

    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    private String username;
    public String getMailAddress() {
        return mailAddress;
    }



    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPositionid() {
        return positionid;
    }

    public void setPositionid(String positionid) {
        this.positionid = positionid;
    }

    private String mailAddress;
    private String positionid;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
