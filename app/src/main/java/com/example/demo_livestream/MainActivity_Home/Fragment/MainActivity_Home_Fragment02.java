package com.example.demo_livestream.MainActivity_Home.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.MainActivity_Home.Fragment_Adapter.MainActivity_Home_Fragment02_Adapter;
import com.example.demo_livestream.MainActivity_Home.Fragment_Model.MainActivity_Home_Fragment02_Model;
//import com.example.demo_livestream.MainActivity_List_LiveStream.MainActivity_List_LiveStream;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Home_Fragment02 extends Fragment {
    private RecyclerView recyclerView;
    private MainActivity_Home_Fragment02_Adapter adapter;
    private List<MainActivity_Home_Fragment02_Model> itemList;
    private Button btnSeeAll;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_home_fragment02, container, false);

        // Cập nhật ID đúng theo XML
        btnSeeAll = view.findViewById(R.id.rm_activity_main_home_fragment02_btn_01);
        recyclerView = view.findViewById(R.id.rm_activity_main_home_fragment02_rv_01);

        // Sự kiện click để chuyển sang MainActivity_All_LiveStream
        btnSeeAll.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), MainActivity_List_LiveStream.class);
//            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Tạo danh sách dữ liệu mẫu
        itemList = new ArrayList<>();
        itemList.add(new MainActivity_Home_Fragment02_Model("LiveStream 1", "1000 Views", R.drawable.rm_bacgroup_01));
        itemList.add(new MainActivity_Home_Fragment02_Model("LiveStream 2", "1500 Views", R.drawable.rm_bacgroup_01));
        itemList.add(new MainActivity_Home_Fragment02_Model("LiveStream 3", "2000 Views", R.drawable.rm_bacgroup_01));
        itemList.add(new MainActivity_Home_Fragment02_Model("LiveStream 4", "2500 Views", R.drawable.rm_bacgroup_01));
        itemList.add(new MainActivity_Home_Fragment02_Model("LiveStream 5", "3000 Views", R.drawable.rm_bacgroup_01));
        itemList.add(new MainActivity_Home_Fragment02_Model("LiveStream 6", "3500 Views", R.drawable.rm_bacgroup_01));
        itemList.add(new MainActivity_Home_Fragment02_Model("LiveStream 7", "4000 Views", R.drawable.rm_bacgroup_01));
        itemList.add(new MainActivity_Home_Fragment02_Model("LiveStream 8", "4500 Views", R.drawable.rm_bacgroup_01));
        itemList.add(new MainActivity_Home_Fragment02_Model("LiveStream 9", "5000 Views", R.drawable.rm_bacgroup_01));

        adapter = new MainActivity_Home_Fragment02_Adapter(itemList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
