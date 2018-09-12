package com.zhitou.job.parttimejob.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.zhitou.job.R;
import com.zhitou.job.parttimejob.base.BaseActivity;

public class ApplyActivity extends BaseActivity {

    private TextView mTvStatus;
    private TextView mTvHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        initView();
        initData();
    }

    private void initData() {
        String status = getIntent().getStringExtra("status");
        String hint = getIntent().getStringExtra("hint");

        mTvStatus.setText(status);
        mTvHint.setText(hint);
    }

    private void initView() {
        setTitle("实名认证");
        mTvStatus = (TextView)findViewById(R.id.tv_status);
        mTvHint = (TextView)findViewById(R.id.tv_hint);
    }
}
