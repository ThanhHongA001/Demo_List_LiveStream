package com.example.demo_livestream.MainActivity_All_Channel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_All_Channel extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MainActivity_All_Channel_Adapter adapter;
    private List<MainActivity_All_Channel_Model> channelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rm_activity_main_all_channel);

        recyclerView = findViewById(R.id.rm_activity_main_all_channel_rcv01);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo danh sách dữ liệu
        channelList = new ArrayList<>();
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "Alex Sun", 8000, 367));
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "John Doe", 5000, 250));
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "Emma Watson", 12000, 450));
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "Alex Sun", 8000, 367));
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "John Doe", 5000, 250));
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "Emma Watson", 12000, 450));
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "Alex Sun", 8000, 367));
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "John Doe", 5000, 250));
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "Emma Watson", 12000, 450));
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "Alex Sun", 8000, 367));
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "John Doe", 5000, 250));
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "Emma Watson", 12000, 450));
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "Alex Sun", 8000, 367));
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "John Doe", 5000, 250));
        channelList.add(new MainActivity_All_Channel_Model(R.drawable.rm_bacgroup_04, "Emma Watson", 12000, 450));

        // Gán adapter cho RecyclerView
        adapter = new MainActivity_All_Channel_Adapter(channelList);
        recyclerView.setAdapter(adapter);
    }
}
