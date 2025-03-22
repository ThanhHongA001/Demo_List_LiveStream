package com.example.demo_livestream.MainActivity_Home.Fragment_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo_livestream.MainActivity_Home.Fragment_Model.MainActivity_Home_Fragment01_Model;
import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_Home_Fragment01_Adapter extends RecyclerView.Adapter<MainActivity_Home_Fragment01_Adapter.ViewHolder> {

    private List<MainActivity_Home_Fragment01_Model> itemList;
    private Context context;

    public MainActivity_Home_Fragment01_Adapter(Context context, List<MainActivity_Home_Fragment01_Model> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rm_activity_item01, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainActivity_Home_Fragment01_Model item = itemList.get(position);

        // Load ảnh từ API bằng Glide
        Glide.with(context)
                .load(item.getThumbnail())
                .placeholder(R.drawable.rm_bacgroup_01)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rm_activity_item01_iv_01);
        }
    }
}
