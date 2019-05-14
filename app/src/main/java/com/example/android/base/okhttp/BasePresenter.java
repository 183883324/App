package com.example.android.base.okhttp;

/**
 * 网络框架参考地址
 * https://blog.csdn.net/qq_37173653/article/details/78924010
 *
 */

public abstract class BasePresenter<V extends BaseView>{

    private V mView;

    public V getView(){
        return mView;
    }
/**
 * 附件视图
 * */
    public void attachView(V v){
        mView = v;
    }
/**
 * 分离视图
 * */
    public void detachView(){
        mView = null;
    }
}
