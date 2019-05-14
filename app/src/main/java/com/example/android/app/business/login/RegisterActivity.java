package com.example.android.app.business.login;

import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.example.R;
import com.example.android.base.activity.BaseActivity;
import com.example.android.base.okhttp.BasePresenter;
import com.example.android.base.okhttp.BaseView;
import com.example.android.tool.Util;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.verification_code)
    TextView VerificationCode;
    @BindView(R.id.register_button)
    SuperTextView RegisterButton;

    //   @BindView(R.id.Register_CcountNumber)
//    XEditText RegisterCcountNumber;
//    @BindView(R.id.Register_Code)
//    XEditText RegisterCode;
//    @BindView(R.id.Register_Password)
//    XEditText RegisterPassword;
    private Disposable mdDisposable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {

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

    @OnClick({R.id.verification_code, R.id.register_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.verification_code:
                //点击后置为不可点击状态
                VerificationCode.setEnabled(false);
                //TODO  请求验证码操作
                //从0开始发射11个数字为：0-10依次输出，延时0s执行，每1s发射一次。
                mdDisposable = Flowable.intervalRange(0, 61, 0, 1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                VerificationCode.setText("重新获取(" + (60 - aLong) + ")");
                                VerificationCode.setTextColor(Util.getColor(R.color.color_text_gray_999999));
                            }
                        })
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                //倒计时完毕置为可点击状态
                                VerificationCode.setEnabled(true);
                                VerificationCode.setText("重新获取验证码");
                                VerificationCode.setTextColor(Util.getColor(R.color.white));
                            }
                        })
                        .subscribe();

                break;
            case R.id.register_button:
//                String Register_Code = RegisterCode.getText().toString();//验证码
//                String Register_CcountNumber = RegisterCcountNumber.getText().toString();//账号
//                String Register_Password = RegisterPassword.getText().toString();//密码
//                //验证码,账号,密码不为空，并且账号长度得等于11，密码长度得大于6，验证码长度得等于4
//                if (TextUtils.isBlank(Register_Code) && TextUtils.isBlank(Register_CcountNumber) && TextUtils.isBlank(Register_Password) && Register_CcountNumber.length() == 11 && Register_Password.length() > 6 && Register_Code.length() == 4) {
//                    //TODO  获取到  验证码，账号，密码  参数去请求数据操作
//                } else {
//                    ToastComponent.error(this, "请正确填写信息");
//                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mdDisposable != null) {
            mdDisposable.dispose();
        }
    }



}
