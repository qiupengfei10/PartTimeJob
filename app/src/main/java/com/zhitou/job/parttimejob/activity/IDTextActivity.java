package com.zhitou.job.parttimejob.activity;

import android.os.Bundle;

import com.zhitou.job.R;
import com.zhitou.job.parttimejob.base.BaseActivity;

/**
 * 输入身份
 */
public class IDTextActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idtext);
        initView();
    }

    private void initView() {
        setTitle("实名认证");
    }
}
