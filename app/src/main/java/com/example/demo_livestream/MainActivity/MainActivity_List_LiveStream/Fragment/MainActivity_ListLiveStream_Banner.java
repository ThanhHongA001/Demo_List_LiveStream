package com.example.demo_livestream.MainActivity.MainActivity_List_LiveStream.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import me.relex.circleindicator.CircleIndicator2;

import com.example.demo_livestream.Api.Apis.Api_Service_Interface;
import com.example.demo_livestream.Api.Apis.Api_Retrofit_Client_Instance;
import com.example.demo_livestream.Api.model.RMLivestream;
import com.example.demo_livestream.Api.response.RMLivestreamResponse;
import com.example.demo_livestream.MainActivity.MainActivity_List_LiveStream.Fragment_Adapter.MainActivity_ListLiveStream_Banner_Adapter;
import com.example.demo_livestream.MainActivity.MainActivity_Video_Play.MainActivity_Video_Play;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_ListLiveStream_Banner extends Fragment {

    private RecyclerView recyclerView;
    private CircleIndicator2 circleIndicator;
    private MainActivity_ListLiveStream_Banner_Adapter adapter;
    private List<RMLivestream> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_list_livestream_banner, container, false);

        // Ánh xạ RecyclerView và Indicator
        recyclerView = view.findViewById(R.id.rm_activity_main_list_livestream_fragment01_rv_01);
        circleIndicator = view.findViewById(R.id.rm_activity_main_list_livestream_fragment01_ci_01);

        // Khởi tạo danh sách
        itemList = new ArrayList<>();

        // Cấu hình RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Khởi tạo Adapter
        adapter = new MainActivity_ListLiveStream_Banner_Adapter(getContext(), itemList);
        recyclerView.setAdapter(adapter);

        // Xử lý sự kiện click vào item
        adapter.setOnItemClickListener(livestream -> {
            Intent intent = new Intent(getActivity(), MainActivity_Video_Play.class);
            intent.putExtra("livestream_url", livestream.getHlsPlaylink());
            intent.putExtra("livestream_title", livestream.getTitle());
            startActivity(intent);
            getActivity().finish();
        });

        // Gọi API để lấy dữ liệu
        loadLiveStreamData();
        return view;
    }

    // Hàm gọi API để lấy danh sách livestream
    private void loadLiveStreamData() {
        Api_Service_Interface apiService = Api_Retrofit_Client_Instance.getClient().create(Api_Service_Interface.class);
        Call<RMLivestreamResponse> call = apiService.get_LiveStream_List(4, 1, 8, "0375331875");

        call.enqueue(new Callback<RMLivestreamResponse>() {
            @Override
            public void onResponse(@NonNull Call<RMLivestreamResponse> call, @NonNull Response<RMLivestreamResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<RMLivestream> liveStreamList = response.body().getListLivStream();
                    Log.e("hongbt", liveStreamList.size() + "");

                    // Cập nhật Adapter với dữ liệu mới
                    adapter.updateData(liveStreamList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RMLivestreamResponse> call, @NonNull Throwable t) {
                Log.e("API Error", t.getMessage(), t);
            }
        });
    }
}
