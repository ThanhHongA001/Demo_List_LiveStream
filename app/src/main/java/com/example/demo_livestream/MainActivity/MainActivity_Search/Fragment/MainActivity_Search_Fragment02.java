package com.example.demo_livestream.MainActivity.MainActivity_Search.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo_livestream.MainActivity.MainActivity_Search.Fragment_Adapter.MainActivity_Search_Fragment02_Adapter;
import com.example.demo_livestream.MainActivity.MainActivity_Search.Fragment_Model.MainActivity_Search_Fragment02_Model;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Search_Fragment02 extends Fragment {
    private RecyclerView recyclerView;
    private MainActivity_Search_Fragment02_Adapter adapter;
    private List<MainActivity_Search_Fragment02_Model> itemList;

    public MainActivity_Search_Fragment02() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_search_fragment02, container, false);

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.rm_activity_main_search_fragment02_rcv_01);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo danh sách dữ liệu
        itemList = new ArrayList<>();
        itemList.add(new MainActivity_Search_Fragment02_Model("Live Gaming", "Alex Sun", "367 Views", "1 hour ago"));
        itemList.add(new MainActivity_Search_Fragment02_Model("Music Live", "Emily Clark", "1.2K Views", "30 minutes ago"));
        itemList.add(new MainActivity_Search_Fragment02_Model("Coding Tutorial", "John Doe", "850 Views", "2 hours ago"));
        itemList.add(new MainActivity_Search_Fragment02_Model("Live Gaming", "Alex Sun", "367 Views", "1 hour ago"));
        itemList.add(new MainActivity_Search_Fragment02_Model("Music Live", "Emily Clark", "1.2K Views", "30 minutes ago"));
        itemList.add(new MainActivity_Search_Fragment02_Model("Coding Tutorial", "John Doe", "850 Views", "2 hours ago"));
        itemList.add(new MainActivity_Search_Fragment02_Model("Live Gaming", "Alex Sun", "367 Views", "1 hour ago"));
        itemList.add(new MainActivity_Search_Fragment02_Model("Music Live", "Emily Clark", "1.2K Views", "30 minutes ago"));
        itemList.add(new MainActivity_Search_Fragment02_Model("Coding Tutorial", "John Doe", "850 Views", "2 hours ago"));

        // Gán Adapter
        adapter = new MainActivity_Search_Fragment02_Adapter(getContext(), itemList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
