package com.example.demo_livestream.MainActivity_Channel.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.MainActivity_Channel.Fragment_Adapter.MainActivity_Channel_Fragment02_Adapter;
import com.example.demo_livestream.MainActivity_Channel.Fragment_Model.MainActivity_Channel_Fragment02_Model;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Channel_Fragment02 extends Fragment {

    private RecyclerView recyclerView;
    private MainActivity_Channel_Fragment02_Adapter adapter;
    private List<MainActivity_Channel_Fragment02_Model> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_channel_fragment02, container, false);

        recyclerView = view.findViewById(R.id.rm_activity_main_channel_fragment02_rcv01);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo danh sách dữ liệu mẫu
        itemList = new ArrayList<>();
        itemList.add(new MainActivity_Channel_Fragment02_Model("Live Stream 1", "Alex Sun", "367 Views", "1 hour", R.drawable.rm_bacgroup_05, R.drawable.rm_ic_live_02));
        itemList.add(new MainActivity_Channel_Fragment02_Model("Live Stream 2", "John Doe", "512 Views", "2 hours", R.drawable.rm_bacgroup_05, R.drawable.rm_ic_live_02));
        itemList.add(new MainActivity_Channel_Fragment02_Model("Live Stream 1", "Alex Sun", "367 Views", "1 hour", R.drawable.rm_bacgroup_05, R.drawable.rm_ic_live_02));
        itemList.add(new MainActivity_Channel_Fragment02_Model("Live Stream 2", "John Doe", "512 Views", "2 hours", R.drawable.rm_bacgroup_05, R.drawable.rm_ic_live_02));
        itemList.add(new MainActivity_Channel_Fragment02_Model("Live Stream 1", "Alex Sun", "367 Views", "1 hour", R.drawable.rm_bacgroup_05, R.drawable.rm_ic_live_02));
        itemList.add(new MainActivity_Channel_Fragment02_Model("Live Stream 2", "John Doe", "512 Views", "2 hours", R.drawable.rm_bacgroup_05, R.drawable.rm_ic_live_02));
        itemList.add(new MainActivity_Channel_Fragment02_Model("Live Stream 1", "Alex Sun", "367 Views", "1 hour", R.drawable.rm_bacgroup_05, R.drawable.rm_ic_live_02));
        itemList.add(new MainActivity_Channel_Fragment02_Model("Live Stream 2", "John Doe", "512 Views", "2 hours", R.drawable.rm_bacgroup_05, R.drawable.rm_ic_live_02));
        itemList.add(new MainActivity_Channel_Fragment02_Model("Live Stream 1", "Alex Sun", "367 Views", "1 hour", R.drawable.rm_bacgroup_05, R.drawable.rm_ic_live_02));

        adapter = new MainActivity_Channel_Fragment02_Adapter(itemList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
