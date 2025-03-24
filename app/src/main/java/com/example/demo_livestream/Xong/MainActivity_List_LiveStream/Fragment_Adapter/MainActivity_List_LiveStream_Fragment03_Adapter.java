package com.example.demo_livestream.Xong.MainActivity_List_LiveStream.Fragment_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.Xong.MainActivity_List_LiveStream.Fragment_Model.MainActivity_List_LiveStream_Fragment03_Model;
import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_List_LiveStream_Fragment03_Adapter extends RecyclerView.Adapter<MainActivity_List_LiveStream_Fragment03_Adapter.ViewHolder> {

    private Context context_list_livestream_fragment03;
    private List<MainActivity_List_LiveStream_Fragment03_Model> List_list_livestream_fragment03;

    public MainActivity_List_LiveStream_Fragment03_Adapter(Context context_list_livestream_fragment03, List<MainActivity_List_LiveStream_Fragment03_Model> List_list_livestream_fragment03) {
        this.context_list_livestream_fragment03 = context_list_livestream_fragment03;
        this.List_list_livestream_fragment03 = List_list_livestream_fragment03;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context_list_livestream_fragment03).inflate(R.layout.rm_activity_item05, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainActivity_List_LiveStream_Fragment03_Model item = List_list_livestream_fragment03.get(position);
        holder.imageView.setImageResource(item.getImageResId_list_livestream_fragment03());
        holder.textView.setText(item.getName_list_livestream_fragment03());
    }

    @Override
    public int getItemCount() {
        return List_list_livestream_fragment03.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rm_activity_item05_img_01);
            textView = itemView.findViewById(R.id.rm_activity_item05_tv_01);
        }
    }
}
