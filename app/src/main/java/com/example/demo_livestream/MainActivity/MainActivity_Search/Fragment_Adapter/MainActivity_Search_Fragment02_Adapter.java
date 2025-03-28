package com.example.demo_livestream.MainActivity.MainActivity_Search.Fragment_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.MainActivity.MainActivity_Search.Fragment_Model.MainActivity_Search_Fragment02_Model;
import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_Search_Fragment02_Adapter extends RecyclerView.Adapter<MainActivity_Search_Fragment02_Adapter.ViewHolder> {

    private Context context;
    private List<MainActivity_Search_Fragment02_Model> itemList;

    public MainActivity_Search_Fragment02_Adapter(Context context, List<MainActivity_Search_Fragment02_Model> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rm_activity_item10, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainActivity_Search_Fragment02_Model item = itemList.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvStreamer.setText(item.getStreamer());
        holder.tvViews.setText(item.getViews());
        holder.tvTime.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStreamer, tvViews, tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.rm_activity_item10_tv_01);
            tvStreamer = itemView.findViewById(R.id.rm_activity_item10_tv_02);
            tvViews = itemView.findViewById(R.id.rm_activity_item10_tv_03);
            tvTime = itemView.findViewById(R.id.rm_activity_item10_tv_05);
        }
    }
}
