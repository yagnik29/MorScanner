package com.mor.morscanner.Model.GetCheckBarcodeResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCheckBarcodeResponse {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Barcode")
    @Expose
    private BarcodeDetails barcodeDetails;



    /**
     *
     * @param message
     * @param barcodeDetails
     */
    public GetCheckBarcodeResponse(String message, BarcodeDetails barcodeDetails) {
        super();
        this.message = message;
        this.barcodeDetails = barcodeDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BarcodeDetails getBarcodeDetails() {
        return barcodeDetails;
    }

    public void setBarcodeDetails(BarcodeDetails barcodeDetails) {
        this.barcodeDetails = barcodeDetails;
    }

}
