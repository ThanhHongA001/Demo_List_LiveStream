package com.example.demo_livestream.MainActivity_List_LiveStream.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment_Adapter.MainActivity_List_LiveStream_Fragment03_Adapter;
import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment_Model.MainActivity_List_LiveStream_Fragment03_Model;
import com.example.demo_livestream.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_List_LiveStream_Fragment03 extends Fragment {

    private RecyclerView recyclerView_list_livestream_fragment03;
    private MainActivity_List_LiveStream_Fragment03_Adapter adapter_list_livestream_fragment03;
    private List<MainActivity_List_LiveStream_Fragment03_Model> List_list_livestream_fragment03;

    public MainActivity_List_LiveStream_Fragment03() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rm_activity_main_list_livestream_fragment03, container, false);
        recyclerView_list_livestream_fragment03 = view.findViewById(R.id.rm_activity_main_list_livestream_fragment03_rv_01);

        // Khởi tạo danh sách dữ liệu
        List_list_livestream_fragment03 = new ArrayList<>();
        List_list_livestream_fragment03.add(new MainActivity_List_LiveStream_Fragment03_Model("John", R.drawable.rm_bacgroup_04));
        List_list_livestream_fragment03.add(new MainActivity_List_LiveStream_Fragment03_Model("Emma", R.drawable.rm_bacgroup_04));
        List_list_livestream_fragment03.add(new MainActivity_List_LiveStream_Fragment03_Model("Liam", R.drawable.rm_bacgroup_04));

        // Thiết lập Adapter
        adapter_list_livestream_fragment03 = new MainActivity_List_LiveStream_Fragment03_Adapter(getContext(), List_list_livestream_fragment03);
        recyclerView_list_livestream_fragment03.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_list_livestream_fragment03.setAdapter(adapter_list_livestream_fragment03);

        return view;
    }
}
