package com.example.demo_livestream.MainActivity_List_LiveStream.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.relex.circleindicator.CircleIndicator2;

import com.example.demo_livestream.Api.Apis.Api_LiveStream_Service_Interface;
import com.example.demo_livestream.Api.Apis.Api_LiveStream_Retrofit_Client_Instance;
import com.example.demo_livestream.Api.Model.LiveStream_List_Model;
import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment_Adapter.MainActivity_List_LiveStream_Fragment01_Adapter;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_List_LiveStream_Fragment01 extends Fragment {
    private RecyclerView recyclerView_list_livestream_fragment01;
    private MainActivity_List_LiveStream_Fragment01_Adapter adapter_list_livestream_fragment01;
    private List<LiveStream_List_Model> itemList_list_livestream_fragment01;
    private CircleIndicator2 circleIndicator2_list_livestream_fragment01;

    private Api_LiveStream_Service_Interface apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_list_livestream_fragment01, container, false);

        // Ánh xạ RecyclerView và Indicator
        recyclerView_list_livestream_fragment01 = view.findViewById(R.id.rm_activity_main_list_livestream_fragment01_rv_01);
        circleIndicator2_list_livestream_fragment01 = view.findViewById(R.id.rm_activity_main_list_livestream_fragment01_ci_01);

        // Khởi tạo danh sách
        itemList_list_livestream_fragment01 = new ArrayList<>();

        // Cấu hình RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_list_livestream_fragment01.setLayoutManager(layoutManager);

        // Khởi tạo Adapter
        adapter_list_livestream_fragment01 = new MainActivity_List_LiveStream_Fragment01_Adapter(getContext(), itemList_list_livestream_fragment01, recyclerView_list_livestream_fragment01);
        recyclerView_list_livestream_fragment01.setAdapter(adapter_list_livestream_fragment01);

        // Gọi API để lấy dữ liệu
        loadLiveStreamData();

        return view;
    }

    private void loadLiveStreamData() {
        apiService = Api_LiveStream_Retrofit_Client_Instance.getClient().create(Api_LiveStream_Service_Interface.class);

        String msisdn = "0393648079";
        int page = 1;
        int limit = 10;

        Call<List<LiveStream_List_Model>> call = apiService.get_LiveStream_List(5, page, limit, msisdn);
        call.enqueue(new Callback<List<LiveStream_List_Model>>() {
            @Override
            public void onResponse(Call<List<LiveStream_List_Model>> call, Response<List<LiveStream_List_Model>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<LiveStream_List_Model> liveStreamData = response.body();
                    updateRecyclerView(liveStreamData);
                    Log.e("khanhpq", "234556");
                } else {
                    Toast.makeText(getContext(), "Lỗi: Không nhận được dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<LiveStream_List_Model>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối API!", Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Lỗi gọi API", t);
            }
        });
    }


    private void updateRecyclerView(List<LiveStream_List_Model> liveStreamData) {
        if (liveStreamData != null) {
            itemList_list_livestream_fragment01.clear();

            Log.e("khanhpq", "99999");
            // Chuyển dữ liệu từ API thành danh sách hiển thị
            itemList_list_livestream_fragment01.add(new LiveStream_List_Model());

            adapter_list_livestream_fragment01.notifyDataSetChanged();

            // Cập nhật CircleIndicator2
            circleIndicator2_list_livestream_fragment01.createIndicators(itemList_list_livestream_fragment01.size(), 0);
            recyclerView_list_livestream_fragment01.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() % itemList_list_livestream_fragment01.size();
                    circleIndicator2_list_livestream_fragment01.animatePageSelected(position);
                }
            });

            // Auto-scroll
            adapter_list_livestream_fragment01.startAutoScroll();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter_list_livestream_fragment01.stopAutoScroll();
    }
}
