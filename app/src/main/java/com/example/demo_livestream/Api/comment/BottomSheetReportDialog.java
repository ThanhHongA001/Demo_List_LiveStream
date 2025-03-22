package com.example.demo_livestream.Api.comment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.viettel.mocha.app.R;
import com.viettel.mocha.module.keeng.widget.buttonSheet.BottomDialog;

public class BottomSheetReportDialog {
    Activity activity;
    BottomDialog bottomSheetDialog;
    private RelativeLayout rlReport, rlCancel;
    private String commentId, msisdn, content;

    public BottomSheetReportDialog(@NonNull Activity activity, String commentId, String msisdn, String content) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rm_dialog_report_bottom_sheet, null);
        bottomSheetDialog = new BottomDialog(activity);
        bottomSheetDialog.setContentView(view);
        this.activity = activity;
        this.commentId = commentId;
        this.msisdn = msisdn;
        this.content = content;
        init(view);
    }

    protected void init(View view) {
        rlReport = view.findViewById(R.id.rl_report);
        rlCancel = view.findViewById(R.id.rl_cancel);

        rlReport.setOnClickListener(view1 -> {
            ReportTypeDialog typeDialog = new ReportTypeDialog(activity, commentId, msisdn, content);
            typeDialog.show();
            dismiss();
        });
        rlCancel.setOnClickListener(view12 -> dismiss());
    }


    public void show() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.show();
        }
    }

    public void dismiss() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.dismiss();
            bottomSheetDialog = null;
        }
    }
}
