package com.example.android.demo.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import com.example.R;
import com.example.android.tool.KeyBoardUtils;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements IOnItemClickListener, View.OnClickListener {
    public static final String TAG = "SearchFragment";
    private ImageView ivSearchBack;
    private EditText etSearchKeyword;
    private ImageView ivSearchSearch;
    private RecyclerView rvSearchHistory;
    private View searchUnderline;
    private TextView tvSearchClean;
    private View viewSearchOutside;

    //历史搜索记录
    private ArrayList<String> allHistorys = new ArrayList<>();  //历史记录
    private ArrayList<String> historys = new ArrayList<>();    //集合
    //适配器
    private SearchHistoryAdapter searchHistoryAdapter;
    //数据库
    private SearchHistoryDB searchHistoryDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        KeyBoardUtils.openKeyboard(this, etSearchKeyword);
        init();//实例化
    }

    private void init() {
        ivSearchBack = (ImageView) findViewById(R.id.iv_search_back);
        etSearchKeyword = (EditText) findViewById(R.id.et_search_keyword);
        ivSearchSearch = (ImageView) findViewById(R.id.iv_search_search);
        rvSearchHistory = (RecyclerView) findViewById(R.id.rv_search_history);
        searchUnderline = (View) findViewById(R.id.search_underline);
        tvSearchClean = (TextView) findViewById(R.id.tv_search_clean);
        viewSearchOutside = (View) findViewById(R.id.view_search_outside);


        //搜索框的监听
    // ivSearchSearch.getViewTreeObserver().addOnPreDrawListener(SearchActivity.this);//绘制监听
        //实例化数据库
        searchHistoryDB = new SearchHistoryDB(SearchActivity.this, SearchHistoryDB.DB_NAME, null, 1);
        //查询全部搜索记录
        allHistorys = searchHistoryDB.queryAllHistory();
        setAllHistorys();
        //初始化recyclerView
        rvSearchHistory.setLayoutManager(new LinearLayoutManager(SearchActivity.this));//list类型
        searchHistoryAdapter = new SearchHistoryAdapter(SearchActivity.this, historys);
        rvSearchHistory.setAdapter(searchHistoryAdapter);

        //设置删除单个记录的监听
        searchHistoryAdapter.setOnItemClickListener(this);
        //监听编辑框文字改变
        etSearchKeyword.addTextChangedListener(new TextWatcherImpl());
        //监听点击
        ivSearchBack.setOnClickListener(this);
        viewSearchOutside.setOnClickListener(this);
        ivSearchSearch.setOnClickListener(this);
        tvSearchClean.setOnClickListener(this);

    }

    /**
     * 把数据库之前查询的历史展现出来
     */
    private void setAllHistorys() {
        historys.clear();
        historys.addAll(allHistorys);
        checkHistorySize();
    }

    /**
     * 如果集合里面的长度大于1就显示分界线 如果小于就不显示
     */
    private void checkHistorySize() {
        if (historys.size() < 1) {
            searchUnderline.setVisibility(View.GONE);
        } else {
            searchUnderline.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_search_back || view.getId() == R.id.view_search_outside) {
            hideAnim();
        } else if (view.getId() == R.id.iv_search_search) {
            search();
        } else if (view.getId() == R.id.tv_search_clean) {
            searchHistoryDB.deleteAllHistory();
            historys.clear();
            searchUnderline.setVisibility(View.GONE);
            searchHistoryAdapter.notifyDataSetChanged();

        }
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
            Toast.makeText(this, searchKey  , Toast.LENGTH_SHORT).show();  //搜索的操作
            searchHistoryDB.insertHistory(searchKey);//插入到数据库
            hideAnim();
        }
    }


    /**
     * 监听编辑框文字改变
     */
    public class TextWatcherImpl implements TextWatcher {

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
                allHistorys.clear();
                allHistorys = searchHistoryDB.queryAllHistory();
                setAllHistorys();
                searchHistoryAdapter.notifyDataSetChanged();
            } else {
                setKeyWordHistorys(editable.toString());//

            }
        }
    }
/**搜索框的内容变化就会调这个*/
    private void setKeyWordHistorys(String keyword) {

        historys.clear();
        for (String string : allHistorys) {
            if (string.contains(keyword)) {
                historys.add(string);
            }
        }
        searchHistoryAdapter.notifyDataSetChanged();
        checkHistorySize();
    }

    /**
     * 删除单个搜索记录
     */
    @Override
    public void onItemDeleteClick(String keyword) {
        searchHistoryDB.deleteHistory(keyword);
        historys.remove(keyword);
        checkHistorySize();
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
/**软键盘的监听*/
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            hideAnim();
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            search();
            return true;
        }
        return super.dispatchKeyEvent(event);

    }

}
