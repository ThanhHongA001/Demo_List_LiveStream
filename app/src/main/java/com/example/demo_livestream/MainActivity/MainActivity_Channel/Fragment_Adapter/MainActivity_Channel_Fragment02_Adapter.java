package com.example.demo_livestream.MainActivity.MainActivity_Channel.Fragment_Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.MainActivity.MainActivity_Channel.Fragment_Model.MainActivity_Channel_Fragment02_Model;
import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_Channel_Fragment02_Adapter extends RecyclerView.Adapter<MainActivity_Channel_Fragment02_Adapter.ViewHolder> {

    private List<MainActivity_Channel_Fragment02_Model> itemList;

    public MainActivity_Channel_Fragment02_Adapter(List<MainActivity_Channel_Fragment02_Model> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rm_activity_item10, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainActivity_Channel_Fragment02_Model item = itemList.get(position);

        holder.title.setText(item.getTitle());
        holder.author.setText(item.getAuthor());
        holder.views.setText(item.getViews());
        holder.time.setText(item.getTime());
        holder.thumbnail.setImageResource(item.getThumbnailResId());
        holder.liveIcon.setImageResource(item.getLiveIconResId());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail, liveIcon;
        TextView title, author, views, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.rm_activity_item10_img_01);
            liveIcon = itemView.findViewById(R.id.rm_activity_item10_img_02);
            title = itemView.findViewById(R.id.rm_activity_item10_tv_01);
            author = itemView.findViewById(R.id.rm_activity_item10_tv_02);
            views = itemView.findViewById(R.id.rm_activity_item10_tv_03);
            time = itemView.findViewById(R.id.rm_activity_item10_tv_05);
        }
    }
}
