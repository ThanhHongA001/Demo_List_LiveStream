package com.example.demo_livestream.Xong.MainActivity_Home.Fragment;

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

import com.example.demo_livestream.MainActivity_Button;
import com.example.demo_livestream.Xong.MainActivity_Home.Fragment_Adapter.MainActivity_Home_Fragment04_Adapter;
import com.example.demo_livestream.Xong.MainActivity_Home.Fragment_Model.MainActivity_Home_Fragment04_Model;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Home_Fragment04 extends Fragment {

    private RecyclerView recyclerView_home_fragment04;
    private MainActivity_Home_Fragment04_Adapter adapter_home_fragment04;
    private List<MainActivity_Home_Fragment04_Model> list_home_fragment04;

    private Button btnSeeAll_home_fragment04;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_home_fragment04, container, false);

        // Ánh xạ nút bấm với ID chính xác từ XML
        btnSeeAll_home_fragment04 = view.findViewById(R.id.rm_activity_main_home_fragment04_btn_01);

        // Sự kiện click để chuyển sang MainActivity_Button
        btnSeeAll_home_fragment04.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity_Button.class);
            startActivity(intent);
        });

        // Ánh xạ RecyclerView với ID đúng
        recyclerView_home_fragment04 = view.findViewById(R.id.rm_activity_main_home_fragment04_rv_01);
        recyclerView_home_fragment04.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Tạo danh sách item
        list_home_fragment04 = new ArrayList<>();
        list_home_fragment04.add(new MainActivity_Home_Fragment04_Model(R.drawable.rm_bacgroup_01, "LiveStream 1", 100));
        list_home_fragment04.add(new MainActivity_Home_Fragment04_Model(R.drawable.rm_bacgroup_01, "LiveStream 2", 200));
        list_home_fragment04.add(new MainActivity_Home_Fragment04_Model(R.drawable.rm_bacgroup_01, "LiveStream 3", 300));
        list_home_fragment04.add(new MainActivity_Home_Fragment04_Model(R.drawable.rm_bacgroup_01, "LiveStream 1", 100));
        list_home_fragment04.add(new MainActivity_Home_Fragment04_Model(R.drawable.rm_bacgroup_01, "LiveStream 2", 200));
        list_home_fragment04.add(new MainActivity_Home_Fragment04_Model(R.drawable.rm_bacgroup_01, "LiveStream 3", 300));
        list_home_fragment04.add(new MainActivity_Home_Fragment04_Model(R.drawable.rm_bacgroup_01, "LiveStream 1", 100));
        list_home_fragment04.add(new MainActivity_Home_Fragment04_Model(R.drawable.rm_bacgroup_01, "LiveStream 2", 200));
        list_home_fragment04.add(new MainActivity_Home_Fragment04_Model(R.drawable.rm_bacgroup_01, "LiveStream 3", 300));

        // Thiết lập adapter
        adapter_home_fragment04 = new MainActivity_Home_Fragment04_Adapter(list_home_fragment04);
        recyclerView_home_fragment04.setAdapter(adapter_home_fragment04);

        return view;
    }
}
