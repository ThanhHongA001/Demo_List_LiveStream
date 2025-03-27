package com.example.demo_livestream.Api.Apis;

import android.util.Log;

import com.example.demo_livestream.Api.APICallBack;
import com.example.demo_livestream.Api.Http.HttpsTrustManager;
import com.example.demo_livestream.Api.Model.LiveStream_Get_Detail_Model;
import com.example.demo_livestream.Api.Model.LiveStream_List_Model;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api_LiveStream_Retrofit_Client_Instance {
    private static final String TAG = Api_LiveStream_Retrofit_Client_Instance.class.getSimpleName();
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = HttpsTrustManager.getUnsafeOkHttpClient();
            retrofit = new Retrofit.Builder()
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(Api_Live.Domain_LiveStream)                    // Sử dụng domain chính của API livestream
                    .addConverterFactory(GsonConverterFactory.create())     // Chuyển đổi JSON sang Object
                    .build();
        }
        return retrofit;
    }


    public void getLiveStream_List(int featureId, int page, int size, String userId, APICallBack<List<LiveStream_List_Model>> callBack) {
        Api_LiveStream_Service_Interface service = getClient().create(Api_LiveStream_Service_Interface.class);

        // Gửi request lấy danh sách livestream
        Call<List<LiveStream_List_Model>> call = service.get_LiveStream_List(featureId, page, size, userId);

        Log.d(TAG, "getLiveStream_List: Request - " + call.request().toString());

        call.enqueue(new Callback<List<LiveStream_List_Model>>() {
            @Override
            public void onResponse(Call<List<LiveStream_List_Model>> call, Response<List<LiveStream_List_Model>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Response: " + response.body().toString());
                    callBack.onResponse(response);
                } else {
                    Log.e(TAG, "Response Error: " + response.errorBody());
                    callBack.onError(new Exception("Response Error"));
                }
            }

            @Override
            public void onFailure(Call<List<LiveStream_List_Model>> call, Throwable t) {
                Log.e(TAG, "API Call Failed", t);
                callBack.onError(t);
            }
        });
    }


    // Lấy danh sách LiveStream Top
    public void getLiveStream_List_Top(String msisdn, String lastHashId, String token, APICallBack<LiveStream_List_Model> callBack) {
        long timestamp = System.currentTimeMillis();
        String security = generateSecurityToken();

        Api_LiveStream_Service_Interface service = getClient().create(Api_LiveStream_Service_Interface.class);
        Call<LiveStream_List_Model> call = service.get_LiveStream_List_Top(msisdn, lastHashId, 1, 20, String.valueOf(timestamp), security);

        Log.d(TAG, "getLiveStream_List_Top: Request - " + call.request().toString());

        call.enqueue(new Callback<LiveStream_List_Model>() {
            @Override
            public void onResponse(Call<LiveStream_List_Model> call, Response<LiveStream_List_Model> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Response: " + response.body().toString());
                    callBack.onResponse(response);
                } else {
                    Log.e(TAG, "Response Error: " + response.errorBody());
                    callBack.onError(new Exception("Response Error"));
                }
            }

            @Override
            public void onFailure(Call<LiveStream_List_Model> call, Throwable t) {
                Log.e(TAG, "API Call Failed", t);
                callBack.onError(t);
            }
        });
    }
    // Lấy chi tiết LiveStream
    public void getLiveStream_Detail(String livestreamId, String msisdn, String token, APICallBack<LiveStream_Get_Detail_Model> callBack) {
        long timestamp = System.currentTimeMillis();
        String security = generateSecurityToken();

        Api_LiveStream_Service_Interface service = getClient().create(Api_LiveStream_Service_Interface.class);
        Call<LiveStream_Get_Detail_Model> call = service.get_LiveStream_Detail(livestreamId, msisdn, String.valueOf(timestamp), security);

        Log.d(TAG, "getLiveStream_Detail: Request - " + call.request().toString());

        call.enqueue(new Callback<LiveStream_Get_Detail_Model>() {
            @Override
            public void onResponse(Call<LiveStream_Get_Detail_Model> call, Response<LiveStream_Get_Detail_Model> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Response: " + response.body().toString());
                    callBack.onResponse(response);
                } else {
                    Log.e(TAG, "Response Error: " + response.errorBody());
                    callBack.onError(new Exception("Response Error"));
                }
            }

            @Override
            public void onFailure(Call<LiveStream_Get_Detail_Model> call, Throwable t) {
                Log.e(TAG, "API Call Failed", t);
                callBack.onError(t);
            }
        });
    }



    // Hàm tạo security token (cần thay thế bằng thuật toán bảo mật thực tế)
    private String generateSecurityToken() {
        return "secure_token_example";
    }
}
