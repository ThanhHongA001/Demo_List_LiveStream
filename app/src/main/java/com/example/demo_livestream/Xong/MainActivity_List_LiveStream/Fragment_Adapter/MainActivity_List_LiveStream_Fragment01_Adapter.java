package com.example.demo_livestream.Xong.MainActivity_List_LiveStream.Fragment_Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.Xong.MainActivity_List_LiveStream.Fragment_Model.MainActivity_List_LiveStream_Fragment01_Model;
import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_List_LiveStream_Fragment01_Adapter extends RecyclerView.Adapter<MainActivity_List_LiveStream_Fragment01_Adapter.ViewHolder> {
    private List<MainActivity_List_LiveStream_Fragment01_Model> list_list_livestream_fragment01;
    private Context context_list_livestream_fragment01;
    private RecyclerView recyclerView_list_livestream_fragment01;
    private Handler handler_list_livestream_fragment01 = new Handler();
    private Runnable runnable_list_livestream_fragment01;

    public MainActivity_List_LiveStream_Fragment01_Adapter(Context context, List<MainActivity_List_LiveStream_Fragment01_Model> list, RecyclerView recyclerView) {
        this.context_list_livestream_fragment01 = context;
        this.list_list_livestream_fragment01 = list;
        this.recyclerView_list_livestream_fragment01 = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context_list_livestream_fragment01).inflate(R.layout.rm_activity_item01, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainActivity_List_LiveStream_Fragment01_Model item = list_list_livestream_fragment01.get(position % list_list_livestream_fragment01.size());
        holder.imageView_list_livestream_fragment01.setImageResource(item.getImageResource_list_livestream_fragment01());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE; // Lặp vô hạn
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_list_livestream_fragment01;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_list_livestream_fragment01 = itemView.findViewById(R.id.rm_activity_item01_iv_01);
        }
    }

    public void startAutoScroll() {
        runnable_list_livestream_fragment01 = new Runnable() {
            @Override
            public void run() {
                int currentPosition = ((LinearLayoutManager) recyclerView_list_livestream_fragment01.getLayoutManager()).findFirstVisibleItemPosition();
                recyclerView_list_livestream_fragment01.smoothScrollToPosition(currentPosition + 1);
                handler_list_livestream_fragment01.postDelayed(this, 3000);
            }
        };
        handler_list_livestream_fragment01.postDelayed(runnable_list_livestream_fragment01, 3000);
    }

    public void stopAutoScroll() {
        handler_list_livestream_fragment01.removeCallbacks(runnable_list_livestream_fragment01);
    }
}
