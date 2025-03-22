package com.example.demo_livestream.Api.comment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.gson.Gson;
import com.viettel.mocha.app.R;
import com.viettel.mocha.common.api.http.HttpCallBack;
import com.viettel.mocha.module.newdetails.utils.ToastUtils;
import com.viettel.mocha.rmlivestream.comment.ThanksReportDialog;
import com.viettel.mocha.rmlivestream.network.LivestreamApi;
import com.viettel.mocha.rmlivestream.network.response.RReportCommentResponse;

public class ReportTypeDialog extends Dialog {
    private final String commentId;
    private final String msisdn;
    private final String content;
    private RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8;

    public ReportTypeDialog(@NonNull Context context, String commentId, String msisdn, String content) {
        super(context, R.style.DialogFullscreen);
        setCancelable(true);
        this.commentId = commentId;
        this.msisdn = msisdn;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rm_dialog_report_type);
        // RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rdg_report);
        AppCompatTextView tvCancel = (AppCompatTextView) findViewById(R.id.tv_cancel);
        AppCompatTextView tvReport = (AppCompatTextView) findViewById(R.id.tv_report);
        rb1 = (RadioButton) findViewById(R.id.rb_type1);
        rb2 = (RadioButton) findViewById(R.id.rb_type2);
        rb3 = (RadioButton) findViewById(R.id.rb_type3);
        rb4 = (RadioButton) findViewById(R.id.rb_type4);
        rb5 = (RadioButton) findViewById(R.id.rb_type5);
        rb6 = (RadioButton) findViewById(R.id.rb_type6);
        rb7 = (RadioButton) findViewById(R.id.rb_type7);
        rb8 = (RadioButton) findViewById(R.id.rb_type8);
        tvReport.setOnClickListener(view -> {
            dismiss();
            String typeR = null;
            if (rb1.isChecked()) {
                typeR = getContext().getResources().getString(R.string.rm_tv_report_type1);
            }
            if (rb2.isChecked()) {
                typeR = getContext().getResources().getString(R.string.rm_tv_report_type2);
            }
            if (rb3.isChecked()) {
                typeR = getContext().getResources().getString(R.string.rm_tv_report_type3);
            }
            if (rb4.isChecked()) {
                typeR = getContext().getResources().getString(R.string.rm_tv_report_type4);
            }
            if (rb5.isChecked()) {
                typeR = getContext().getResources().getString(R.string.rm_tv_report_type5);
            }
            if (rb6.isChecked()) {
                typeR = getContext().getResources().getString(R.string.rm_tv_report_type6);
            }
            if (rb7.isChecked()) {
                typeR = getContext().getResources().getString(R.string.rm_tv_report_type7);
            }
            if (rb8.isChecked()) {
                typeR = getContext().getResources().getString(R.string.rm_tv_report_type8);
            }
            if (typeR != null) {
                callApiReport(commentId, msisdn, content, typeR);
            } else
                ToastUtils.makeText(getContext(), getContext().getResources().getString(R.string.rm_tv_please_report));
        });

        tvCancel.setOnClickListener(view -> dismiss());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void callApiReport(String commentId, String msisdn, String content, String typeR) {
        LivestreamApi.getInstance().postReportComment(commentId, content, typeR, new HttpCallBack() {
            @Override
            public void onSuccess(String data) throws Exception {
                try {
                    Gson gson = new Gson();
                    RReportCommentResponse response = gson.fromJson(data, RReportCommentResponse.class);
                    if (response.getData() != null) {
                        ThanksReportDialog tDialog = new ThanksReportDialog(getContext());
                        tDialog.show();
                    }
                }catch (Exception e){
                    Log.d("ReportTypeDialog", "IOException", e);
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                Log.d("ReportTypeDialog", "onFailure: " + message);
            }
        });

    }


}
