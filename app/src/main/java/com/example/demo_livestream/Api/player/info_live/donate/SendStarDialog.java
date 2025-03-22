package com.viettel.mocha.rmlivestream.player.info_live.donate;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.viettel.mocha.app.ApplicationController;
import com.viettel.mocha.app.R;
import com.viettel.mocha.app.databinding.RmDialogSendStarBinding;
import com.viettel.mocha.common.api.http.HttpCallBack;
import com.viettel.mocha.rmlivestream.RMDonateTermsActivity;
import com.viettel.mocha.rmlivestream.RMLivestreamActivity;
import com.viettel.mocha.rmlivestream.model.Donate;
import com.viettel.mocha.rmlivestream.model.RMLivestream;
import com.viettel.mocha.rmlivestream.network.LivestreamApi;
import com.viettel.mocha.rmlivestream.network.response.CurrentStarNumberResponse;
import com.viettel.mocha.rmlivestream.network.response.ListDonateResponse;
import com.viettel.mocha.ui.dialog.BottomSheetDialog;
import com.viettel.mocha.v5.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class SendStarDialog extends BottomSheetDialog {
    private final RMLivestreamActivity context;
    private final RMLivestream livestream;
    private final RmDialogSendStarBinding binding;
    private ApplicationController app;
    private Bitmap avtBitmap;
    private LivestreamDonateAdapter adapter;
    private Dialog donateFailDialog;
    int loading = 0;
    long balance = 0;
    private Donate currentDonate;

    public SendStarDialog(@NonNull RMLivestreamActivity context, RMLivestream livestream) {
        super(context);
        this.context = context;
        this.livestream = livestream;
        binding = RmDialogSendStarBinding.inflate(LayoutInflater.from(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        app = ApplicationController.self();
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        initView();
        getListStarPackage();
        getUserStarNumber();
    }

    private void initView() {
        if (app.getReengAccountBusiness().getLastChangeAvatar() != null) {
            String urlAvatar = ApplicationController.self().getAvatarBusiness().getAvatarUrl(
                    app.getReengAccountBusiness().getLastChangeAvatar(),
                    app.getReengAccountBusiness().getPhoneNumberLogin(), 200
            );
            Glide.with(context)
                    .load(urlAvatar).centerCrop()
                    .placeholder(R.drawable.ic_avatar_default)
                    .into(binding.ivAvatar);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_avatar_default).centerCrop()
                    .placeholder(R.drawable.ic_avatar_default)
                    .into(binding.ivAvatar);
        }

        binding.edtComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (TextUtils.isEmpty(binding.edtStarNumber.getText().toString().trim())) {
//                    disableSendButton();
//                } else {
//                    sendStarNumber = binding.edtStarNumber.getText().toString().trim();
//                    try {
//                        if (Long.parseLong(sendStarNumber) > 0) {
//                            binding.edtComment.setText(context.getString(R.string.sent_stars, shortenNumber(Long.parseLong(sendStarNumber))));
//                            binding.tvBtnSendStar.setText(context.getString(R.string.text_btn_send_star, shortenNumber(Long.parseLong(sendStarNumber))));
//                            enableSendButton();
//                        } else {
//                            disableSendButton();
//                        }
//                    } catch (Exception e) {
//                        ToastUtils.showToast(context, context.getResources().getString(R.string.invalid_number_notify));
//                        disableSendButton();
//                    }
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        adapter = new LivestreamDonateAdapter(context);
        adapter.setClickStarPackageListener(starPackage -> {
//            if (TextUtils.isEmpty(binding.edtComment.getText().toString())
//                    || binding.edtComment.getText().toString().matches("Donate .*")) {
//                binding.edtComment.setText(context.getString(R.string.sent_gift, starPackage.getName()));
//            }
            currentDonate = starPackage;

        });
        binding.rcvStarPackages.setAdapter(adapter);
        binding.rcvStarPackages.setLayoutManager(new GridLayoutManager(context, 3));
        binding.btnTopup.setOnClickListener(view -> {
            dismiss();
            new RechargeStarDialog(context).show();
        });
        binding.tvTerm.setOnClickListener(view -> {
            Intent i = new Intent(context, RMDonateTermsActivity.class);
            context.startActivity(i);
        });
        binding.btnSendStars.setOnClickListener(view -> {
            apiSendStar();
        });
    }

    private void getListStarPackage() {
        LivestreamApi.getInstance().getListDonate(new HttpCallBack() {
            @Override
            public void onSuccess(String data) throws Exception {
                Gson gson = new Gson();
                ListDonateResponse response = gson.fromJson(data, ListDonateResponse.class);
                if (response != null && response.getData() != null) {
                    adapter.setList(response.getData());
                } else {
                    context.showToast(context.getString(R.string.error_message_default));
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                context.showToast(context.getString(R.string.error_message_default));
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
            }
        });
    }

    private void apiSendStar() {
        try {
            if (currentDonate == null) return;
            if (balance < currentDonate.getAmountStar()) {
                ToastUtils.showToast(context, context.getString(R.string.rm_not_enough_stars), 3000, 0, null);
                return;
            }
            String message = TextUtils.isEmpty(binding.edtComment.getText().toString().trim())
                    ? ""
                    : binding.edtComment.getText().toString().trim();
            LivestreamApi.getInstance().sendStar(message, String.valueOf(currentDonate.getId()), livestream.getChannel().getId(), livestream.getId(), new HttpCallBack() {
                @Override
                public void onSuccess(String data) throws Exception {
                    JSONObject json = new JSONObject(data);
                    int code = json.getInt("code");
                    if (code == 200) {
                        ToastUtils.showToast(context, R.string.rm_donate_success);
                        EventBus.getDefault().postSticky(new ReloadTopDonateEvent());
                        dismiss();
                    } else {
                        ToastUtils.showToast(context, R.string.rm_donate_fail);
                    }
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    ToastUtils.showToast(context, R.string.rm_donate_fail);
                }

                @Override
                public void onCompleted() {
                    super.onCompleted();
                }
            });
        } catch (Exception e) {
            ToastUtils.showToast(context, context.getResources().getString(R.string.rm_invalid_number_notify));
        }

    }


    private String shortenNumber(long value) {
        String shortenValue = "";
        if (value < 1000) {
            shortenValue = String.valueOf(value);
        } else if (value < 999999) {
            shortenValue = new DecimalFormat("#.##").format((double) value / 1000) + "K";
        } else if (value < 999999999) {
            shortenValue = new DecimalFormat("#.##").format((double) value / 1000000) + "M";
        } else if (value < Long.parseLong("999999999999")) {
            shortenValue = new DecimalFormat("#.##").format((double) value / 1000000000) + "B";
        } else if (value < Long.parseLong("999999999999999")) {
            shortenValue = new DecimalFormat("#.##").format((double) value / Long.parseLong("1000000000000")) + "T";
        } else {
            shortenValue = new DecimalFormat("#.##").format((double) value / Long.parseLong("1000000000000000")) + "Q";
        }
        return shortenValue;
    }

    public void getUserStarNumber() {
        LivestreamApi.getInstance().getUserStarNumber(new HttpCallBack() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                CurrentStarNumberResponse response = gson.fromJson(data, CurrentStarNumberResponse.class);
                if (response != null) {
                    binding.tvCurrentStar.setText(shortenNumber(response.getData().getTotalStar()));
                    balance = response.getData().getTotalStar();
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);

            }

            @Override
            public void onCompleted() {
                super.onCompleted();
            }
        });
    }
}
