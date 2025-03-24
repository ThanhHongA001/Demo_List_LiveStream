package com.example.demo_livestream.Xong.MainActivity_Star;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.R;

import java.util.List;

public class MainActivity_Star_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MainActivity_Star_Model> itemList;

    public MainActivity_Star_Adapter(List<MainActivity_Star_Model> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getViewType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MainActivity_Star_Model.TYPE_ITEM_08) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rm_activity_item08, parent, false);
            return new ViewHolderItem08(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rm_activity_item09, parent, false);
            return new ViewHolderItem09(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MainActivity_Star_Model item = itemList.get(position);

        if (holder instanceof ViewHolderItem08) {
            ViewHolderItem08 viewHolder = (ViewHolderItem08) holder;
            viewHolder.icon.setImageResource(item.getIcon());
            viewHolder.title.setText(item.getTitle());
            viewHolder.time.setText(item.getTime());
            viewHolder.starCount.setText(String.valueOf(item.getStarCount()));
        } else if (holder instanceof ViewHolderItem09) {
            ViewHolderItem09 viewHolder = (ViewHolderItem09) holder;
            viewHolder.icon.setImageResource(item.getIcon());
            viewHolder.title.setText(item.getTitle());
            viewHolder.time.setText(item.getTime());
            viewHolder.starCount.setText(String.valueOf(item.getStarCount()));
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolderItem08 extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title, time, starCount;

        public ViewHolderItem08(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.rm_activity_item08_img_01);
            title = itemView.findViewById(R.id.rm_activity_item08_tv_01);
            time = itemView.findViewById(R.id.rm_activity_item08_tv_02);
            starCount = itemView.findViewById(R.id.rm_activity_item08_tv_03);
        }
    }

    static class ViewHolderItem09 extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title, time, starCount;

        public ViewHolderItem09(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.rm_activity_item09_img_01);
            title = itemView.findViewById(R.id.rm_activity_item09_tv_01);
            time = itemView.findViewById(R.id.rm_activity_item09_tv_02);
            starCount = itemView.findViewById(R.id.rm_activity_item09_tv_03);
        }
    }
}
