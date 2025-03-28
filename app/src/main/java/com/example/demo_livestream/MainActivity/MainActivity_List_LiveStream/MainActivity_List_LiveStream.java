package com.example.demo_livestream.MainActivity.MainActivity_List_LiveStream;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.MainActivity.MainActivity_Home.MainActivity_Home;
import com.example.demo_livestream.MainActivity.MainActivity_List_LiveStream.Fragment.MainActivity_ListLiveStream_Banner;
import com.example.demo_livestream.MainActivity.MainActivity_List_LiveStream.Fragment.MainActivity_ListLiveStream_Video;
import com.example.demo_livestream.MainActivity.MainActivity_List_LiveStream.Fragment.MainActivity_ListLiveStream_Channel;
import com.example.demo_livestream.MainActivity.MainActivity_Search.MainActivity_Search;
import com.example.demo_livestream.MainActivity.MainActivity_Star.MainActivity_Star;
import com.example.demo_livestream.R;
import java.util.Arrays;
import java.util.List;

public class MainActivity_List_LiveStream extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rm_activity_main_list_livestream);

        // Tìm RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rm_activity_main_list_livestream_rv_01);

        // Danh sách các Fragment cần hiển thị
        List<Fragment> fragments_list_livestream = Arrays.asList(
                new MainActivity_ListLiveStream_Banner(),
                new MainActivity_ListLiveStream_Video(),
                new MainActivity_ListLiveStream_Channel()
        );

        // Thiết lập Adapter cho RecyclerView
        MainActivity_List_LiveStream_Adapter adapter = new MainActivity_List_LiveStream_Adapter(this, fragments_list_livestream);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        // Ánh xạ các ImageView
        ImageView imgBack = findViewById(R.id.rm_activity_main_list_livestream_img_01);
        ImageView imgStar = findViewById(R.id.rm_activity_main_list_livestream_img_02);
        ImageView imgSearch = findViewById(R.id.rm_activity_main_list_livestream_img_03);

        // Sự kiện bấm nút back (chuyển sang MainActivity_Home)
        imgBack.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_List_LiveStream.this, MainActivity_Home.class);
            startActivity(intent);
            finish();
        });

        // Sự kiện bấm vào imgStar (chuyển sang MainActivity_Star)
        imgStar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_List_LiveStream.this, MainActivity_Star.class);
            startActivity(intent);
            finish();
        });

        // Sự kiện bấm vào imgSearch (chuyển sang MainActivity_Search)
        imgSearch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_List_LiveStream.this, MainActivity_Search.class);
            startActivity(intent);
            finish();
        });
    }
}
