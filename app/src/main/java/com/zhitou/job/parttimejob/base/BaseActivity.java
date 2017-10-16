package com.zhitou.job.parttimejob.base;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhitou.job.parttimejob.R;

/**
 * Created by qiupengfei on 2017/10/16.
 */
public abstract class BaseActivity extends Activity{
    private TextView mTvTitle;
    private ImageView mIvBack;

    protected void setTitle(String title) {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvBack = (ImageView) findViewById(R.id.iv_title_left);

        mTvTitle.setText(title);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setTitle(String title,boolean canBack) {
        setTitle(title);
        mIvBack.setVisibility(View.GONE);
    }


    protected void showToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
