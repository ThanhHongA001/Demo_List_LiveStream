package com.example.demo_livestream.MainActivity_List_LiveStream.Fragment_Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo_livestream.Api.model.RMChannel;
import com.example.demo_livestream.MainActivity_Channel.MainActivity_Channel;
import com.example.demo_livestream.MainActivity_Video_Play.MainActivity_Video_Play;
import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_ListLiveStream_Channel_Adapter extends RecyclerView.Adapter<MainActivity_ListLiveStream_Channel_Adapter.ViewHolder> {

    private Context context;
    private List<RMChannel> channelList;

    public MainActivity_ListLiveStream_Channel_Adapter(Context context, List<RMChannel> channelList) {
        this.context = context;
        this.channelList = channelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rm_activity_item05, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RMChannel channel = channelList.get(position);

        // Load ảnh vào ImageView
        Glide.with(context).load(channel.getImageUrl())
                .into(holder.channelImage);

        // Set tên kênh
        holder.channelName.setText(channel.getName());

        // Xử lý sự kiện click vào item trong RecyclerView
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity_Channel.class);
            intent.putExtra("channel_id", channel.getId()); // Truyền ID kênh qua Intent
            intent.putExtra("channel_name", channel.getName()); // Truyền tên kênh
            intent.putExtra("channel_image", channel.getImageUrl()); // Truyền ảnh kênh

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    // Hàm cập nhật danh sách channel từ API
    public void updateData(List<RMChannel> newChannelList) {
        channelList.clear();
        channelList.addAll(newChannelList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView channelName;
        ImageView channelImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            channelName = itemView.findViewById(R.id.rm_activity_item05_tv_01);
            channelImage = itemView.findViewById(R.id.rm_activity_item05_img_01);
        }
    }
}
