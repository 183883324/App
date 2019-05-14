package com.example.android.app.business.login.splash.main.WorkFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.R;
import com.example.android.base.dao.User;
import com.example.android.app.business.login.splash.main.SetActivity;
import com.example.android.base.activity.SerchBaseActivitty;
import com.example.android.base.fragment.base_dialog.BaseDialog;
import com.example.android.base.fragment.base_dialog.CommonDialog;
import com.example.android.base.fragment.base_dialog.ViewConvertListener;
import com.example.android.base.fragment.base_dialog.ViewHolder;
import com.example.android.base.fragment.BaseFragment;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;
import com.example.android.demo.nine_palace.DemoActivity;
import com.example.android.imview.LoadingDialog;
import com.example.android.tool.GrennDaoUtil;
import com.example.android.tool.Util;

import butterknife.BindView;
import butterknife.OnClick;

public class WorkFragment extends BaseFragment {


    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.bt2)
    Button bt2;
    @BindView(R.id.bt3)
    Button bt3;
    @BindView(R.id.bt4)
    Button bt4;
    @BindView(R.id.bt5)
    Button bt5;
    @BindView(R.id.bt6)
    Button bt6;
    @BindView(R.id.bt7)
    Button bt7;
    @BindView(R.id.bt8)
    Button bt8;
    @BindView(R.id.bt9)
    Button bt9;
    @BindView(R.id.bt10)
    Button bt10;
    @BindView(R.id.dptext)
    TextView dptext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ALLOW_TITLEBAR_SHOW = true;
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work;
    }

    @Override
    protected void initView() {
        Log.e("Fragment", "WorkFragment");
    }

    @Override
    protected void initListener() {

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

    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5, R.id.bt6, R.id.bt7, R.id.bt8, R.id.bt9, R.id.bt10})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                new PinglunFragmeent().show(getChildFragmentManager(), "dialog");
                break;
            case R.id.bt2:   //增加
                User user = new User();
                user.setId(1);
                user.setName("苹果");
                user.setContent("我得苹果");
                user.setDate("我得一个大苹果");
                GrennDaoUtil.AddData(user);
                break;
            case R.id.bt3: //删除一个
                GrennDaoUtil.DellAllData();
                Util.ClearCache();
                break;
            case R.id.bt4:  //改
                User user3 = new User(1, "花千骨", "19", "你是");
                GrennDaoUtil.UpDate(user3);
                break;
            case R.id.bt5: //查一个
                GrennDaoUtil.CheckNameDate("花千骨");
                break;
            case R.id.bt6: //支付
                CommonDialog.newInstance()
                        .setLayoutId(R.layout.dialog_confirm)
                        .setConvertListener(new ViewConvertListener() {
                            @Override
                            public void convertView(ViewHolder holder, final BaseDialog dialog) {
                                TextView message = (TextView) holder.getView(R.id.message);
                                message.setText("123123213123");
                                holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                holder.setOnClickListener(R.id.confirm, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Util.Toast("确定");
                                        dialog.dismiss();
                                    }
                                });

                            }
                        })
                        .setMargin(40)
                        .setDimAmout(0.3f)
                        .setShowBottom(false)
                        .setOutCancel(false)
                        .show(getFragmentManager());
                break;
            case R.id.bt7:
                try {
                    bt7.setText(Util.GetCache());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LoadingDialog instance = LoadingDialog.getInstance(getContext());
                instance.show();
                instance.setCanceledOnTouchOutside(false);//点击旁边不可取消
                break;
            case R.id.bt8:
                ActivityUtils.startActivity(SerchBaseActivitty.class);
                break;
            case R.id.bt9:
                ActivityUtils.startActivity(SetActivity.class);
                break;
            case R.id.bt10:
                ActivityUtils.startActivity(DemoActivity.class);
                break;
        }
    }
}
