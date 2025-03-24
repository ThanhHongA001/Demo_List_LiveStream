package com.example.demo_livestream;

import android.os.Bundle;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.demo_livestream.MainActivity_Home.MainActivity_Home;
import com.example.demo_livestream.MainActivity_Home.Fragment.MainActivity_Home_Fragment01;
import com.example.demo_livestream.MainActivity_Home.Fragment.MainActivity_Home_Fragment02;
import com.example.demo_livestream.MainActivity_Home.Fragment.MainActivity_Home_Fragment03;
import com.example.demo_livestream.MainActivity_Home.Fragment.MainActivity_Home_Fragment04;

import com.example.demo_livestream.MainActivity_List_LiveStream.MainActivity_List_LiveStream;
import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment.MainActivity_List_LiveStream_Fragment01;
import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment.MainActivity_List_LiveStream_Fragment02;
import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment.MainActivity_List_LiveStream_Fragment03;

public class MainActivity_Button extends AppCompatActivity {

    // Khai báo các Button
    private AppCompatButton btnHomeFragment01, btnHomeFragment02, btnHomeFragment03, btnHomeFragment04, btnActivityHome;
    private AppCompatButton btnList_LiveStream_Fragment01, btnList_LiveStream_Fragment02, btnList_LiveStream_Fragment03, btnList_LiveStream;

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

        // Thiết lập sự kiện onClick cho các Button
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

        // Thiết lập sự kiện onClick cho các Button
        btnList_LiveStream_Fragment01.setOnClickListener(v -> openFragment(new MainActivity_List_LiveStream_Fragment01()));
        btnList_LiveStream_Fragment02.setOnClickListener(v -> openFragment(new MainActivity_List_LiveStream_Fragment02()));
        btnList_LiveStream_Fragment03.setOnClickListener(v -> openFragment(new MainActivity_List_LiveStream_Fragment03()));
        btnList_LiveStream.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_Button.this, MainActivity_List_LiveStream.class);
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
