package com.mor.morscanner.Model.GetPartyListResponse;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPartyListResponse {

    @SerializedName("PartyID")
    @Expose
    private Integer partyID;
    @SerializedName("PartyName")
    @Expose
    private String partyName;


    /**
     *
     * @param partyName
     * @param partyID
     */
    public GetPartyListResponse(Integer partyID, String partyName) {
        super();
        this.partyID = partyID;
        this.partyName = partyName;
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

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
