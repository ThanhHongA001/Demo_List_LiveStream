package com.example.demo_livestream.MainActivity.MainActivity_All_LiveStream;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demo_livestream.R;
import java.util.ArrayList;
import java.util.List;

public class MainActivity_All_LiveStream extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MainActivity_All_LiveStream_Adapter adapter;
    private List<MainActivity_All_LiveStream_Model> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rm_activity_main_all_livestream);

        recyclerView = findViewById(R.id.rm_activity_main_all_livestream_rcv_01);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Vuốt dọc

        itemList = new ArrayList<>();
        itemList.add(new MainActivity_All_LiveStream_Model(0)); // Item 06
        itemList.add(new MainActivity_All_LiveStream_Model(1)); // Item 07
        itemList.add(new MainActivity_All_LiveStream_Model(1)); // Item 07
        itemList.add(new MainActivity_All_LiveStream_Model(1)); // Item 07
        itemList.add(new MainActivity_All_LiveStream_Model(1)); // Item 07
        itemList.add(new MainActivity_All_LiveStream_Model(1)); // Item 07

        adapter = new MainActivity_All_LiveStream_Adapter(this, itemList);
        recyclerView.setAdapter(adapter);
    }
}
