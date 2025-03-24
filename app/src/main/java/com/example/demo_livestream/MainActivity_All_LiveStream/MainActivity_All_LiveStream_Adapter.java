package com.example.demo_livestream.MainActivity_All_LiveStream;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demo_livestream.R;
import java.util.List;

public class MainActivity_All_LiveStream_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_06 = 0;
    private static final int ITEM_TYPE_07 = 1;

    private Context context;
    private List<MainActivity_All_LiveStream_Model> itemList;

    public MainActivity_All_LiveStream_Adapter(Context context, List<MainActivity_All_LiveStream_Model> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getItemViewType(int position) {
        // Phân loại item dựa trên kiểu dữ liệu
        return itemList.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == ITEM_TYPE_06) {
            View view = inflater.inflate(R.layout.rm_activity_item06, parent, false);
            return new ViewHolder06(view);
        } else {
            View view = inflater.inflate(R.layout.rm_activity_item07, parent, false);
            return new ViewHolder07(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Không cần cập nhật dữ liệu vì chưa có dữ liệu động
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder cho rm_activity_item06
    static class ViewHolder06 extends RecyclerView.ViewHolder {
        public ViewHolder06(@NonNull View itemView) {
            super(itemView);
        }
    }

    // ViewHolder cho rm_activity_item07
    static class ViewHolder07 extends RecyclerView.ViewHolder {
        public ViewHolder07(@NonNull View itemView) {
            super(itemView);
        }
    }
}
