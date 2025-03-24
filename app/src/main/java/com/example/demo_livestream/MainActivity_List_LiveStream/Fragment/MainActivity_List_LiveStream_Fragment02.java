package com.example.demo_livestream.MainActivity_List_LiveStream.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment_Adapter.MainActivity_List_LiveStream_Fragment02_Adapter;
import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment_Model.MainActivity_List_LiveStream_Fragment02_Model;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_List_LiveStream_Fragment02 extends Fragment {

    private RecyclerView recyclerView_list_livestream_fragment02;
    private MainActivity_List_LiveStream_Fragment02_Adapter adapter_list_livestream_fragment02;
    private List<MainActivity_List_LiveStream_Fragment02_Model> List_list_livestream_fragment02;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_list_livestream_fragment02, container, false);

        recyclerView_list_livestream_fragment02 = view.findViewById(R.id.rm_activity_main_list_livestream_fragment02_rv_01);
        recyclerView_list_livestream_fragment02.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Tạo danh sách livestream mẫu
        List_list_livestream_fragment02 = new ArrayList<>();
        List_list_livestream_fragment02.add(new MainActivity_List_LiveStream_Fragment02_Model("LiveStream 1", 1200));
        List_list_livestream_fragment02.add(new MainActivity_List_LiveStream_Fragment02_Model("LiveStream 2", 980));
        List_list_livestream_fragment02.add(new MainActivity_List_LiveStream_Fragment02_Model("LiveStream 3", 1500));

        adapter_list_livestream_fragment02 = new MainActivity_List_LiveStream_Fragment02_Adapter(getContext(), List_list_livestream_fragment02);
        recyclerView_list_livestream_fragment02.setAdapter(adapter_list_livestream_fragment02);

        return view;
    }
}
