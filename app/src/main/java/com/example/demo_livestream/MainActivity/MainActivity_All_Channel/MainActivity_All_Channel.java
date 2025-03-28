package com.example.demo_livestream.MainActivity.MainActivity_All_Channel;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.Api.Apis.Api_Retrofit_Client_Instance;
import com.example.demo_livestream.Api.Apis.Api_Service_Interface;
import com.example.demo_livestream.Api.response.RmChannelResponse;
import com.example.demo_livestream.Api.model.RMChannel;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_All_Channel extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MainActivity_All_Channel_Adapter adapter;
    private List<RMChannel> channelList = new ArrayList<>();
    private ImageView btnBack_all_channel;

    // API Service
    private Api_Service_Interface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rm_activity_main_all_channel);

        recyclerView = findViewById(R.id.rm_activity_main_all_channel_rcv01);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo API Service
        apiService = Api_Retrofit_Client_Instance.getClient().create(Api_Service_Interface.class);

        // Load danh sách kênh từ API
        loadChannelList();

        btnBack_all_channel = findViewById(R.id.rm_activity_main_all_channel_img_01);
        btnBack_all_channel.setOnClickListener(v -> onBackPressed());
    }

    private void loadChannelList() {
        // Gọi API lấy danh sách kênh
        Call<RmChannelResponse> call = apiService.get_LiveStream_ListChannel(0, 10, "user_123");

        call.enqueue(new Callback<RmChannelResponse>() {
            @Override
            public void onResponse(Call<RmChannelResponse> call, Response<RmChannelResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    channelList = response.body().getData();
                    // Cập nhật Adapter
                    adapter = new MainActivity_All_Channel_Adapter(channelList);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity_All_Channel.this, "Lỗi lấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RmChannelResponse> call, Throwable t) {
                Log.e("API_ERROR", "Lỗi API: " + t.getMessage());
                Toast.makeText(MainActivity_All_Channel.this, "Không thể kết nối đến server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
