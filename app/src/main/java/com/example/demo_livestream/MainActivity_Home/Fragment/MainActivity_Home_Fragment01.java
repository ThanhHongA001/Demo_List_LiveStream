package com.example.demo_livestream.MainActivity_Home.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.Api.Api_LiveStream_Interface;
import com.example.demo_livestream.Api.Api_LiveStream_Retrofit;
import com.example.demo_livestream.MainActivity_Home.Fragment_Adapter.MainActivity_Home_Fragment01_Adapter;
import com.example.demo_livestream.MainActivity_Home.Fragment_Model.MainActivity_Home_Fragment01_Model;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_Home_Fragment01 extends Fragment {

    private RecyclerView recyclerView;
    private MainActivity_Home_Fragment01_Adapter adapter;
    private List<MainActivity_Home_Fragment01_Model> livestreamList;
    private CircleIndicator2 circleIndicator;
    private LinearLayoutManager layoutManager;

    public MainActivity_Home_Fragment01() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_home_fragment01, container, false);

        // Ánh xạ view
        recyclerView = view.findViewById(R.id.rm_activity_main_home_fragment01_rv_01);
        circleIndicator = view.findViewById(R.id.rm_activity_main_home_fragment01_ci_01);

        // Cấu hình RecyclerView
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Khởi tạo danh sách livestream rỗng
        livestreamList = new ArrayList<>();
        adapter = new MainActivity_Home_Fragment01_Adapter(getContext(), livestreamList);
        recyclerView.setAdapter(adapter);

        // Gọi API để lấy dữ liệu
        fetchLivestreamData();

        return view;
    }

    private void fetchLivestreamData() {
        Api_LiveStream_Interface apiService = Api_LiveStream_Retrofit.getApiService();
        apiService.getListLivestream(1, 1, 10).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Giả sử dữ liệu từ API là một danh sách JSON
                    List<MainActivity_Home_Fragment01_Model> fetchedData = parseLivestreamResponse(response.body());

                    if (fetchedData != null) {
                        livestreamList.clear();
                        livestreamList.addAll(fetchedData);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private List<MainActivity_Home_Fragment01_Model> parseLivestreamResponse(Object response) {
        List<MainActivity_Home_Fragment01_Model> result = new ArrayList<>();

        // Giả sử response là một List<Map<String, Object>>
        if (response instanceof List<?>) {
            for (Object obj : (List<?>) response) {
                if (obj instanceof java.util.Map) {
                    java.util.Map<?, ?> map = (java.util.Map<?, ?>) obj;
                    String id = map.get("id") != null ? map.get("id").toString() : "";
                    String title = map.get("title") != null ? map.get("title").toString() : "";
                    String thumbnail = map.get("thumbnail") != null ? map.get("thumbnail").toString() : "";

                    result.add(new MainActivity_Home_Fragment01_Model(id, title, thumbnail));
                }
            }
        }
        return result;
    }
}
