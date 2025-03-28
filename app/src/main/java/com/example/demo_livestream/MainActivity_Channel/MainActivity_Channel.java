package com.example.demo_livestream.MainActivity_Channel;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.demo_livestream.MainActivity_Channel.Fragment.MainActivity_Channel_Fragment01;
import com.example.demo_livestream.MainActivity_Channel.Fragment.MainActivity_Channel_Fragment02;
import com.example.demo_livestream.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Channel extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TextView tvChannelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.rm_activity_main_channel);

        // Ánh xạ View
        tabLayout = findViewById(R.id.rm_activity_main_channel_tb_01);
        viewPager2 = findViewById(R.id.rm_activity_main_channel_vp2_01);


        // Thiết lập Adapter cho ViewPager2
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        // Liên kết TabLayout với ViewPager2
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) {
                tab.setText("Information");
            } else {
                tab.setText("Video");
            }
        }).attach();

        tvChannelName = findViewById(R.id.rm_activity_main_channel_tv_01);

        // Lấy dữ liệu từ Intent
        String channelId = getIntent().getStringExtra("channel_id");
        String channelName = getIntent().getStringExtra("channel_name");

        // Hiển thị tên kênh (nếu cần)
        if (channelName != null) {
            tvChannelName.setText("Kênh: " + channelName);
        }
    }

    // Adapter quản lý Fragment cho ViewPager2
    private static class ViewPagerAdapter extends FragmentStateAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull AppCompatActivity fragmentActivity) {
            super(fragmentActivity);
            fragmentList.add(new MainActivity_Channel_Fragment01()); // Fragment "Information"
            fragmentList.add(new MainActivity_Channel_Fragment02()); // Fragment "Video"
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }
}
