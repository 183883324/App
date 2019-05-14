package com.example.android.base.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;



public abstract class RefreshBaseFragment  extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {
    /**
     * 下拉刷新容器
     */
    protected RefreshLayout mRefreshLayout;
    /**
     * 是否有Footer
     */
    protected boolean HAS_FOOTER = true;
    /**
     * 是否可以刷新
     */
    protected boolean CAN_REFRESH = true;
    @Override
    protected int getLayoutId() {
        return R.layout.base_refresh;
    }

    @Override
    protected void initView() {
        mRefreshLayout = mBaseView.findViewById(R.id.refreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        /**是否有尾巴*/
        if (HAS_FOOTER) {
            mRefreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
        }
        /**不可以刷新下*/
        if (!CAN_REFRESH) {
            mRefreshLayout.setEnableRefresh(false);
        }
        /**设置刷新Content（用于动态替换空布局）*/
        mRefreshLayout.setRefreshContent(View.inflate(mContext, getRefreshLayoutId(), null));
    }

    @Override
    protected void initListener() {
        /**    设置上拉刷新  是否有尾巴 有就监听  */
        mRefreshLayout.setOnRefreshListener(this);
        if (HAS_FOOTER) {
            mRefreshLayout.setOnLoadMoreListener(this);
        }
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refresh();
        /**是否有更多数据*/
        //恢复没有更多数据的原始状态
        mRefreshLayout.setNoMoreData(false);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        loadMore();
    }

    /**
     * description:  下拉刷新子布局
     */
    protected abstract int getRefreshLayoutId();

    /**
     * description:  下拉刷新
     */
    protected abstract void refresh();

    /**
     * description:  上拉加载
     */
    protected abstract void loadMore();


    /**
     *预加载
     */
    protected void PreLoading(RecyclerView BaseRV) {
        BaseRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int minLeftItemCount = 5;//剩余多少条时开始加载更多
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int itemCount = layoutManager.getItemCount();
                    int lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                    Log.i("minLeftItemCount", "【总数】" + itemCount + "【位置】" + lastPosition);
                    if (lastPosition == layoutManager.getItemCount() - 5) {//容错处理，保证滑到最后一条时一定可以加载更多
                        loadMore();
                    } else {
                        if (itemCount > minLeftItemCount) {
                            if (lastPosition == itemCount - minLeftItemCount) {
                                //一定要意识到，onScrolled方法并不是一直被回调的，估计最多一秒钟几十次
                                //所以当此条件满足时，可能并没有回调onScrolled方法，也就不会调用onLoadMore方法
                                //所以一定要想办法弥补这隐藏的bug，最简单的方式就是当滑到最后一条时一定可以加载更多
                                loadMore();
                            }
                        } else {//（第一次进入时）如果总数特别少，直接加载更多
                            loadMore();
                        }
                    }
                }
            }
        });

    }
}
