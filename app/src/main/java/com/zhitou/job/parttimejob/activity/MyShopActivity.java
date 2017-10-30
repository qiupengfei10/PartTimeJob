package com.zhitou.job.parttimejob.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.base.BaseActivity;

/**
 * 我的店铺页面
 */
public class MyShopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        initView();
        setOnClick();
    }

    private void setOnClick() {
        findViewById(R.id.tv_go).setOnClickListener(this);
    }

    private void initView() {
        setTitle("我的店铺");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_go:
                startActivity(new Intent(this,PushShopInfoActivity.class));
                break;
        }
    }
}
