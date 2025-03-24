package com.example.demo_livestream.MainActivity_Home.Fragment_Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.MainActivity_Home.Fragment_Model.MainActivity_Home_Fragment01_Model;
import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_Home_Fragment01_Adapter extends RecyclerView.Adapter<MainActivity_Home_Fragment01_Adapter.ViewHolder> {
    private List<MainActivity_Home_Fragment01_Model> list_home_fragment01;
    private Context context_home_fragment01;
    private RecyclerView recyclerView_home_livestream;
    private Handler handler_home_livestream = new Handler();
    private Runnable runnable_home_livestream;

    // Constructor Adapter
    public MainActivity_Home_Fragment01_Adapter(Context context_home_fragment01, List<MainActivity_Home_Fragment01_Model> list_home_fragment01, RecyclerView recyclerView) {
        this.context_home_fragment01 = context_home_fragment01;
        this.list_home_fragment01 = list_home_fragment01;
        this.recyclerView_home_livestream = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context_home_fragment01).inflate(R.layout.rm_activity_item01, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainActivity_Home_Fragment01_Model item = list_home_fragment01.get(position % list_home_fragment01.size());
        holder.imageView_home_fragment01.setImageResource(item.getImageResId_home_livestream());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE; // Giúp RecyclerView có thể lặp vô hạn
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_home_fragment01;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_home_fragment01 = itemView.findViewById(R.id.rm_activity_item01_iv_01);
        }
    }

    // Bắt đầu auto-scroll
    public void startAutoScroll() {
        runnable_home_livestream = new Runnable() {
            @Override
            public void run() {
                int currentPosition = ((LinearLayoutManager) recyclerView_home_livestream.getLayoutManager()).findFirstVisibleItemPosition();
                recyclerView_home_livestream.smoothScrollToPosition(currentPosition + 1);
                handler_home_livestream.postDelayed(this, 3000); // Lặp lại sau 3 giây
            }
        };
        handler_home_livestream.postDelayed(runnable_home_livestream, 3000);
    }

    // Dừng auto-scroll khi không cần thiết
    public void stopAutoScroll() {
        handler_home_livestream.removeCallbacks(runnable_home_livestream);
    }
}
