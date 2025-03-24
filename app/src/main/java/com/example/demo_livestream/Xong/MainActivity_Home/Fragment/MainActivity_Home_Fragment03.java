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
import com.example.demo_livestream.Xong.MainActivity_Home.Fragment_Adapter.MainActivity_Home_Fragment03_Adapter;
import com.example.demo_livestream.Xong.MainActivity_Home.Fragment_Model.MainActivity_Home_Fragment03_Model;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Home_Fragment03 extends Fragment {
    private RecyclerView recyclerView_home_fragment03;
    private MainActivity_Home_Fragment03_Adapter adapter_home_fragment03;
    private List<MainActivity_Home_Fragment03_Model> list__home_fragment03;
    private Button btnSeeAll_home_fragment03;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_home_fragment03, container, false);

        // Cập nhật ID đúng theo XML
        btnSeeAll_home_fragment03 = view.findViewById(R.id.rm_activity_main_home_fragment03_btn_01);
        recyclerView_home_fragment03 = view.findViewById(R.id.rm_activity_main_home_fragment03_rv_01);

        // Sự kiện click để chuyển sang MainActivity_Button
        btnSeeAll_home_fragment03.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity_Button.class);
            startActivity(intent);
        });

        // Thiết lập LayoutManager cho RecyclerView
        recyclerView_home_fragment03.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Tạo danh sách dữ liệu mẫu
        list__home_fragment03 = new ArrayList<>();
        list__home_fragment03.add(new MainActivity_Home_Fragment03_Model("$ 888", "SH123", "429 MB and 428 On-net SMS, only $ 200", "Validity: 30 Days"));
        list__home_fragment03.add(new MainActivity_Home_Fragment03_Model("$ 599", "SH456", "500 MB and 500 On-net SMS, only $ 300", "Validity: 20 Days"));
        list__home_fragment03.add(new MainActivity_Home_Fragment03_Model("$ 999", "SH789", "1 GB and 1000 On-net SMS, only $ 500", "Validity: 60 Days"));
        list__home_fragment03.add(new MainActivity_Home_Fragment03_Model("$ 888", "SH123", "429 MB and 428 On-net SMS, only $ 200", "Validity: 30 Days"));
        list__home_fragment03.add(new MainActivity_Home_Fragment03_Model("$ 599", "SH456", "500 MB and 500 On-net SMS, only $ 300", "Validity: 20 Days"));
        list__home_fragment03.add(new MainActivity_Home_Fragment03_Model("$ 999", "SH789", "1 GB and 1000 On-net SMS, only $ 500", "Validity: 60 Days"));
        list__home_fragment03.add(new MainActivity_Home_Fragment03_Model("$ 888", "SH123", "429 MB and 428 On-net SMS, only $ 200", "Validity: 30 Days"));
        list__home_fragment03.add(new MainActivity_Home_Fragment03_Model("$ 599", "SH456", "500 MB and 500 On-net SMS, only $ 300", "Validity: 20 Days"));
        list__home_fragment03.add(new MainActivity_Home_Fragment03_Model("$ 999", "SH789", "1 GB and 1000 On-net SMS, only $ 500", "Validity: 60 Days"));

        // Gán Adapter
        adapter_home_fragment03 = new MainActivity_Home_Fragment03_Adapter(list__home_fragment03);
        recyclerView_home_fragment03.setAdapter(adapter_home_fragment03);

        return view;
    }
}
