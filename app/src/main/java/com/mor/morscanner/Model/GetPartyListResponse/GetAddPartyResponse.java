package com.mor.morscanner.Model.GetPartyListResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAddPartyResponse {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;



    /**
     *
     * @param message
     * @param statusCode
     */
    public GetAddPartyResponse(String message, Integer statusCode) {
        super();
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}
