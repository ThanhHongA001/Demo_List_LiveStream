package com.example.demo_livestream.MainActivity_List_LiveStream;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment.MainActivity_List_LiveStream_Fragment01;
import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment.MainActivity_List_LiveStream_Fragment02;
import com.example.demo_livestream.MainActivity_List_LiveStream.Fragment.MainActivity_List_LiveStream_Fragment03;
import com.example.demo_livestream.R;
import java.util.Arrays;
import java.util.List;

public class MainActivity_List_LiveStream extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rm_activity_main_list_livestream);

        // Tìm RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rm_activity_main_list_livestream_rv_01);

        // Danh sách các Fragment cần hiển thị
        List<Fragment> fragments_list_livestream = Arrays.asList(
                new MainActivity_List_LiveStream_Fragment01(),
                new MainActivity_List_LiveStream_Fragment02(),
                new MainActivity_List_LiveStream_Fragment03()
        );

        // Thiết lập Adapter cho RecyclerView
        MainActivity_List_LiveStream_Adapter adapter = new MainActivity_List_LiveStream_Adapter(this, fragments_list_livestream);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}
