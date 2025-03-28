package com.example.demo_livestream.Api.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RReportComment {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("msisdn")
    @Expose
    private String msisdn;
    @SerializedName("cmtId")
    @Expose
    private String commentId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("problem")
    @Expose
    private String problem;
    @SerializedName("date")
    @Expose
    private String Date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
