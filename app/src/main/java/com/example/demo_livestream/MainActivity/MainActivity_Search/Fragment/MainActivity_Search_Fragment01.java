package com.example.demo_livestream.MainActivity.MainActivity_Search.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.MainActivity.MainActivity_Search.Fragment_Adapter.MainActivity_Search_Fragment01_Adapter;
import com.example.demo_livestream.MainActivity.MainActivity_Search.Fragment_Model.MainActivity_Search_Fragment01_Model;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Search_Fragment01 extends Fragment {

    private RecyclerView recyclerView;
    private MainActivity_Search_Fragment01_Adapter adapter;
    private List<MainActivity_Search_Fragment01_Model> itemList;

    public MainActivity_Search_Fragment01() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_search_fragment01, container, false);
        recyclerView = view.findViewById(R.id.rm_activity_main_search_fragment01_rcv_01);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo danh sách dữ liệu mẫu
        itemList = new ArrayList<>();
        itemList.add(new MainActivity_Search_Fragment01_Model("Alex Sun", "8k Followers", "367 Videos", R.drawable.rm_bacgroup_04));
        itemList.add(new MainActivity_Search_Fragment01_Model("Jane Doe", "12k Followers", "420 Videos", R.drawable.rm_bacgroup_04));
        itemList.add(new MainActivity_Search_Fragment01_Model("John Smith", "5k Followers", "150 Videos", R.drawable.rm_bacgroup_04));
        itemList.add(new MainActivity_Search_Fragment01_Model("Alex Sun", "8k Followers", "367 Videos", R.drawable.rm_bacgroup_04));
        itemList.add(new MainActivity_Search_Fragment01_Model("Jane Doe", "12k Followers", "420 Videos", R.drawable.rm_bacgroup_04));
        itemList.add(new MainActivity_Search_Fragment01_Model("John Smith", "5k Followers", "150 Videos", R.drawable.rm_bacgroup_04));
        itemList.add(new MainActivity_Search_Fragment01_Model("Alex Sun", "8k Followers", "367 Videos", R.drawable.rm_bacgroup_04));
        itemList.add(new MainActivity_Search_Fragment01_Model("Jane Doe", "12k Followers", "420 Videos", R.drawable.rm_bacgroup_04));
        itemList.add(new MainActivity_Search_Fragment01_Model("John Smith", "5k Followers", "150 Videos", R.drawable.rm_bacgroup_04));

        // Gán adapter cho RecyclerView
        adapter = new MainActivity_Search_Fragment01_Adapter(getContext(), itemList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
