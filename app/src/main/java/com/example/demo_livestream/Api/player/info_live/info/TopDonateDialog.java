package com.viettel.mocha.rmlivestream.player.info_live.info;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.google.gson.Gson;
import com.viettel.mocha.adapter.BaseAdapter;
import com.viettel.mocha.app.R;
import com.viettel.mocha.app.databinding.RmDialogTopDonateBinding;
import com.viettel.mocha.common.api.http.HttpCallBack;
import com.viettel.mocha.rmlivestream.RMLivestreamActivity;
import com.viettel.mocha.rmlivestream.model.RMChannel;
import com.viettel.mocha.rmlivestream.model.RMLivestream;
import com.viettel.mocha.rmlivestream.model.TopDonate;
import com.viettel.mocha.rmlivestream.network.LivestreamApi;
import com.viettel.mocha.rmlivestream.network.response.CurrentStarNumberResponse;
import com.viettel.mocha.rmlivestream.network.response.TopDonateResponse;
import com.viettel.mocha.rmlivestream.player.info_live.donate.SendStarDialog;
import com.viettel.mocha.ui.dialog.BottomSheetDialog;
import com.viettel.mocha.ui.view.load_more.OnEndlessScrollListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopDonateDialog extends BottomSheetDialog implements BaseAdapter.OnLoadMoreListener {
    private final RMLivestreamActivity context;
    private ArrayList<TopDonate> listSenders;
    private RMChannel channel;
    private RmDialogTopDonateBinding binding;
    private TopDonateAdapter topDonateAdapter;
    private Boolean isLeftData = true;
    private RMLivestream rmLivestream;
    private String filter;

    public TopDonateDialog(@NonNull RMLivestreamActivity context, RMChannel channel, RMLivestream rmLivestream) {
        super(context);
        binding = RmDialogTopDonateBinding.inflate(LayoutInflater.from(context));
        this.context = context;
        this.channel = channel;
        this.rmLivestream = rmLivestream;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);

        initListSender();
        binding.btnBack.setOnClickListener(view -> dismiss());
        binding.btnSendStars.setOnClickListener(view -> {
            SendStarDialog sendStarDialog = new SendStarDialog((RMLivestreamActivity) activity, rmLivestream);
            sendStarDialog.show();
            dismiss();
        });

        List<String> items = Arrays.asList(context.getResources().getStringArray(R.array.rm_spinner_filter));

        RmFilterSpinnerAdapter adapter = new RmFilterSpinnerAdapter(context, items);

        binding.spinnerFilter.setAdapter(adapter);


        binding.spinnerFilter.setSelection(1);
        binding.spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString().toLowerCase();
                adapter.setSelectedPosition(position);
                filter = selectedItem;
                getListTopDonate(channel.getId(), selectedItem, 0, 10);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void initListSender() {
        binding.rcvListSenders.setLayoutManager(new LinearLayoutManager(context));
        topDonateAdapter = new TopDonateAdapter(context);
        BaseAdapter.setRecyclerViewLoadMore(binding.rcvListSenders, this);
        binding.rcvListSenders.setAdapter(topDonateAdapter);
        filter = activity.getString(R.string.rm_week).toLowerCase();
        getListTopDonate(channel.getId(), filter, 0, 10);
    }


    private void getListTopDonate(String channelId, String filter, Integer page, Integer size) {
        LivestreamApi.getInstance()
                .getTopDonateFilter(channelId, filter, page, size, new HttpCallBack() {
                    @Override
                    public void onSuccess(String data) throws Exception {
                        Gson gson = new Gson();
                        TopDonateResponse response = gson.fromJson(data, TopDonateResponse.class);
                        if (response != null) {
                            listSenders = response.getData();
                            if(listSenders.isEmpty()){
                                binding.lineEmpty.setVisibility(View.VISIBLE);
                                binding.rcvListSenders.setVisibility(View.INVISIBLE);
                            }else {
                                binding.lineEmpty.setVisibility(View.GONE);
                                binding.rcvListSenders.setVisibility(View.VISIBLE);
                                topDonateAdapter.setList(listSenders);
                                topDonateAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                });
    }

    private void loadMore(String channelId, String filter, Integer page, Integer size) {
        LivestreamApi.getInstance()
                .getTopDonateFilter(channelId, filter, page, size, new HttpCallBack() {
                    @Override
                    public void onSuccess(String data) throws Exception {
                        Gson gson = new Gson();
                        TopDonateResponse response = gson.fromJson(data, TopDonateResponse.class);
                        if (response != null) {
                            listSenders.addAll(response.getData());
                            topDonateAdapter.notifyDataSetChanged();
                            if (response.getData().size() < 10) {
                                isLeftData = false;
                            }
                        }
                    }
                });
    }

    @Override
    public void onLoadMore() {
        if(listSenders.size()>=10 && isLeftData){
            loadMore(channel.getId(), filter, listSenders.size() / 10, 10);
        }
    }
}
