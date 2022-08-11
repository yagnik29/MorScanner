package com.mor.morscanner.Model.GetPartyListResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddPartyJson {

    @SerializedName("PartyName")
    @Expose
    private String partyName;
    @SerializedName("ID")
    @Expose
    private Integer id;


    /**
     *
     * @param partyName
     * @param id
     */
    public AddPartyJson(String partyName, Integer id) {
        super();
        this.partyName = partyName;
        this.id = id;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
