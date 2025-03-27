package com.example.demo_livestream.Api.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LiveStream_List_ModelResponse {

    @SerializedName("data")
    ArrayList<LiveStream_List_Model> listLivStream;

    public ArrayList<LiveStream_List_Model> getListLivStream() {
        if(listLivStream == null) {
            listLivStream = new ArrayList<>();
        }
        return listLivStream;
    }
}
