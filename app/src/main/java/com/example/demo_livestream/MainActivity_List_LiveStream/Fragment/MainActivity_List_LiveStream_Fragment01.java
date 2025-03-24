package com.example.demo_livestream.MainActivity_List_LiveStream.Fragment;

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

import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment_Adapter.MainActivity_List_LiveStream_Fragment01_Adapter;
import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment_Model.MainActivity_List_LiveStream_Fragment01_Model;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_List_LiveStream_Fragment01 extends Fragment {
    private RecyclerView recyclerView_list_livestream_fragment01;
    private MainActivity_List_LiveStream_Fragment01_Adapter adapter_list_livestream_fragment01;
    private List<MainActivity_List_LiveStream_Fragment01_Model> itemList_list_livestream_fragment01;
    private CircleIndicator2 circleIndicator2_list_livestream_fragment01;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_list_livestream_fragment01, container, false);

        // Ánh xạ RecyclerView và Indicator
        recyclerView_list_livestream_fragment01 = view.findViewById(R.id.rm_activity_main_list_livestream_fragment01_rv_01);
        circleIndicator2_list_livestream_fragment01 = view.findViewById(R.id.rm_activity_main_list_livestream_fragment01_ci_01);

        // Dữ liệu mẫu
        itemList_list_livestream_fragment01 = new ArrayList<>();
        itemList_list_livestream_fragment01.add(new MainActivity_List_LiveStream_Fragment01_Model(R.drawable.rm_bacgroup_01));
        itemList_list_livestream_fragment01.add(new MainActivity_List_LiveStream_Fragment01_Model(R.drawable.rm_bacgroup_01));
        itemList_list_livestream_fragment01.add(new MainActivity_List_LiveStream_Fragment01_Model(R.drawable.rm_bacgroup_01));
        itemList_list_livestream_fragment01.add(new MainActivity_List_LiveStream_Fragment01_Model(R.drawable.rm_bacgroup_02));
        itemList_list_livestream_fragment01.add(new MainActivity_List_LiveStream_Fragment01_Model(R.drawable.rm_bacgroup_02));
        itemList_list_livestream_fragment01.add(new MainActivity_List_LiveStream_Fragment01_Model(R.drawable.rm_bacgroup_02));
        itemList_list_livestream_fragment01.add(new MainActivity_List_LiveStream_Fragment01_Model(R.drawable.rm_bacgroup_03));
        itemList_list_livestream_fragment01.add(new MainActivity_List_LiveStream_Fragment01_Model(R.drawable.rm_bacgroup_03));
        itemList_list_livestream_fragment01.add(new MainActivity_List_LiveStream_Fragment01_Model(R.drawable.rm_bacgroup_03));

        // Cấu hình RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_list_livestream_fragment01.setLayoutManager(layoutManager);

        // Khởi tạo Adapter
        adapter_list_livestream_fragment01 = new MainActivity_List_LiveStream_Fragment01_Adapter(getContext(), itemList_list_livestream_fragment01, recyclerView_list_livestream_fragment01);
        recyclerView_list_livestream_fragment01.setAdapter(adapter_list_livestream_fragment01);

        // Cấu hình CircleIndicator2
        circleIndicator2_list_livestream_fragment01.createIndicators(itemList_list_livestream_fragment01.size(), 0);
        recyclerView_list_livestream_fragment01.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() % itemList_list_livestream_fragment01.size();
                circleIndicator2_list_livestream_fragment01.animatePageSelected(position);
            }
        });

        // Bắt đầu auto-scroll
        adapter_list_livestream_fragment01.startAutoScroll();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter_list_livestream_fragment01.stopAutoScroll();
    }
}
