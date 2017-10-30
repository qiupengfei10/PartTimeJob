package com.zhitou.job.parttimejob.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends BaseActivity {

    private EditText mEdtPassword;
    private EditText mEdtPhone;
    private Button mBtnLogin;

    private String phone;
    private String password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setClick();


    }

    private void setClick() {
        mTvTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        phone = mEdtPhone.getText().toString().trim();

        if (!VerifyUtil.isPhoneNum(phone)){
            showToast("请输入合法的手机号");
            return;
        }

        password = mEdtPassword.getText().toString().trim();
        if (password == null || password == "" || password.length() < 6){
            showToast("请输入6为以上的登录密码！");
            return;
        }

        BmobUser.loginByAccount(phone, password, new LogInListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null){
                    showToast("登录成功！");
                    Intent intent = new Intent();
                    intent.putExtra("user",myUser);
                    setResult(0,intent);
                    finish();
                }   else {
                    showToast("登录失败！请重试！");
                }
            }
        });
    }

    private void initView() {
        setTitle("登录");
        mTvTitleRight.setText("注册");
        mTvTitleRight.setVisibility(View.VISIBLE);
        mEdtPassword = (EditText)findViewById(R.id.edt_password);
        mEdtPhone = (EditText)findViewById(R.id.edt_phone);
        mBtnLogin = (Button)findViewById(R.id.btn_login);
    }
}
