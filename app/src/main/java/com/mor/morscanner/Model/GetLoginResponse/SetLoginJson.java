package com.mor.morscanner.Model.GetLoginResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetLoginJson {

    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("Password")
    @Expose
    private String password;



    /**
     *
     * @param password
     * @param userID
     */
    public SetLoginJson(String userID, String password) {
        super();
        this.userID = userID;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
