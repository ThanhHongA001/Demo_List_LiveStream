package com.example.demo_livestream.MainActivity.MainActivity_Search.Fragment_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.MainActivity.MainActivity_Search.Fragment_Model.MainActivity_Search_Fragment01_Model;
import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_Search_Fragment01_Adapter extends RecyclerView.Adapter<MainActivity_Search_Fragment01_Adapter.ViewHolder> {

    private Context context;
    private List<MainActivity_Search_Fragment01_Model> itemList;

    public MainActivity_Search_Fragment01_Adapter(Context context, List<MainActivity_Search_Fragment01_Model> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rm_activity_item11, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainActivity_Search_Fragment01_Model item = itemList.get(position);
        holder.imgProfile.setImageResource(item.getImageResource());
        holder.tvName.setText(item.getName());
        holder.tvFollowerCount.setText(item.getFollowerCount());
        holder.tvVideoCount.setText(item.getVideoCount());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProfile;
        TextView tvName, tvFollowerCount, tvVideoCount;
        LinearLayout lnDetails;
        Button btnFollow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.rm_activity_item11_img_01);
            tvName = itemView.findViewById(R.id.rm_activity_item11_tv_01);
            tvFollowerCount = itemView.findViewById(R.id.rm_activity_item11_tv_02);
            tvVideoCount = itemView.findViewById(R.id.rm_activity_item11_tv_04);
            lnDetails = itemView.findViewById(R.id.rm_activity_item11_ln_01);
            btnFollow = itemView.findViewById(R.id.rm_activity_item11_btn_01);
        }
    }
}
