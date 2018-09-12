package com.zhitou.job.parttimejob.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhitou.job.R;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.been.MyUser;
import com.zhitou.job.parttimejob.been.ProductClassify;
import com.zhitou.job.parttimejob.been.Special;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SelectSubActivity extends BaseActivity {


    private TagFlowLayout tagFlowLayout;
    private EditText mEdtContent;
    private String value;
    private List<ProductClassify> classifyList = new ArrayList<>();
    private String shop_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sub);
        //获取店铺id
        shop_id = getIntent().getStringExtra("shop_id");
        initView();
        initData();
        initClick();
    }

    private void initClick() {
        mTvTitleRight.setOnClickListener(this);
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                //选择的
                mEdtContent.setText(classifyList.get(position).getSubject());
                return false;
            }
        });
    }

    private void initView() {
        setTitle("选择类目");
        mTvTitleRight.setText("添加");
        mTvTitleRight.setVisibility(View.VISIBLE);
        mEdtContent = (EditText)findViewById(R.id.edt_content);
        tagFlowLayout = (TagFlowLayout)findViewById(R.id.id_flowlayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_right:
                value = mEdtContent.getText().toString().trim();
                if (!value.equals("")&&value.length() < 5){
                    ProductClassify productClassify = new ProductClassify();
                    //该标签是否存在服务器
                    boolean isExist = false;
                    //上传服务器，返回结果
                    for (int i = 0;i < classifyList.size();i++){
                        ProductClassify info = classifyList.get(i);
                        //不用上传服务器
                        if (info.getSubject().equals(value)){
                            productClassify = info;
                            isExist = true;
                        }

                    }
                    //不存在该标签则直接上传服务器
                    if (!isExist){
                        productClassify.setIndex(classifyList.size());
                        productClassify.setShop_id(shop_id);
                        productClassify.setSubject(value);
                        productClassify.save();
                    }

                    Intent intent = new Intent();
                    intent.putExtra("sub",productClassify);
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    showToast("您添加的类目名称有误！");
                }



                break;
        }
    }

    private void initData() {
        //获取我的店铺标签
        BmobQuery<ProductClassify> query = new BmobQuery<>();
        query.addWhereEqualTo("shop_id",shop_id);
        query.findObjects(new FindListener<ProductClassify>() {
            @Override
            public void done(final List<ProductClassify> list, BmobException e) {
                if (e == null){
                    classifyList = list;
                    tagFlowLayout.setAdapter(new TagAdapter<ProductClassify>(list) {
                        @Override
                        public View getView(FlowLayout parent, int position, ProductClassify productClassify) {
                            TextView tv = (TextView) LayoutInflater.from(SelectSubActivity.this).inflate(R.layout.item_tag_classify,null);
                            tv.setText(productClassify.getSubject());
                            return tv;
                        }
                    });
                }
            }
        });
    }
}
