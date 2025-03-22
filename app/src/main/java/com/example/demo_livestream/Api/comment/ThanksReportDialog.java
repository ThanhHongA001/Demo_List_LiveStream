package com.example.demo_livestream.Api.comment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import com.viettel.mocha.app.R;

public class ThanksReportDialog extends Dialog {

    public ThanksReportDialog(@NonNull Context context) {
        super(context, R.style.DialogFullscreen);
        setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rm_dialog_thanks_report);
        AppCompatTextView tvOk = (AppCompatTextView) findViewById(R.id.tv_ok);
        tvOk.setOnClickListener(view -> dismiss());
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
