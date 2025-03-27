package com.example.demo_livestream.MainActivity_List_LiveStream.Fragment_Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo_livestream.Api.Model.LiveStream_List_Model;
import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_List_LiveStream_Fragment01_Adapter extends RecyclerView.Adapter<MainActivity_List_LiveStream_Fragment01_Adapter.ViewHolder> {
    private List<LiveStream_List_Model> list_list_livestream_fragment01;
    private Context context_list_livestream_fragment01;
    private RecyclerView recyclerView_list_livestream_fragment01;
    private Handler handler_list_livestream_fragment01 = new Handler();
    private Runnable runnable_list_livestream_fragment01;

    public MainActivity_List_LiveStream_Fragment01_Adapter(Context context, List<LiveStream_List_Model> list, RecyclerView recyclerView) {
        this.context_list_livestream_fragment01 = context;
        this.list_list_livestream_fragment01 = list;
        this.recyclerView_list_livestream_fragment01 = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context_list_livestream_fragment01).inflate(R.layout.rm_activity_item01, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list_list_livestream_fragment01 == null || list_list_livestream_fragment01.isEmpty()) {
            return;
        }

        // Lấy dữ liệu từ danh sách
        LiveStream_List_Model item = list_list_livestream_fragment01.get(position % list_list_livestream_fragment01.size());

        // Load ảnh từ URL vào ImageView bằng Glide
        Glide.with(context_list_livestream_fragment01)
                .load(item.getLinkAvatar()) // Lấy URL từ Model
                .placeholder(R.drawable.rm_bacgroup_01) // Ảnh tạm thời khi tải
                .error(R.drawable.ic_launcher_background) // Ảnh lỗi nếu không tải được
                .into(holder.imageView_list_livestream_fragment01);
    }

    @Override
    public int getItemCount() {
        return list_list_livestream_fragment01 == null ? 0 : Integer.MAX_VALUE; // Lặp vô hạn
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_list_livestream_fragment01;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                    }
    }

    // Tự động cuộn RecyclerView
    public void startAutoScroll() {

    }

    // Dừng tự động cuộn
    public void stopAutoScroll() {
        handler_list_livestream_fragment01.removeCallbacks(runnable_list_livestream_fragment01);
    }
}
