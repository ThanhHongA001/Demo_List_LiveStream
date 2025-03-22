package com.example.demo_livestream.Api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Actions implements Serializable {
    @SerializedName("msisdn")
    @Expose
    private String msisdn;
    @SerializedName("status")
    @Expose
    private long status;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }
}
