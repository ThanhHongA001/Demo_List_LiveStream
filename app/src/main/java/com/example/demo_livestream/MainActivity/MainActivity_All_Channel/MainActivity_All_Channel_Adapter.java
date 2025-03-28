package com.example.demo_livestream.MainActivity.MainActivity_All_Channel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo_livestream.Api.model.RMChannel;
import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_All_Channel_Adapter extends RecyclerView.Adapter<MainActivity_All_Channel_Adapter.ViewHolder> {

    private List<RMChannel> channelList;

    // Constructor
    public MainActivity_All_Channel_Adapter(List<RMChannel> channelList) {
        this.channelList = channelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rm_activity_item11, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RMChannel channel = channelList.get(position);

        // Load ảnh kênh bằng Glide
        Glide.with(holder.itemView.getContext())
                .load(channel.getImageUrl())
                .placeholder(R.drawable.rm_bacgroup_04) // Ảnh mặc định nếu chưa load được
                .into(holder.imgAvatar);

        // Gán dữ liệu vào các thành phần giao diện
        holder.tvName.setText(channel.getName());
        holder.tvFollowers.setText(channel.getNumfollow() + " Followers");
        holder.tvVideos.setText(channel.getNumVideo() + " Videos");

//         Xử lý sự kiện nhấn theo dõi
        holder.btnFollow.setOnClickListener(v -> {
            // TODO: Thêm logic follow/unfollow tại đây
        });

    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvName, tvFollowers, tvVideos;
        Button btnFollow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.rm_activity_item11_img_01);
            tvName = itemView.findViewById(R.id.rm_activity_item11_tv_01);
            tvFollowers = itemView.findViewById(R.id.rm_activity_item11_tv_02);
            tvVideos = itemView.findViewById(R.id.rm_activity_item11_tv_04);
            btnFollow = itemView.findViewById(R.id.rm_activity_item11_btn_01);
        }

        // Cập nhật UI nút Follow
        private void updateFollowButton(Button button, int isFollow) {
            if (isFollow == 1) {
                button.setText("Unfollow");
                button.setBackgroundResource(R.drawable.rm_ic_button_folow_01);
            } else {
                button.setText("Follow");
                button.setBackgroundResource(R.drawable.rm_ic_button_unfolow_01);
            }
        }

    }
}
