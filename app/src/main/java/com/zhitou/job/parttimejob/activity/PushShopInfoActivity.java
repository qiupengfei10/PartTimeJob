package com.zhitou.job.parttimejob.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.base.BaseActivity;

/**
 * 上传店铺信息
 */
public class PushShopInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_shop_info);
        setTitle("创建店铺");
        mTvTitleRight.setText("下一步");
        mTvTitleRight.setVisibility(View.VISIBLE);
    }
}
