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
import me.relex.circleindicator.CircleIndicator2;

import com.example.demo_livestream.MainActivity_Home.Fragment_Adapter.MainActivity_Home_Fragment01_Adapter;
import com.example.demo_livestream.MainActivity_Home.Fragment_Model.MainActivity_Home_Fragment01_Model;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Home_Fragment01 extends Fragment {
    private RecyclerView recyclerView_home_fragment01;
    private MainActivity_Home_Fragment01_Adapter adapter_home_fragment01;
    private List<MainActivity_Home_Fragment01_Model> itemList;
    private CircleIndicator2 circleIndicator2_home_fragment01;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_home_fragment01, container, false);

        // Ánh xạ RecyclerView và Indicator
        recyclerView_home_fragment01 = view.findViewById(R.id.rm_activity_main_home_fragment01_rv_01);
        circleIndicator2_home_fragment01 = view.findViewById(R.id.rm_activity_main_home_fragment01_ci_01);

        // Dữ liệu mẫu
        itemList = new ArrayList<>();
        itemList.add(new MainActivity_Home_Fragment01_Model(R.drawable.rm_bacgroup_01));
        itemList.add(new MainActivity_Home_Fragment01_Model(R.drawable.rm_bacgroup_01));
        itemList.add(new MainActivity_Home_Fragment01_Model(R.drawable.rm_bacgroup_01));
        itemList.add(new MainActivity_Home_Fragment01_Model(R.drawable.rm_bacgroup_02));
        itemList.add(new MainActivity_Home_Fragment01_Model(R.drawable.rm_bacgroup_02));
        itemList.add(new MainActivity_Home_Fragment01_Model(R.drawable.rm_bacgroup_02));
        itemList.add(new MainActivity_Home_Fragment01_Model(R.drawable.rm_bacgroup_03));
        itemList.add(new MainActivity_Home_Fragment01_Model(R.drawable.rm_bacgroup_03));
        itemList.add(new MainActivity_Home_Fragment01_Model(R.drawable.rm_bacgroup_03));

        // Cấu hình RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_home_fragment01.setLayoutManager(layoutManager);

        // Khởi tạo Adapter với RecyclerView để hỗ trợ auto-scroll
        adapter_home_fragment01 = new MainActivity_Home_Fragment01_Adapter(getContext(), itemList, recyclerView_home_fragment01);
        recyclerView_home_fragment01.setAdapter(adapter_home_fragment01);

        // Cấu hình CircleIndicator2
        circleIndicator2_home_fragment01.createIndicators(itemList.size(), 0);
        recyclerView_home_fragment01.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() % itemList.size();
                circleIndicator2_home_fragment01.animatePageSelected(position);
            }
        });

        // Bắt đầu auto-scroll
        adapter_home_fragment01.startAutoScroll();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter_home_fragment01.stopAutoScroll();
    }
}
