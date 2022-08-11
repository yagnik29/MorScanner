package com.mor.morscanner.Model.GetCheckBarcodeResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetCheckBarcodeJson {

    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("Barcode")
    @Expose
    private String barcode;



    /**
     *
     * @param id
     * @param barcode
     */
    public SetCheckBarcodeJson(Integer id, String barcode) {
        super();
        this.id = id;
        this.barcode = barcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
