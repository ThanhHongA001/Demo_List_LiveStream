package com.example.demo_livestream.MainActivity.MainActivity_Star;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Star extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainActivity_Star_Adapter adapter;
    private List<MainActivity_Star_Model> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.rm_activity_main_star);

        recyclerView = findViewById(R.id.rm_activity_main_star_rcv_01);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        itemList.add(new MainActivity_Star_Model(R.drawable.rm_ic_star_01, "Give stars", "21:02 21/12/2024", 200, MainActivity_Star_Model.TYPE_ITEM_08));
        itemList.add(new MainActivity_Star_Model(R.drawable.rm_ic_star_02, "Give stars", "12:45 15/03/2025", -150, MainActivity_Star_Model.TYPE_ITEM_09));
        itemList.add(new MainActivity_Star_Model(R.drawable.rm_ic_star_03, "Give stars", "08:30 01/01/2025", -300, MainActivity_Star_Model.TYPE_ITEM_09));
        itemList.add(new MainActivity_Star_Model(R.drawable.rm_ic_star_04, "Give stars", "10:15 07/02/2025", 100, MainActivity_Star_Model.TYPE_ITEM_09));
        itemList.add(new MainActivity_Star_Model(R.drawable.rm_ic_star_05, "Give stars", "10:15 07/02/2025", 100, MainActivity_Star_Model.TYPE_ITEM_09));

        adapter = new MainActivity_Star_Adapter(itemList);
        recyclerView.setAdapter(adapter);
    }
}
