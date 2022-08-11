package com.mor.morscanner.Model.GetOrderSummeryResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetOrderSummeryJson {

    @SerializedName("OrderID")
    @Expose
    private Integer orderID;
    @SerializedName("PartyID")
    @Expose
    private Integer partyID;
    @SerializedName("FromOrderDate")
    @Expose
    private String fromOrderDate;
    @SerializedName("ToOrderDate")
    @Expose
    private String toOrderDate;
    @SerializedName("FromDeliveryDate")
    @Expose
    private String fromDeliveryDate;
    @SerializedName("ToDeliveryDate")
    @Expose
    private String toDeliveryDate;



    /**
     *
     * @param fromOrderDate
     * @param orderID
     * @param toDeliveryDate
     * @param partyID
     * @param toOrderDate
     * @param fromDeliveryDate
     */
    public SetOrderSummeryJson(Integer orderID, Integer partyID, String fromOrderDate, String toOrderDate, String fromDeliveryDate, String toDeliveryDate) {
        super();
        this.orderID = orderID;
        this.partyID = partyID;
        this.fromOrderDate = fromOrderDate;
        this.toOrderDate = toOrderDate;
        this.fromDeliveryDate = fromDeliveryDate;
        this.toDeliveryDate = toDeliveryDate;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getPartyID() {
        return partyID;
    }

    public void setPartyID(Integer partyID) {
        this.partyID = partyID;
    }

    public String getFromOrderDate() {
        return fromOrderDate;
    }

    public void setFromOrderDate(String fromOrderDate) {
        this.fromOrderDate = fromOrderDate;
    }

    public String getToOrderDate() {
        return toOrderDate;
    }

    public void setToOrderDate(String toOrderDate) {
        this.toOrderDate = toOrderDate;
    }

    public String getFromDeliveryDate() {
        return fromDeliveryDate;
    }

    public void setFromDeliveryDate(String fromDeliveryDate) {
        this.fromDeliveryDate = fromDeliveryDate;
    }

    public String getToDeliveryDate() {
        return toDeliveryDate;
    }

    public void setToDeliveryDate(String toDeliveryDate) {
        this.toDeliveryDate = toDeliveryDate;
    }
}
