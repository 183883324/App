package com.example.android.app.base_abcmp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.R;
import com.example.android.app.business.login.splash.main.SetActivity;

import java.util.ArrayList;
import java.util.List;



public class SetRecyclerView extends BaseQuickAdapter<List, BaseViewHolder> {
    private Context context;
    public SetRecyclerView(int layoutResId, @Nullable ArrayList<List> data, SetActivity setActivity) {
        super(layoutResId, data);
        this.context=setActivity;

    }

    @Override
    protected void convert(BaseViewHolder helper, List item) {

        com.example.android.imview.imnineimgridview.ImNineImGridview gridview_activity = (com.example.android.imview.imnineimgridview.ImNineImGridview)helper.getView(R.id.gridview_activity);
        gridview_activity.setImNineImGridviewData(item ,context);
    }

}
