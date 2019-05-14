package com.example.android.base.event;

public class BaseEvent  {
    protected boolean isSuccess;
    protected String mMsg;

    public boolean isSuccess(){
        return isSuccess;
    }

    public String getMsg() {
        return mMsg;
    }
}
