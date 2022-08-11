package com.mor.morscanner.Model.GetAddOrderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mor.morscanner.Model.GetCheckBarcodeResponse.BarcodeDetails;

import java.util.ArrayList;

public class SetOrderJson {

    @SerializedName("OrderID")
    @Expose
    private Integer orderID;
    @SerializedName("PartyID")
    @Expose
    private Integer partyID;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("DeliveryDate")
    @Expose
    private String deliveryDate;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("Barcode")
    @Expose
    private ArrayList<BarcodeDetails> barcode = null;


    /**
     *
     * @param notes
     * @param id
     * @param partyID
     * @param deliveryDate
     * @param orderDate
     * @param barcode
     */
    public SetOrderJson(Integer orderID, Integer partyID, String orderDate, String deliveryDate, String notes, Integer id, ArrayList<BarcodeDetails> barcode) {
        super();
        this.orderID = orderID;
        this.partyID = partyID;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.notes = notes;
        this.id = id;
        this.barcode = barcode;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<BarcodeDetails> getBarcode() {
        return barcode;
    }

    public void setBarcode(ArrayList<BarcodeDetails> barcode) {
        this.barcode = barcode;
    }
}
