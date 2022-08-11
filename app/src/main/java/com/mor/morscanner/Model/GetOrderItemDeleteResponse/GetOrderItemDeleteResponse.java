package com.mor.morscanner.Model.GetOrderItemDeleteResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrderItemDeleteResponse {

    @SerializedName("OrderID")
    @Expose
    private Integer orderID;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;



    /**
     *
     * @param orderID
     * @param message
     * @param statusCode
     */
    public GetOrderItemDeleteResponse(Integer orderID, String message, Integer statusCode) {
        super();
        this.orderID = orderID;
        this.message = message;
        this.statusCode = statusCode;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
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
