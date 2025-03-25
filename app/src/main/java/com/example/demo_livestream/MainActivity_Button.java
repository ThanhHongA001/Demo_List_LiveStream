package com.example.demo_livestream;

import android.os.Bundle;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.demo_livestream.MainActivity_All_Channel.MainActivity_All_Channel;
import com.example.demo_livestream.MainActivity_Channel.MainActivity_Channel;
import com.example.demo_livestream.MainActivity_Channel.MainActivity_Channel_Fragment01;
import com.example.demo_livestream.MainActivity_Channel.MainActivity_Channel_Fragment02;
import com.example.demo_livestream.MainActivity_Video_Live.MainActivity_Video_Live;

import com.example.demo_livestream.MainActivity_All_LiveStream.MainActivity_All_LiveStream;

import com.example.demo_livestream.MainActivity_Home.MainActivity_Home;
import com.example.demo_livestream.MainActivity_Home.Fragment.MainActivity_Home_Fragment01;
import com.example.demo_livestream.MainActivity_Home.Fragment.MainActivity_Home_Fragment02;
import com.example.demo_livestream.MainActivity_Home.Fragment.MainActivity_Home_Fragment03;
import com.example.demo_livestream.MainActivity_Home.Fragment.MainActivity_Home_Fragment04;

import com.example.demo_livestream.MainActivity_List_LiveStream.MainActivity_List_LiveStream;
import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment.MainActivity_List_LiveStream_Fragment01;
import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment.MainActivity_List_LiveStream_Fragment02;
import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment.MainActivity_List_LiveStream_Fragment03;

import com.example.demo_livestream.MainActivity_Star.MainActivity_Star;

public class MainActivity_Button extends AppCompatActivity {

    // Khai báo các Button
    private AppCompatButton btnHomeFragment01, btnHomeFragment02, btnHomeFragment03, btnHomeFragment04, btnActivityHome;
    private AppCompatButton btnList_LiveStream_Fragment01, btnList_LiveStream_Fragment02, btnList_LiveStream_Fragment03, btnList_LiveStream;
    private AppCompatButton btnAll_LiveStream;
    private AppCompatButton btnStar;
    private AppCompatButton btnVideo_Live;
    private AppCompatButton btnAll_Channel;
    private AppCompatButton btnChannel_Fragment01,btnChannel_Fragment02,btnChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_button);

        // Ánh xạ ID từ XML
        btnHomeFragment01 = findViewById(R.id.MainActivity_Home_Fragment01);
        btnHomeFragment02 = findViewById(R.id.MainActivity_Home_Fragment02);
        btnHomeFragment03 = findViewById(R.id.MainActivity_Home_Fragment03);
        btnHomeFragment04 = findViewById(R.id.MainActivity_Home_Fragment04);
        btnActivityHome = findViewById(R.id.MainActivity_Home);
        btnHomeFragment01.setOnClickListener(v -> openFragment(new MainActivity_Home_Fragment01()));
        btnHomeFragment02.setOnClickListener(v -> openFragment(new MainActivity_Home_Fragment02()));
        btnHomeFragment03.setOnClickListener(v -> openFragment(new MainActivity_Home_Fragment03()));
        btnHomeFragment04.setOnClickListener(v -> openFragment(new MainActivity_Home_Fragment04()));
        btnActivityHome.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_Button.this, MainActivity_Home.class);
            startActivity(intent);
        });

        // Ánh xạ ID từ XML
        btnList_LiveStream_Fragment01 = findViewById(R.id.MainActivity_List_LiveStream_Fragment01);
        btnList_LiveStream_Fragment02 = findViewById(R.id.MainActivity_List_LiveStream_Fragment02);
        btnList_LiveStream_Fragment03 = findViewById(R.id.MainActivity_List_LiveStream_Fragment03);
        btnList_LiveStream = findViewById(R.id.MainActivity_List_LiveStream);
        btnList_LiveStream_Fragment01.setOnClickListener(v -> openFragment(new MainActivity_List_LiveStream_Fragment01()));
        btnList_LiveStream_Fragment02.setOnClickListener(v -> openFragment(new MainActivity_List_LiveStream_Fragment02()));
        btnList_LiveStream_Fragment03.setOnClickListener(v -> openFragment(new MainActivity_List_LiveStream_Fragment03()));
        btnList_LiveStream.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_Button.this, MainActivity_List_LiveStream.class);
            startActivity(intent);
        });

        btnAll_LiveStream = findViewById(R.id.MainActivity_All_LiveStream);
        btnAll_LiveStream.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_Button.this, MainActivity_All_LiveStream.class);
            startActivity(intent);
        });

        btnStar = findViewById(R.id.MainActivity_Star);
        btnStar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_Button.this, MainActivity_Star.class);
            startActivity(intent);
        });

        btnVideo_Live = findViewById(R.id.MainActivity_Video_Live);
        btnVideo_Live.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_Button.this, MainActivity_Video_Live.class);
            startActivity(intent);
        });

        btnAll_Channel = findViewById(R.id.Activity_All_Channel);
        btnAll_Channel.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_Button.this, MainActivity_All_Channel.class);
            startActivity(intent);
        });

        btnChannel_Fragment01 = findViewById(R.id.Activity_Channel_Fragment01);
        btnChannel_Fragment02 = findViewById(R.id.Activity_Channel_Fragment02);
        btnChannel = findViewById(R.id.Activity_Channel);
        btnChannel_Fragment01.setOnClickListener(v -> openFragment(new MainActivity_Channel_Fragment01()));
        btnChannel_Fragment02.setOnClickListener(v -> openFragment(new MainActivity_Channel_Fragment02()));
        btnChannel.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_Button.this, MainActivity_Channel.class);
            startActivity(intent);
        });

    }

    // Hàm mở Fragment mới
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
