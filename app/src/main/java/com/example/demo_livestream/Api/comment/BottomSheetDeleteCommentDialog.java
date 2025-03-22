package com.example.demo_livestream.Api.comment;


import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.viettel.mocha.app.R;
import com.viettel.mocha.common.api.http.HttpCallBack;
import com.viettel.mocha.module.keeng.widget.buttonSheet.BottomDialog;
import com.viettel.mocha.rmlivestream.comment.event_bus.DeleteCommentEvent;
import com.viettel.mocha.rmlivestream.comment.event_bus.DeleteReplyEvent;
import com.viettel.mocha.rmlivestream.model.RMLivestream;
import com.viettel.mocha.rmlivestream.model.RmCommentModel;
import com.viettel.mocha.rmlivestream.network.LivestreamApi;
import com.viettel.mocha.rmlivestream.network.response.RDeleteReply;
import com.viettel.mocha.rmlivestream.network.response.RReportCommentResponse;

import org.greenrobot.eventbus.EventBus;

public class BottomSheetDeleteCommentDialog {
    Activity activity;
    BottomDialog bottomSheetDialog;
    RMLivestream video;
    private RelativeLayout rlDelete, rlCancel;
    private RmCommentModel rmCommentModel;

    public BottomSheetDeleteCommentDialog(@NonNull Activity activity, RmCommentModel rmCommentModel, RMLivestream rmLivestream) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rm_dialog_delete_bottom_sheet, null);
        bottomSheetDialog = new BottomDialog(activity);
        bottomSheetDialog.setContentView(view);
        this.activity = activity;
        this.video = rmLivestream;
        this.rmCommentModel = rmCommentModel;

        if (bottomSheetDialog != null) {
            init(view);
        }
    }

    protected void init(View view) {
        rlDelete = view.findViewById(R.id.rl_delete);
        rlCancel = view.findViewById(R.id.rl_cancel);

        rlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doneDelete(rmCommentModel, video);

            }
        });
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void callApiDeleteComment(RmCommentModel rmCommentModel) {

        LivestreamApi.getInstance().postDeleteComment(rmCommentModel.getCommentId(), rmCommentModel.getContentId(), new HttpCallBack() {
            @Override
            public void onSuccess(String data) throws Exception {
                try {
                    Gson gson = new Gson();
                    RReportCommentResponse response = gson.fromJson(data, RReportCommentResponse.class);
                    if (response.getData() != null) {
                        EventBus.getDefault().postSticky(new DeleteCommentEvent(rmCommentModel));
                    }
                } catch (Exception e) {
                    Log.d("call API delete comment", "onResponse: ", e);

                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                Log.d("DeleteCommentDialog", "onFailure: " + message);

            }
        });

    }

    private void callApiDeleteReply(RmCommentModel rmCommentModel) {

        LivestreamApi.getInstance().postDeleteReply(rmCommentModel.getId(), rmCommentModel.getCommentReplyId(), rmCommentModel.getContentId(), new HttpCallBack() {
            @Override
            public void onSuccess(String data) throws Exception {
                try {
                    Gson gson = new Gson();
                    RDeleteReply response = gson.fromJson(data, RDeleteReply.class);
                    if (response != null) {
                        EventBus.getDefault().postSticky(new DeleteReplyEvent(rmCommentModel.getCommentId()));
                    }
                } catch (Exception e) {
                    Log.d("call API delete reply", "onResponse: ", e);
                }
            }

            @Override
            public void onFailure(String message) {
                super.onFailure(message);
                Log.d("DeleteCommentDialog", "onFailure: " + message);

            }
        });

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

    public void doneDelete(RmCommentModel rmCommentModel, RMLivestream video) {
        dismiss();
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(activity.getResources().getString(R.string.rm_title_delete_comment));
        alert.setMessage(activity.getResources().getString(R.string.rm_message_delete_comment));
        alert.setNegativeButton(activity.getResources().getString(R.string.rm_luckygame_cancle), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create();
        alert.setPositiveButton(activity.getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (rmCommentModel.getCommentReplyId() != null) {
                    callApiDeleteReply(rmCommentModel);
                } else {
                    callApiDeleteComment(rmCommentModel);
                }


            }
        }).create();
        AlertDialog dialog = alert.create();
        dialog.show();
        Button nButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nButton.setTextColor(activity.getResources().getColor(R.color.color_007A));
        Button pButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pButton.setTextColor(activity.getResources().getColor(R.color.color_007A));
        //alert.show();

    }

}
