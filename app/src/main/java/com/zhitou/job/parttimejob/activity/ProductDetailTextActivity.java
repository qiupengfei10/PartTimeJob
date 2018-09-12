package com.zhitou.job.parttimejob.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.zhitou.job.R;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.been.ProductClassify;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

public class ProductDetailTextActivity extends BaseActivity {

    private EditText mEdtContent;
    private String detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_text);

        initView();
        mTvTitleRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_right:
                detail = mEdtContent.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("detail",detail);
                setResult(RESULT_OK,intent);
                finish();

                break;
        }
    }

    private void initView() {
        setTitle("商品描述");
        mTvTitleRight.setText("确定");
        mTvTitleRight.setVisibility(View.VISIBLE);
        mEdtContent = (EditText)findViewById(R.id.edt_content);
    }

    private void initClick() {
        mTvTitleRight.setOnClickListener(this);
    }
}
