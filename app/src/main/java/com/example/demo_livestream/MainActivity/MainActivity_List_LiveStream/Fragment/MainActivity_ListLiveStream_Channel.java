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
import com.example.demo_livestream.Api.model.RMChannel;
import com.example.demo_livestream.Api.response.RmChannelResponse;
import com.example.demo_livestream.MainActivity.MainActivity_All_Channel.MainActivity_All_Channel;
import com.example.demo_livestream.MainActivity.MainActivity_List_LiveStream.Fragment_Adapter.MainActivity_ListLiveStream_Channel_Adapter;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_ListLiveStream_Channel extends Fragment {

    private RecyclerView recyclerView;
    private MainActivity_ListLiveStream_Channel_Adapter adapter;
    private List<RMChannel> channelList = new ArrayList<>();

    private AppCompatButton btn_channel;

    public MainActivity_ListLiveStream_Channel() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_list_livestream_channel, container, false);

        recyclerView = view.findViewById(R.id.rm_activity_main_list_livestream_fragment03_rv_01);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MainActivity_ListLiveStream_Channel_Adapter(getContext(), channelList);
        recyclerView.setAdapter(adapter);

        fetchListChannel();

        btn_channel = view.findViewById(R.id.rm_activity_main_list_livestream_fragment03_btn_01);
        btn_channel.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity_All_Channel.class);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        return view;
    }

    private void fetchListChannel() {
        Api_Service_Interface apiService = Api_Retrofit_Client_Instance.getClient().create(Api_Service_Interface.class);
        Call<RmChannelResponse> call = apiService.get_LiveStream_ListChannel(2, 2, "0375331875");

        call.enqueue(new Callback<RmChannelResponse>() {
            @Override
            public void onResponse(@NonNull Call<RmChannelResponse> call, @NonNull Response<RmChannelResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    channelList.clear();
                    channelList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("API_ERROR", "Response error: " + response.message());
                    Toast.makeText(getContext(), "Lỗi tải danh sách kênh", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RmChannelResponse> call, @NonNull Throwable t) {
                Log.e("API_ERROR", "Call failed: " + t.getMessage());
                Toast.makeText(getContext(), "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
