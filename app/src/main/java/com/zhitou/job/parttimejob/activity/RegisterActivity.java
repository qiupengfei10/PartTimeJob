package com.zhitou.job.parttimejob.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.base.MyApplication;
import com.zhitou.job.parttimejob.been.MyUser;
import com.zhitou.job.parttimejob.utils.VerifyUtil;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {

    private EditText mEdtPassword;
    private EditText mEdtCode;
    private EditText mEdtPhone;
    private TextView mTvCode;
    private Button mBtnRegister;

    private String phone;
    private String password;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setClick();
    }

    private void setClick() {
        //注册
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        //获取验证码
        mTvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = 60;
                getCode();
            }
        });
    }

    private void register() {
        password = mEdtPassword.getText().toString().trim();
        code = mEdtCode.getText().toString().trim();


        if (code == null || code == "" || code.length() != 6){
            showToast("验证码错误！");
            return;
        }

        if (password == null || password == "" || password.length() < 6){
            showToast("请输入6为以上的登录密码！");
            return;
        }

        MyUser user = new MyUser();
        user.setPassword(password);
        user.setMobilePhoneNumber(phone);
        user.setSee_password(password);
        user.signOrLogin(code, new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null){
                    showToast("注册成功！");
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void initView() {
        setTitle("注册");
        mEdtPassword = (EditText)findViewById(R.id.edt_password);
        mEdtCode = (EditText)findViewById(R.id.edt_code);
        mEdtPhone = (EditText)findViewById(R.id.edt_phone);
        mTvCode = (TextView)findViewById(R.id.tv_code);
        mBtnRegister = (Button)findViewById(R.id.btn_register);
    }

    int time;
    public void getCode() {
        phone = mEdtPhone.getText().toString().trim();
       if (!VerifyUtil.isPhoneNum(phone)){
           showToast("请核对您输入的手机号是否正确!");
           return;
       }

        //设置为不可点击
        mTvCode.setEnabled(false);
        //开始倒计时 60秒
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (time >= 0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTvCode.setText(time+"S后重试");
                            if (time <= 0){
                                mTvCode.setText("获取验证码");
                                mTvCode.setEnabled(true);
                            }
                        }
                    });
                    time--;
                }
            }
        }).start();


        BmobSMS.requestSMSCode(phone,"学生超市", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId,BmobException ex) {
                if(ex==null){//验证码发送成功
                   showToast("验证码已发送，请及时查收！");
                }
            }
        });
    }
}
