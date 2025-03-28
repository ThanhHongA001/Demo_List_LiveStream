package com.example.demo_livestream.Api.Apis;

import com.example.demo_livestream.Api.response.RMLivestreamResponse;
import com.example.demo_livestream.Api.response.RmChannelResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api_Service_Interface {

    //LiveStream_List
    @Headers({"accept: */*", "Client-Type: Android", "sec-api: 123"})
    @GET("/LivestreamAPI/v2/my_livestream/all")
    Call<RMLivestreamResponse> get_LiveStream_List(
            @Query("featureId") int featureId,
            @Query("page") int page,
            @Query("size") int size,
            @Query("userId") String userId
    );

    @Headers({"accept: */*", "Client-Type: Android", "sec-api: 123"})
    @GET("/LivestreamAPI/v1/report_livestream")
    Call<RMLivestreamResponse> get_LiveStream_Report(

    );

    //Channel
    @Headers({"accept: */*", "Client-Type: Android", "sec-api: 123"})
    @GET("/LivestreamAPI/v1/channel/list/v2")
    Call<RmChannelResponse> get_LiveStream_ListChannel(
            @Query("page") int page,
            @Query("size") int size,
            @Query("userId") String userId
    );

    @Headers({"Accept-language: en", "accept: */*", "Client-Type: Android", "sec-api: 123"})
    @GET("/LivestreamAPI/v1/channel/{id}/follow")
    Call<RmChannelResponse> get_Follow_Channel(
            @Path("id") int id,
            @Query("msisdn") int msisdn,
            @Query("timestamp") int timestamp,
            @Query("security") int security
    );

    @Headers({"Accept-language: en", "accept: */*", "Client-Type: Android", "sec-api: 123"})
    @GET("/LivestreamAPI/v1/channel/{id}/unfollow")
    Call<RmChannelResponse> get_UnFollow_Channel(
            @Path("id") int id,
            @Query("msisdn") int msisdn,
            @Query("timestamp") int timestamp,
            @Query("security") int security
    );



}
