package com.example.demo_livestream.Xong.MainActivity_List_LiveStream;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demo_livestream.R;
import java.util.List;

public class MainActivity_List_LiveStream_Adapter extends RecyclerView.Adapter<MainActivity_List_LiveStream_Adapter.ViewHolder> {
    private final FragmentActivity activity;
    private final List<Fragment> fragmentList_livestream;

    public MainActivity_List_LiveStream_Adapter(FragmentActivity activity, List<Fragment> fragmentList) {
        this.activity = activity;
        this.fragmentList_livestream = fragmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rm_activity_main_list_livestream_fragment_container, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.rm_activity_main_list_livestream_fragment01_container_fl_01, fragmentList_livestream.get(0));
        transaction.replace(R.id.rm_activity_main_list_livestream_fragment01_container_fl_02, fragmentList_livestream.get(1));
        transaction.replace(R.id.rm_activity_main_list_livestream_fragment01_container_fl_03, fragmentList_livestream.get(2));

        transaction.commit();
    }

    @Override
    public int getItemCount() {
        return 1; // Chỉ có một mục chứa tất cả Fragment
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
