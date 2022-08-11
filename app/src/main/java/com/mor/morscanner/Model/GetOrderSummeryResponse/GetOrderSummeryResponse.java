package com.mor.morscanner.Model.GetOrderSummeryResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrderSummeryResponse {

    @SerializedName("OrderID")
    @Expose
    private Integer orderID;
    @SerializedName("PartyID")
    @Expose
    private Integer partyID;
    @SerializedName("PartyName")
    @Expose
    private String partyName;
    @SerializedName("ContactNumber")
    @Expose
    private String contactNumber;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("DeliveryDate")
    @Expose
    private String deliveryDate;
    @SerializedName("Notes")
    @Expose
    private String notes;

    /**
     *
     * @param notes
     * @param orderID
     * @param partyName
     * @param contactNumber
     * @param partyID
     * @param deliveryDate
     * @param orderDate
     */
    public GetOrderSummeryResponse(Integer orderID, Integer partyID, String partyName, String contactNumber, String orderDate, String deliveryDate, String notes) {
        super();
        this.orderID = orderID;
        this.partyID = partyID;
        this.partyName = partyName;
        this.contactNumber = contactNumber;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.notes = notes;
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

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
