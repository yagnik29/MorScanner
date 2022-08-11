package com.mor.morscanner.Model.GetCheckBarcodeResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BarcodeDetails {

    @SerializedName("OrderID")
    @Expose
    private Integer orderId;
    @SerializedName("Barcode")
    @Expose
    private String barcode;
    @SerializedName("DesignCategory")
    @Expose
    private String designCategory;
    @SerializedName("DesignNumber")
    @Expose
    private String designNumber;
    @SerializedName("DiamondPCS")
    @Expose
    private Integer diamondPCS;
    @SerializedName("GoldCategory")
    @Expose
    private Integer goldCategory;
    @SerializedName("GoldWGT")
    @Expose
    private Double goldWGT;



    /**
     *
     * @param designNumber
     * @param goldWGT
     * @param goldCategory
     * @param designCategory
     * @param barcode
     * @param diamondPCS
     */
    public BarcodeDetails(Integer orderId, String barcode, String designCategory, String designNumber, Integer diamondPCS, Integer goldCategory, Double goldWGT) {
        super();
        this.orderId = orderId;
        this.barcode = barcode;
        this.designCategory = designCategory;
        this.designNumber = designNumber;
        this.diamondPCS = diamondPCS;
        this.goldCategory = goldCategory;
        this.goldWGT = goldWGT;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDesignCategory() {
        return designCategory;
    }

    public void setDesignCategory(String designCategory) {
        this.designCategory = designCategory;
    }

    public String getDesignNumber() {
        return designNumber;
    }

    public void setDesignNumber(String designNumber) {
        this.designNumber = designNumber;
    }

    public Integer getDiamondPCS() {
        return diamondPCS;
    }

    public void setDiamondPCS(Integer diamondPCS) {
        this.diamondPCS = diamondPCS;
    }

    public Integer getGoldCategory() {
        return goldCategory;
    }

    public void setGoldCategory(Integer goldCategory) {
        this.goldCategory = goldCategory;
    }

    public Double getGoldWGT() {
        return goldWGT;
    }

    public void setGoldWGT(Double goldWGT) {
        this.goldWGT = goldWGT;
    }

}
