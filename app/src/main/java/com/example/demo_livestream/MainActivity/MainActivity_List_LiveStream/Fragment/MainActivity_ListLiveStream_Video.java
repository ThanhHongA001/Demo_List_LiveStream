package com.example.demo_livestream.MainActivity.MainActivity_List_LiveStream.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.Api.Apis.Api_Retrofit_Client_Instance;
import com.example.demo_livestream.Api.Apis.Api_Service_Interface;
import com.example.demo_livestream.Api.model.RMLivestream;
import com.example.demo_livestream.Api.response.RMLivestreamResponse;
import com.example.demo_livestream.MainActivity.MainActivity_All_LiveStream.MainActivity_All_LiveStream;
import com.example.demo_livestream.MainActivity.MainActivity_List_LiveStream.Fragment_Adapter.MainActivity_ListLiveStream_Video_Adapter;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_ListLiveStream_Video extends Fragment {
    private RecyclerView recyclerView;
    private MainActivity_ListLiveStream_Video_Adapter adapter;
    private List<RMLivestream> videoList;
    private Api_Service_Interface apiService;

    private AppCompatButton btn_video;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_list_livestream_video, container, false);

        recyclerView = view.findViewById(R.id.rm_activity_main_list_livestream_fragment02_rv_01);
        videoList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MainActivity_ListLiveStream_Video_Adapter(getContext(), videoList);
        recyclerView.setAdapter(adapter);

        // Khởi tạo API Service
        apiService = Api_Retrofit_Client_Instance.getClient().create(Api_Service_Interface.class);

        btn_video = view.findViewById(R.id.rm_activity_main_list_livestream_fragment02_btn_01);
        btn_video.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity_All_LiveStream.class);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        // Gọi API
        loadVideoLiveStreamData();

        return view;
    }

    private void loadVideoLiveStreamData() {
        apiService.get_LiveStream_List(1, 1, 30, "12345").enqueue(new Callback<RMLivestreamResponse>() {
            @Override
            public void onResponse(Call<RMLivestreamResponse> call, Response<RMLivestreamResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<RMLivestream> data = response.body().getListLivStream();
                    if (data != null && !data.isEmpty()) {
                        adapter.updateData(data);
                    }
                } else {
                    Log.e("hongbt", "Lỗi: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RMLivestreamResponse> call, Throwable t) {
                Log.e("hongbt", "Lỗi kết nối: " + t.getMessage());
                Toast.makeText(getContext(), "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


