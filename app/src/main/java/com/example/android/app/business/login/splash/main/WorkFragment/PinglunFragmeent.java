package com.example.android.app.business.login.splash.main.WorkFragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.R;
import com.example.android.base.fragment.base_dialog.BaseDialog;
import com.example.android.base.fragment.base_dialog.CommonDialog;
import com.example.android.base.fragment.base_dialog.ViewConvertListener;
import com.example.android.base.fragment.base_dialog.ViewHolder;
import com.example.android.base.fragment.dialog_fragment.BaseDialogFragment;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;
import com.example.android.imview.CustomPropertiesSet;
import com.example.android.tool.Util;

import butterknife.BindView;
import butterknife.OnClick;


public class PinglunFragmeent extends BaseDialogFragment {
    @BindView(R.id.edittk)
    Button edittk;
    @BindView(R.id.setii)
    CustomPropertiesSet setii;


    @Override
    protected int getLayoutId() {
        setTopOffset(200);//距离顶部
        return R.layout.activity_edittext;//布局内容的第一个控件不能match 要不然不能显示圆角
    }

    @Override
    protected void initView() {
        Glide.with(this).load(R.drawable.login).into(setii.setIm());
    }

    @Override
    protected void initListener() {
        setii.setIm().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.Toast("图片点击");
            }
        });
        setii.setIv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.Toast("文字点击");
            }
        });


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


    @OnClick({R.id.edittk, R.id.setii})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edittk:
                CommonDialog.newInstance()
                        .setLayoutId(R.layout.dialog_comment)
                        .setConvertListener(new ViewConvertListener() {
                            @Override
                            public void convertView(ViewHolder holder, final BaseDialog dialog) {
                                final EditText et_input = holder.getView(R.id.edit_input);
                                holder.setOnClickListener(R.id.tv_commit, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String text = et_input.getText().toString().trim();
                                        text = TextUtils.isEmpty(text) ? "请输入文字" : et_input.getText().toString().trim();
                                        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                            }
                        })
                        .SetPlayingKeyboard(true)
                        .setShowBottom(true)
                        .show(getChildFragmentManager());
                break;
            case R.id.setii:
                Util.Toast("自定义属性");
                break;
        }
    }
}
