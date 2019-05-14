package com.example.android.base.okhttp;

import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.example.android.tool.ExceptionHandle;
import com.example.android.tool.Util;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 观察者
 */
public class ProgressObserver<T> implements Observer<T>, ProgressCancelListener {
    private static final String TAG = "ProgressObserver____ ";
    private ObserverResponseListener listener;
    private ProgressDialogHandler mProgressDialogHandler;
    private Context context;
    private Disposable d;

    public ProgressObserver(Context context, ObserverResponseListener listener, boolean isDialog, boolean cancelable) {
        this.listener = listener;
        this.context = context;
        if (isDialog) {
            mProgressDialogHandler = new ProgressDialogHandler(context, this, cancelable);
        }
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;

        showProgressDialog();
    }

    @Override
    public void onNext(T t) {
        listener.onNext(t);//可定制接口，通过code回调处理不同的业务
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        Log.e(TAG, "onError: ", e);
        //自定义异常处理
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            listener.onError((ExceptionHandle.ResponeThrowable) e);
        } else {
            listener.onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }

        if (e instanceof UnknownHostException) {
            Util.ToastError("请检查网络设置");
        } else if (e instanceof SocketTimeoutException) {
            Util.ToastError("请求超时");
        } else if (e instanceof ConnectException) {
            Util.ToastError("连接失败");
        } else if (e instanceof HttpException) {
            Util.ToastError("网络异常");
        } else {
            Util.ToastError("请求失败");
        }
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
        Log.e(TAG, "onComplete: ");
    }

    @Override
    public void onCancelProgress() {
        Log.e(TAG, "requestCancel: ");
        //如果处于订阅状态，则取消订阅
        if (!d.isDisposed()) {
            d.dispose();
        }
    }
}
