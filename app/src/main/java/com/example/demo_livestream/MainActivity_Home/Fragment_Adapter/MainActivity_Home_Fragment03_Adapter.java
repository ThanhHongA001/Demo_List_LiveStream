package com.example.demo_livestream.MainActivity_Home.Fragment_Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.MainActivity_Home.Fragment_Model.MainActivity_Home_Fragment03_Model;
import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_Home_Fragment03_Adapter extends RecyclerView.Adapter<MainActivity_Home_Fragment03_Adapter.ViewHolder> {
    private List<MainActivity_Home_Fragment03_Model> dataList;

    public MainActivity_Home_Fragment03_Adapter(List<MainActivity_Home_Fragment03_Model> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rm_activity_item03, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainActivity_Home_Fragment03_Model item = dataList.get(position);
        holder.tvPrice.setText(item.getPrice_home_fragment03());
        holder.tvPackId.setText(item.getPackId_home_fragment03());
        holder.tvDetails.setText(item.getDetails_home_fragment03());
        holder.tvValidity.setText(item.getValidity_home_fragment03());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPrice, tvPackId, tvDetails, tvValidity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrice = itemView.findViewById(R.id.rm_activity_item03_tv_01);
            tvPackId = itemView.findViewById(R.id.rm_activity_item03_tv_03);
            tvDetails = itemView.findViewById(R.id.rm_activity_item03_tv_04);
            tvValidity = itemView.findViewById(R.id.rm_activity_item03_tv_05);
        }
    }
}
