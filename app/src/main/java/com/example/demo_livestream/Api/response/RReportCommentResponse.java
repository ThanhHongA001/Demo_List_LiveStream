package com.example.demo_livestream.Api.response;

import com.example.demo_livestream.Api.model.RReportComment;
import com.google.gson.annotations.SerializedName;
//import com.viettel.mocha.rmlivestream.model.RReportComment;

public class RReportCommentResponse {
    @SerializedName("data")
    RReportComment data;

    public RReportComment getData() {
        return data;
    }

    public void setData(RReportComment data) {
        this.data = data;
    }
}
