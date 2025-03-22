package com.viettel.mocha.rmlivestream.player.info_live.donate;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.viettel.mocha.app.ApplicationController;
import com.viettel.mocha.app.R;
import com.viettel.mocha.app.databinding.RmDialogRechargeStarBinding;
import com.viettel.mocha.common.api.http.HttpCallBack;
import com.viettel.mocha.rmlivestream.ConfirmListener;
import com.viettel.mocha.rmlivestream.InputListener;
import com.viettel.mocha.rmlivestream.RMDialogConfirm;
import com.viettel.mocha.rmlivestream.RMDialogInput;
import com.viettel.mocha.rmlivestream.RMDonateTermsActivity;
import com.viettel.mocha.rmlivestream.RMLivestreamActivity;
import com.viettel.mocha.rmlivestream.model.PackageStar;
import com.viettel.mocha.rmlivestream.network.LivestreamApi;
import com.viettel.mocha.rmlivestream.network.response.OtpPackageStarResponse;
import com.viettel.mocha.rmlivestream.network.response.PackageStarResponse;
import com.viettel.mocha.ui.dialog.BottomSheetDialog;
import com.viettel.mocha.v5.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class RechargeStarDialog extends BottomSheetDialog {
    private final RMLivestreamActivity context;
    private final RmDialogRechargeStarBinding binding;
    private ApplicationController app;
    private Bitmap avtBitmap;
    private LivestreamPackageStarAdapter adapter;
    private Dialog confirmDialog;
    PackageStar currentPackage;
    int loading = 0;

    public RechargeStarDialog(@NonNull RMLivestreamActivity context) {
        super(context);
        this.context = context;
        binding = RmDialogRechargeStarBinding.inflate(LayoutInflater.from(context));
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
    }

    private void initView() {
        adapter = new LivestreamPackageStarAdapter(context);

        adapter.setClickStarPackageListener(starPackage -> {
                currentPackage = starPackage;
        });
        binding.rcvStarPackages.setAdapter(adapter);
        binding.rcvStarPackages.setLayoutManager(new GridLayoutManager(context, 3));
        binding.btnBack.setOnClickListener(view -> {
            dismiss();
        });
        binding.tvTerm.setOnClickListener(view -> {
            Intent i = new Intent(context, RMDonateTermsActivity.class);
            context.startActivity(i);
        });
        binding.btnRecharge.setOnClickListener(view -> {
//            context.switchToTabChat();
            if(currentPackage!=null){
                initConfirmDialog();
            }else {
                ToastUtils.showToast(context, context.getString(R.string.error_message_default));
            }
        });
    }

    private void getListStarPackage() {
        LivestreamApi.getInstance().getListPackageStart(new HttpCallBack() {
            @Override
            public void onSuccess(String data) throws Exception {
                Gson gson = new Gson();
                PackageStarResponse response = gson.fromJson(data, PackageStarResponse.class);
                if (response != null && response.getData() != null) {

                    if(!response.getData().isEmpty()){
                    }
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

    private void apiRechargeOtp(String code, boolean isResend) {
        LivestreamApi.getInstance().buyPackageStartOTP(code,new HttpCallBack() {
            @Override
            public void onSuccess(String data) throws Exception {
                OtpPackageStarResponse response = ApplicationController.self().getGson().fromJson(data, OtpPackageStarResponse.class);
                if(response.getCode() == 200) {
                    if(!isResend) {
                        showDialogOtp(response.getRequestIdCp());
                    }
                } else {
                    ToastUtils.showToast(context, response.getMessage());
                }
            }
            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                ToastUtils.showToast(context, context.getString(R.string.error_message_default));
            }
        });
    }
    private void showDialogOtp(String requestId) {
        RMDialogInput dialogInput = new RMDialogInput(getContext().getString(R.string.rm_enter_otp), new InputListener() {
            @Override
            public void onClickLeft() {

            }

            @Override
            public void onClickRight(@NonNull String otp) {
                if(!otp.isEmpty()){
                    apiRechargeValidate(otp, requestId);
                }else {
                    ToastUtils.showToast(context, context.getString(R.string.error_message_default));
                }
            }

            @Override
            public void onClickResend() {

                RMDialogConfirm dialogConfirm = new RMDialogConfirm(getContext().getString(R.string.rm_resend),getContext().getString(R.string.rm_are_you_sure),  new ConfirmListener (){

                    @Override
                    public void onClickRight() {
                        dismiss();
                    }

                    @Override
                    public void onClickLeft() {
                        apiRechargeOtp(currentPackage.getCode(), true);
                        dismiss();
                    }
                });

                dialogConfirm.show(activity.getSupportFragmentManager(), "");
            }
        });
        dialogInput.show(activity.getSupportFragmentManager(), "");
    }
    private void apiRechargeValidate(String otp, String requestId) {
        LivestreamApi.getInstance().buyPackageStartValidate(currentPackage.getCode(),otp, requestId,new HttpCallBack() {
            @Override
            public void onSuccess(String data) throws Exception {
                JSONObject jsonObject = new JSONObject(data);
                int code = jsonObject.getInt("code");
                String message = jsonObject.getString("message");
                if(code == 200) {
                    EventBus.getDefault().postSticky(new BuyPackageStarEvent());
                    ToastUtils.showToast(context, context.getString(R.string.rm_recharge_success));
                } else if (code == 503) {
                    ToastUtils.showToast(context, message);
                } else {
                    ToastUtils.showToast(context, context.getString(R.string.error_message_default));
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                ToastUtils.showToast(context, context.getString(R.string.error_message_default));
            }
        });

    }

    public void initConfirmDialog() {
        RMDialogConfirm dialogConfirm = new RMDialogConfirm(
                getContext().getString(R.string.rm_confirm),
                getContext().getString(R.string.confirm_recharge_star,
                        shortenNumber((long) currentPackage.getValue()), shortenNumber((long) currentPackage.getPrice())),
                new ConfirmListener() {
                    @Override
                    public void onClickLeft() {
                    }

                    @Override
                    public void onClickRight() {
                        if(currentPackage!=null){
                            apiRechargeOtp(currentPackage.getCode(), false);
                        }else {
                            ToastUtils.showToast(context, context.getString(R.string.error_message_default));
                        }
                    }
                }
        );
        dialogConfirm.show(activity.getSupportFragmentManager(), "");
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

}
