package com.example.demo_livestream.MainActivity_Home.Fragment_Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.MainActivity_Home.Fragment_Model.MainActivity_Home_Fragment04_Model;
import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_Home_Fragment04_Adapter extends RecyclerView.Adapter<MainActivity_Home_Fragment04_Adapter.ViewHolder> {

    private List<MainActivity_Home_Fragment04_Model> list_home_fragment04;

    public MainActivity_Home_Fragment04_Adapter(List<MainActivity_Home_Fragment04_Model> list_home_fragment04) {
        this.list_home_fragment04 = list_home_fragment04;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rm_activity_item04, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainActivity_Home_Fragment04_Model item = list_home_fragment04.get(position);

        holder.imageView_home_fragment04.setImageResource(item.getImageResource_home_fragment04());
        holder.textViewName_home_fragment04.setText(item.getLivestreamName_home_fragment04());
        holder.textViewViews_home_fragment04.setText(item.getViewCount_home_fragment04() + " View");
    }

    @Override
    public int getItemCount() {
        return list_home_fragment04.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_home_fragment04;
        TextView textViewName_home_fragment04, textViewViews_home_fragment04;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_home_fragment04 = itemView.findViewById(R.id.rm_activity_item04_iv_01);
            textViewName_home_fragment04 = itemView.findViewById(R.id.rm_activity_item04_tv_01);
            textViewViews_home_fragment04 = itemView.findViewById(R.id.rm_activity_item04_tv_02);
        }
    }
}
