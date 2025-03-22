package com.example.demo_livestream.Api_Support;

import android.app.Activity;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

// BaseAdapter: Adapter cơ bản giúp tái sử dụng
public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {

    protected final Activity activity;
    protected final LayoutInflater layoutInflater;
    private final List<T> items = new ArrayList<>();

    public BaseAdapter(Activity activity) {
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public void setItems(@NonNull List<T> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }
}
