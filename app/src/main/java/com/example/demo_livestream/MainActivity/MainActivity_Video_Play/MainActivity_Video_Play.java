package com.example.demo_livestream.MainActivity.MainActivity_Video_Play;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Video_Play extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MainActivity_Video_Play_Adapter adapter;
    private List<MainActivity_Video_Play_Model> itemList;
    private ImageView imageView;
    private TextView textViewTitle;
    private ImageView imgBack_VidePlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rm_activity_main_video_play);

        // Ánh xạ View
        recyclerView = findViewById(R.id.rm_activity_main_video_play_rcv_01);
        imageView = findViewById(R.id.rm_activity_main_video_play_img_02);
        textViewTitle = findViewById(R.id.rm_activity_main_video_play_tv_02);
        imgBack_VidePlay = findViewById(R.id.rm_activity_main_video_play_img_01);

        // Cấu hình RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo danh sách mẫu (sau này có thể đổ từ API)
        itemList = new ArrayList<>();
        itemList.add(new MainActivity_Video_Play_Model("Gaming Stream", "Chris Doe", 512, "2 hours ago"));
        itemList.add(new MainActivity_Video_Play_Model("Gaming Stream", "Chris Doe", 512, "2 hours ago"));

        // Khởi tạo Adapter
        adapter = new MainActivity_Video_Play_Adapter(itemList);
        recyclerView.setAdapter(adapter);

        // Nhận dữ liệu từ Intent
        Intent receivedIntent = getIntent(); // Đổi tên Intent nhận dữ liệu để tránh trùng lặp
        String livestreamTitle = receivedIntent.getStringExtra("livestream_title");
        String livestreamAvatar = receivedIntent.getStringExtra("livestream_avatar");

        // Kiểm tra dữ liệu hợp lệ trước khi hiển thị
        if (livestreamTitle != null) {
            textViewTitle.setText(livestreamTitle);
        } else {
            textViewTitle.setText("No title available");
        }

        if (livestreamAvatar != null) {
            Glide.with(this).load(livestreamAvatar).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.rm_bacgroup_04);
        }
//
//        imgBack_VidePlay.setOnClickListener(v -> {
//            Intent intentBack = new Intent(MainActivity_Video_Play.this, MainActivity_List_LiveStream.class);
//            startActivity(intentBack);
//            finish();
//        });


    }
}
