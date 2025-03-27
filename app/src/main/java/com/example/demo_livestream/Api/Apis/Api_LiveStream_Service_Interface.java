package com.example.demo_livestream.Api.Apis;

import com.example.demo_livestream.Api.Model.LiveStream_Get_Detail_Model;
import com.example.demo_livestream.Api.Model.LiveStream_List_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api_LiveStream_Service_Interface {

    //LiveStream_List
    @Headers({"Accept-language: en","accept: */*", "Client-Type: Android", "sec-api: 123"})
    @GET("/LivestreamAPI/v2/my_livestream/all")
    Call<List<LiveStream_List_Model>> get_LiveStream_List(
            @Query("featureId") int featureId,
            @Query("page") int page,
            @Query("size") int size,
            @Query("userId") String userId
    );


    //LiveStream_List_Top
    @Headers({"Accept-language: en", "Client-Type: Android", "sec-api: 123"})
    @GET("/LivestreamAPI/v2/my_livestream/top")
    Call<LiveStream_List_Model> get_LiveStream_List_Top(
            @Query("msisdn") String msisdn,
            @Query("lastHashId") String lastHashId,
            @Query("page") int page,
            @Query("size") int size,
            @Query("timestamp") String timestamp,
            @Query("security") String security
    );

    //LiveStream_Detail
    @Headers({"Accept-language: en", "Client-Type: Android", "sec-api: 123"})
    @GET("/LivestreamAPI/v2/my_livestream/details")
    Call<LiveStream_Get_Detail_Model> get_LiveStream_Detail(
            @Query("livestream_id") String livestreamId,
            @Query("msisdn") String msisdn,
            @Query("timestamp") String timestamp,
            @Query("security") String security
    );



}
