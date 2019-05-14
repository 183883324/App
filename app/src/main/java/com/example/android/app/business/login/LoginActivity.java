package com.example.android.app.business.login;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.R;
import com.example.android.app.base_abcmp.bean.LoginBean;
import com.example.android.app.base_abcmp.contract.LoginContract;
import com.example.android.app.base_abcmp.presenter.LoginPresenter;
import com.example.android.app.business.login.splash.main.MainActivity;
import com.example.android.base.activity.BaseActivity;
import com.example.android.tool.KeyBoardUtils;
import com.example.android.tool.Util;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

    public class LoginActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {
    @BindView(R.id.login_ScrollView)
    LinearLayout login_ScrollView;
    @BindView(R.id.LoginCcountNumber)
    com.example.android.imview.PowerfulEditText LoginCcountNumber;
    @BindView(R.id.LoginPassword)
    com.example.android.imview.PowerfulEditText LoginPassword;
    @BindView(R.id.LoginLand)
    SuperTextView LoginLand;
    @BindView(R.id.register)
    TextView register;
    private String phone;
    private String password;
    private HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**是否需要标题栏*/
        ALLOW_TITLEBAR_SHOW = false;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void runOnResumeOnlyOnce() {
        /**检查版本更新*/
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        Util.EditShield(LoginPassword);//ditText 屏蔽选择、复制、粘贴等一切剪切板的操作
        Util.SoftKeyboard(login_ScrollView, LoginLand);//弹出软键盘时滚动视图
    }

    @Override
    protected void initData() {

    }

    @Override
    public LoginContract.Presenter createPresenter() {
            return new LoginPresenter(this);
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }


    @Override
    public void result(LoginBean o) {
        Util.ToastInfo(o.getMsg());
        ToastUtils.showShort(o.getMsg());
        ActivityUtils.startActivity(MainActivity.class);
        ActivityUtils.finishActivity(LoginActivity.class);


    }

    @Override
    public void logoutResult(Object o) {
        ToastUtils.showShort("请输入6位数以上手机号");
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }

    @OnClick({R.id.LoginLand, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.LoginLand:
                KeyBoardUtils.closeKeyboard(this, LoginPassword);//关闭软键盘
                phone = LoginCcountNumber.getText().toString();
                password = LoginPassword.getText().toString();
                if (phone.length() == 11) {
                    if (password.length() > 6) {
                        map = new HashMap<>();
                        getPresenter().login(map, true, true);

                    } else {
                        LoginPassword.startShakeAnimation();//抖动
                        ToastUtils.showShort("请输入6位数以上手机号");
                    }
                } else {
                    LoginCcountNumber.startShakeAnimation();//抖动
                    ToastUtils.showShort("请输入11位手机号");
                }
                break;
            case R.id.register:
                ActivityUtils.startActivity(RegisterActivity.class);
                break;

        }
    }


}


