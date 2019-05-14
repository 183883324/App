package com.example.android.base.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.R;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;
import com.example.android.demo.search.IOnItemClickListener;
import com.example.android.tool.KeyBoardUtils;
import com.example.android.demo.search.SearchHistoryAdapter;
import com.example.android.demo.search.SearchHistoryDB;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SerchBaseActivitty extends BaseActivity implements IOnItemClickListener {
    @BindView(R.id.iv_search_back)
    ImageView ivSearchBack;
    @BindView(R.id.et_search_keyword)
    EditText etSearchKeyword;
    @BindView(R.id.iv_search_search)
    ImageView ivSearchSearch;
    @BindView(R.id.rv_search_history)
    RecyclerView rvSearchHistory;
    @BindView(R.id.search_underline)
    View searchUnderline;
    @BindView(R.id.tv_search_clean)
    TextView tvSearchClean;
    @BindView(R.id.view_search_outside)
    View viewSearchOutside;
    //历史搜索记录
    private ArrayList<String> allHistorys = new ArrayList<>();  //历史记录
    private ArrayList<String> historys = new ArrayList<>();    //集合
    //适配器
    private SearchHistoryAdapter searchHistoryAdapter;
    //数据库
    private SearchHistoryDB searchHistoryDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**是否需要标题栏*/
        ALLOW_TITLEBAR_SHOW = false;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        //实例化数据库
        searchHistoryDB = new SearchHistoryDB(SerchBaseActivitty.this, SearchHistoryDB.DB_NAME, null, 1);
        //查询全部搜索记录
        allHistorys = searchHistoryDB.queryAllHistory();
        setAllHistorys();
        //初始化recyclerView
        rvSearchHistory.setLayoutManager(new LinearLayoutManager(SerchBaseActivitty.this));//list类型
        searchHistoryAdapter = new SearchHistoryAdapter(SerchBaseActivitty.this, historys);
        rvSearchHistory.setAdapter(searchHistoryAdapter);
        //设置删除单个记录的监听
        searchHistoryAdapter.setOnItemClickListener(this);
        //监听编辑框文字改变
        etSearchKeyword.addTextChangedListener(new TextWatcherImpl());
    }

    @Override
    protected void initData() {

    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }

    /**
     * 点击事件
     */
    @OnClick({R.id.iv_search_back, R.id.et_search_keyword, R.id.iv_search_search, R.id.rv_search_history, R.id.search_underline, R.id.tv_search_clean, R.id.view_search_outside})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_search_back:
                hideAnim();
                ActivityUtils.finishActivity(SerchBaseActivitty.class);
                break;
            case R.id.et_search_keyword:
                break;
            case R.id.iv_search_search:
                search();
                break;
            case R.id.rv_search_history:
                break;
            case R.id.search_underline:
                break;
            case R.id.tv_search_clean:
                searchHistoryDB.deleteAllHistory();
                historys.clear();
                searchUnderline.setVisibility(View.GONE);
                searchHistoryAdapter.notifyDataSetChanged();
                break;
            case R.id.view_search_outside:
                hideAnim();
                break;
        }
    }
    /**
     * 把数据库之前查询的历史展现出来
     */
    private void setAllHistorys() {
        historys.clear();
        historys.addAll(allHistorys);
    }


    /**
     * 关闭软键盘
     */
    private void hideAnim() {
        KeyBoardUtils.closeKeyboard(this, etSearchKeyword);

    }

    /**
     * 搜索
     */
    private void search() {
        String searchKey = etSearchKeyword.getText().toString();  //获取到搜索的内容
        if (TextUtils.isEmpty(searchKey.trim())) {
            Toast.makeText(this, "请输入关键字", Toast.LENGTH_SHORT).show();
        } else {
            searchHistoryDB.insertHistory(searchKey);//插入到数据库
            hideAnim();
            etSearchKeyword.setText("");
            Toast.makeText(this, searchKey, Toast.LENGTH_SHORT).show();  //搜索的操作
        }
    }




    /**
     * 监听编辑框文字改变
     */
    private class TextWatcherImpl implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {//文本变化之后
            String keyword = editable.toString();   //获取长度内容
            if (TextUtils.isEmpty(keyword.trim())) {   //非空判断
                allHistorys.clear();//清空历史记录
                allHistorys = searchHistoryDB.queryAllHistory();//查询全部搜索记录
                setAllHistorys();//把数据库之前查询的历史展现出来
                searchHistoryAdapter.notifyDataSetChanged();//刷新数据
            } else {
                setKeyWordHistorys(editable.toString());//搜索框的内容变化就会调这个*
            }
        }
    }

    /**
     * 搜索框的内容变化就会调这个
     */
    private void setKeyWordHistorys(String keyword) {
        historys.clear();
        for (String string : allHistorys) {
            if (string.contains(keyword)) {
                historys.add(string);
            }
        }
        searchHistoryAdapter.notifyDataSetChanged();
    }

    /**
     * 删除单个搜索记录
     */
    @Override
    public void onItemDeleteClick(String keyword) {
        searchHistoryDB.deleteHistory(keyword);
        historys.remove(keyword);
        searchHistoryAdapter.notifyDataSetChanged();
    }

    /**
     * 点击单个搜索记录
     */
    @Override
    public void onItemClick(String keyword) {
        Toast.makeText(this, keyword, Toast.LENGTH_SHORT).show();
        hideAnim();
    }

    /**
     * 软键盘的监听
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            hideAnim();
            ActivityUtils.finishActivity(SerchBaseActivitty.class);
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            search();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
