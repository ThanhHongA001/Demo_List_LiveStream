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

public class MainActivity_ListLiveStream_Banner_Adapter extends RecyclerView.Adapter<MainActivity_ListLiveStream_Banner_Adapter.ViewHolder> {

    private final Context context;
    private final List<RMLivestream> bannerList;
    private OnItemClickListener listener;

    // Interface để bắt sự kiện click vào item
    public interface OnItemClickListener {
        void onItemClick(RMLivestream livestream);
    }

    // Hàm set sự kiện click
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MainActivity_ListLiveStream_Banner_Adapter(Context context, List<RMLivestream> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rm_activity_item01, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RMLivestream bannerItem = bannerList.get(position);

        // Load hình ảnh từ API
        Glide.with(context)
                .load(bannerItem.getLinkAvatar())
                .placeholder(R.drawable.rm_bacgroup_01)
                .into(holder.imageView);

        holder.textView.setText(bannerItem.getTitle());

        // Bắt sự kiện click vào item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(bannerItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    // Hàm cập nhật danh sách banner từ API
    public void updateData(List<RMLivestream> newBannerList) {
        bannerList.clear();
        bannerList.addAll(newBannerList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rm_activity_item01_iv_01);
            textView = itemView.findViewById(R.id.rm_activity_item01_tv_01);
        }
    }
}
