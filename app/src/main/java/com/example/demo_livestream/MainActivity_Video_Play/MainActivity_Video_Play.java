package com.example.demo_livestream.MainActivity_Video_Play;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demo_livestream.R;
import java.util.ArrayList;
import java.util.List;

public class MainActivity_Video_Play extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainActivity_Video_Play_Adapter adapter;
    private List<MainActivity_Video_Play_Model> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rm_activity_main_video_play);

        recyclerView = findViewById(R.id.rm_activity_main_video_play_rcv_01);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        itemList.add(new MainActivity_Video_Play_Model("Gaming Stream", "Chris Doe", 512, "2 hours ago"));
        itemList.add(new MainActivity_Video_Play_Model("Gaming Stream", "Chris Doe", 512, "2 hours ago"));
        itemList.add(new MainActivity_Video_Play_Model("Gaming Stream", "Chris Doe", 512, "2 hours ago"));
        itemList.add(new MainActivity_Video_Play_Model("Gaming Stream", "Chris Doe", 512, "2 hours ago"));
        itemList.add(new MainActivity_Video_Play_Model("Gaming Stream", "Chris Doe", 512, "2 hours ago"));
        itemList.add(new MainActivity_Video_Play_Model("Gaming Stream", "Chris Doe", 512, "2 hours ago"));
        itemList.add(new MainActivity_Video_Play_Model("Gaming Stream", "Chris Doe", 512, "2 hours ago"));
        itemList.add(new MainActivity_Video_Play_Model("Gaming Stream", "Chris Doe", 512, "2 hours ago"));
        itemList.add(new MainActivity_Video_Play_Model("Gaming Stream", "Chris Doe", 512, "2 hours ago"));

        adapter = new MainActivity_Video_Play_Adapter(itemList);
        recyclerView.setAdapter(adapter);
    }
}
