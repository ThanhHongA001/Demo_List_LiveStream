package com.example.demo_livestream.Xong.MainActivity_List_LiveStream.Fragment_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.Xong.MainActivity_List_LiveStream.Fragment_Model.MainActivity_List_LiveStream_Fragment02_Model;
import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_List_LiveStream_Fragment02_Adapter extends RecyclerView.Adapter<MainActivity_List_LiveStream_Fragment02_Adapter.ViewHolder> {

    private Context context_list_livestream_fragment02;
    private List<MainActivity_List_LiveStream_Fragment02_Model> List_list_livestream_fragment02;

    public MainActivity_List_LiveStream_Fragment02_Adapter(Context context, List<MainActivity_List_LiveStream_Fragment02_Model> livestreamList) {
        this.context_list_livestream_fragment02 = context;
        this.List_list_livestream_fragment02 = livestreamList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context_list_livestream_fragment02).inflate(R.layout.rm_activity_item02, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainActivity_List_LiveStream_Fragment02_Model livestream = List_list_livestream_fragment02.get(position);

        // Cập nhật dữ liệu vào item
        holder.livestreamName.setText(livestream.getTitle_list_livestream_fragment02());
        holder.viewCount.setText(livestream.getViewCount_list_livestream_fragment02() + " View");

        // Nếu có hình ảnh, bạn có thể sử dụng Glide/Picasso để load
        holder.livestreamImage.setImageResource(R.drawable.rm_bacgroup_01); // Tạm thời đặt ảnh mặc định
    }

    @Override
    public int getItemCount() {
        return List_list_livestream_fragment02.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView livestreamImage, viewIcon;
        TextView livestreamName, viewCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            livestreamImage = itemView.findViewById(R.id.rm_activity_item02_iv_01);
            livestreamName = itemView.findViewById(R.id.rm_activity_item02_tv_01);
            viewIcon = itemView.findViewById(R.id.rm_activity_item02_iv_02);
            viewCount = itemView.findViewById(R.id.rm_activity_item02_tv_02);
        }
    }
}
