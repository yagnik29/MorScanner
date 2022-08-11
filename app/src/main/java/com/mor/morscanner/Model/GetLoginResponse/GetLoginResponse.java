package com.mor.morscanner.Model.GetLoginResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLoginResponse {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("ID")
    @Expose
    private Integer id;


    /**
     *
     * @param id
     * @param message
     * @param userID
     */
    public GetLoginResponse(String message, String userID, Integer id) {
        super();
        this.message = message;
        this.userID = userID;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
