package com.example.demo_livestream;

import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.demo_livestream.MainActivity_All_Channel.MainActivity_All_Channel;
import com.example.demo_livestream.MainActivity_Channel.MainActivity_Channel;
import com.example.demo_livestream.MainActivity_Channel.Fragment.MainActivity_Channel_Fragment01;
import com.example.demo_livestream.MainActivity_Channel.Fragment.MainActivity_Channel_Fragment02;
import com.example.demo_livestream.MainActivity_Search.Fragment.MainActivity_Search_Fragment01;
import com.example.demo_livestream.MainActivity_Search.Fragment.MainActivity_Search_Fragment02;
import com.example.demo_livestream.MainActivity_Search.MainActivity_Search;
import com.example.demo_livestream.MainActivity_Video_Play.MainActivity_Video_Play;
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

    private AppCompatButton btnHomeFragment01, btnHomeFragment02, btnHomeFragment03, btnHomeFragment04, btnActivityHome;
    private AppCompatButton btnList_LiveStream_Fragment01, btnList_LiveStream_Fragment02, btnList_LiveStream_Fragment03, btnList_LiveStream;
    private AppCompatButton btnAll_LiveStream;
    private AppCompatButton btnStar;
    private AppCompatButton btnVideo_Live;
    private AppCompatButton btnAll_Channel;
    private AppCompatButton btnChannel_Fragment01, btnChannel_Fragment02, btnChannel;
    private AppCompatButton btnSearch_Fragment01, btnSearch_Fragment02, btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_button);

        // Home
        btnHomeFragment01 = findViewById(R.id.MainActivity_Home_Fragment_01);
        btnHomeFragment02 = findViewById(R.id.MainActivity_Home_Fragment_02);
        btnHomeFragment03 = findViewById(R.id.MainActivity_Home_Fragment_03);
        btnHomeFragment04 = findViewById(R.id.MainActivity_Home_Fragment_04);
        btnActivityHome = findViewById(R.id.MainActivity_Home_Activity);
        btnHomeFragment01.setOnClickListener(v -> openFragment(new MainActivity_Home_Fragment01()));
        btnHomeFragment02.setOnClickListener(v -> openFragment(new MainActivity_Home_Fragment02()));
        btnHomeFragment03.setOnClickListener(v -> openFragment(new MainActivity_Home_Fragment03()));
        btnHomeFragment04.setOnClickListener(v -> openFragment(new MainActivity_Home_Fragment04()));
        btnActivityHome.setOnClickListener(v -> startActivity(new Intent(this, MainActivity_Home.class)));

        // LiveStream
        btnList_LiveStream_Fragment01 = findViewById(R.id.MainActivity_List_LiveStream_Fragment_01);
        btnList_LiveStream_Fragment02 = findViewById(R.id.MainActivity_List_LiveStream_Fragment_02);
        btnList_LiveStream_Fragment03 = findViewById(R.id.MainActivity_List_LiveStream_Fragment_03);
        btnList_LiveStream = findViewById(R.id.MainActivity_List_LiveStream_Activity);
        btnList_LiveStream_Fragment01.setOnClickListener(v -> openFragment(new MainActivity_List_LiveStream_Fragment01()));
        btnList_LiveStream_Fragment02.setOnClickListener(v -> openFragment(new MainActivity_List_LiveStream_Fragment02()));
        btnList_LiveStream_Fragment03.setOnClickListener(v -> openFragment(new MainActivity_List_LiveStream_Fragment03()));
        btnList_LiveStream.setOnClickListener(v -> startActivity(new Intent(this, MainActivity_List_LiveStream.class)));

        // All LiveStream
        btnAll_LiveStream = findViewById(R.id.MainActivity_All_LiveStream_Activity);
        btnAll_LiveStream.setOnClickListener(v -> startActivity(new Intent(this, MainActivity_All_LiveStream.class)));

        // Star
        btnStar = findViewById(R.id.MainActivity_Star_Activity);
        btnStar.setOnClickListener(v -> startActivity(new Intent(this, MainActivity_Star.class)));

        // Video Live
        btnVideo_Live = findViewById(R.id.MainActivity_Video_Live_Activity);
        btnVideo_Live.setOnClickListener(v -> startActivity(new Intent(this, MainActivity_Video_Play.class)));

        // All Channel
        btnAll_Channel = findViewById(R.id.MainActivity_All_Channel_Activity);
        btnAll_Channel.setOnClickListener(v -> startActivity(new Intent(this, MainActivity_All_Channel.class)));

        // Channel
        btnChannel_Fragment01 = findViewById(R.id.MainActivity_Channel_Fragment_01);
        btnChannel_Fragment02 = findViewById(R.id.MainActivity_Channel_Fragment_02);
        btnChannel = findViewById(R.id.MainActivity_Channel_Activity);
        btnChannel_Fragment01.setOnClickListener(v -> openFragment(new MainActivity_Channel_Fragment01()));
        btnChannel_Fragment02.setOnClickListener(v -> openFragment(new MainActivity_Channel_Fragment02()));
        btnChannel.setOnClickListener(v -> startActivity(new Intent(this, MainActivity_Channel.class)));

        // Search
        btnSearch_Fragment01 = findViewById(R.id.MainActivity_Search_Fragment_01);
        btnSearch_Fragment02 = findViewById(R.id.MainActivity_Search_Fragment_02);
        btnSearch = findViewById(R.id.MainActivity_Search_Activity);
        btnSearch_Fragment01.setOnClickListener(v -> openFragment(new MainActivity_Search_Fragment01()));
        btnSearch_Fragment02.setOnClickListener(v -> openFragment(new MainActivity_Search_Fragment02()));
        btnSearch.setOnClickListener(v -> startActivity(new Intent(this, MainActivity_Search.class)));
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
