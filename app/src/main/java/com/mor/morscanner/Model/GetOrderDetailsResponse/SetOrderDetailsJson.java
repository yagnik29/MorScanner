package com.mor.morscanner.Model.GetOrderDetailsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetOrderDetailsJson {

    @SerializedName("OrderID")
    @Expose
    private Integer orderID;
    @SerializedName("ID")
    @Expose
    private Integer id;



    /**
     *
     * @param orderID
     * @param id
     */
    public SetOrderDetailsJson(Integer orderID, Integer id) {
        super();
        this.orderID = orderID;
        this.id = id;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
