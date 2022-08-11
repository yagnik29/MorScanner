package com.mor.morscanner.Model.GetOrderItemDeleteResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetOrderItemDeleteJson {
    @SerializedName("OrderID")
    @Expose
    private Integer orderID;
    @SerializedName("Barcode")
    @Expose
    private String barcode;
    @SerializedName("ID")
    @Expose
    private Integer id;



    /**
     *
     * @param orderID
     * @param id
     * @param barcode
     */
    public SetOrderItemDeleteJson(Integer orderID, String barcode, Integer id) {
        super();
        this.orderID = orderID;
        this.barcode = barcode;
        this.id = id;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
