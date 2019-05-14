package com.example.android.app.business.login.splash.main;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.R;
import com.example.android.app.base_abcmp.adapter.SetRecyclerView;
import com.example.android.base.activity.RefreshBaseActivity;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;
import com.example.android.demo.nine_palace.DemoBean;
import com.example.android.demo.nine_palace.UrlData;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SetActivity extends RefreshBaseActivity {
    private List<String> imageLists = UrlData.getImageLists();
    private RecyclerView baseRecyclerview;
    private ArrayList<List> data = new ArrayList<>();
    private SetRecyclerView mAdapter;


    @Override
    protected int getRefreshLayoutId() {
        return R.layout.base_recyclerview;
    }

    @Override
    protected void refresh() {
        data.clear();//数据清空
        for (int i = 0; i < 20; i++) {
            data.add(imageLists);
        }
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh();//结束刷新

    }

    @Override
    protected void loadMore() {
        //添加30个随机假数据
        data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            List<String> img = new ArrayList<>();
            int random = new Random().nextInt(UrlData.getImageLists().size()) + 1;
            for (int j = 0; j < random; j++) {
                img.add(UrlData.getImageLists().get(j));
            }
            data.add(img);
        }
        for (int i = 20; i < 40; i++) {

            data.add(imageLists);
        }
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishLoadMore();//结束加载
        mRefreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据
    }

    @Override
    protected void initViews() {
        baseRecyclerview = (RecyclerView) findViewById(R.id.base_recyclerview);
        baseRecyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        mAdapter = new SetRecyclerView(R.layout.base_recyclerview_item, data,this);
        baseRecyclerview.getItemAnimator().setChangeDuration(0);// 通过设置动画执行时间为0来解决闪烁问题
        baseRecyclerview.setAdapter(mAdapter);
        mRefreshLayout.autoRefresh();//一进页面调用refresh()方法刷新数据。
        PreLoading(baseRecyclerview);//预加载


    }
        @Override
        protected void initData () {


        }

        @Override
        public BasePresenter createPresenter () {
            return null;
        }

        @Override
        public BaseView createView () {
            return null;
        }

    }
