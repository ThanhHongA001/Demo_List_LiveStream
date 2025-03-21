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

public class MainActivity_Button extends AppCompatActivity {

    // Khai báo các Button
    private AppCompatButton btnHomeFragment01, btnHomeFragment02, btnHomeFragment03, btnHomeFragment04, btnHomeFragment05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_button);

        // Ánh xạ ID từ XML
        btnHomeFragment01 = findViewById(R.id.Activity_Home_Fragment01);
        btnHomeFragment02 = findViewById(R.id.Activity_Home_Fragment02);
        btnHomeFragment03 = findViewById(R.id.Activity_Home_Fragment03);
        btnHomeFragment04 = findViewById(R.id.Activity_Home_Fragment04);
        btnHomeFragment05 = findViewById(R.id.Activity_Home_Fragment05);

        // Thiết lập sự kiện onClick cho các Button
        btnHomeFragment01.setOnClickListener(v -> openFragment(new MainActivity_Home_Fragment01()));
        btnHomeFragment02.setOnClickListener(v -> openFragment(new MainActivity_Home_Fragment02()));
        btnHomeFragment03.setOnClickListener(v -> openFragment(new MainActivity_Home_Fragment03()));
        btnHomeFragment04.setOnClickListener(v -> openFragment(new MainActivity_Home_Fragment04()));
        btnHomeFragment05.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_Button.this, MainActivity_Home.class);
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
