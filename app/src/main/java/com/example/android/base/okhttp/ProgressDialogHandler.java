
package com.example.android.base.okhttp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;


import com.example.android.imview.LoadingDialog;


/**
 * Dialog的进度控制
 */

public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;


    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;
    private LoadingDialog sad;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener, boolean cancelable) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog() {

        if (sad == null) {
            sad = LoadingDialog.getInstance(context);
            /*sad = new LoadingDailog.Builder(context)
                    .setMessage("加载中...")
                    .setCancelable(true)
                    .setCancelOutside(true).create();
            sad.setCanceledOnTouchOutside(false);//点击旁边不可取消
            sad.show();*/
            sad.setCanceledOnTouchOutside(false);//点击旁边不可取消
            sad.show();
            if (cancelable) {
                sad.setCancelable(true);
                sad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }

            if (!sad.isShowing()) {
                sad.show();
            }
        }
    }

    private void dismissProgressDialog() {
        if (sad != null) {
            sad.dismiss();
            sad = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}