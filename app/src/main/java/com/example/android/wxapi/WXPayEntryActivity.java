package com.example.android.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.tool.Util;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private static final String APP_ID = "YOU_APP_ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    /**
     * 得到支付结果回调
     */
    @Override
    public void onResp(BaseResp resp) {
        String result;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "支付成功，开始采集吧~";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "已取消支付，请完成支付后进行采集~";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "支付中断，请完成支付后进行采集~";
                break;
            default:
                result = "支付异常，请完成支付后进行采集~";
                break;
        }
        Util.Toast(result);
        finish();
    }
}
