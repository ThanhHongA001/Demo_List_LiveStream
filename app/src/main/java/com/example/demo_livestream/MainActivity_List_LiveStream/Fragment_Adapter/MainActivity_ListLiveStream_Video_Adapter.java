package com.example.demo_livestream.MainActivity_List_LiveStream.Fragment_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo_livestream.Api.model.RMLivestream;
import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_ListLiveStream_Video_Adapter extends RecyclerView.Adapter<MainActivity_ListLiveStream_Video_Adapter.ViewHolder> {
    private Context context;
    private List<RMLivestream> videoList;

    public MainActivity_ListLiveStream_Video_Adapter(Context context, List<RMLivestream> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rm_activity_item02, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RMLivestream videoItem = videoList.get(position);

        // Set dữ liệu vào TextView
        holder.textView.setText(videoItem.getTitle());

        // Load ảnh bằng Glide
        Glide.with(context)
                .load(videoItem.getLinkAvatar())
                .placeholder(R.drawable.rm_bacgroup_03)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    // Hàm cập nhật dữ liệu mới
    public void updateData(List<RMLivestream> newList) {
        videoList.clear();
        videoList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rm_activity_item02_iv_01);
            textView = itemView.findViewById(R.id.rm_activity_item02_tv_01);
        }
    }
}
