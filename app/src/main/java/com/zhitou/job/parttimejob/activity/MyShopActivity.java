package com.zhitou.job.parttimejob.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.been.HomeShop;
import com.zhitou.job.parttimejob.utils.ImageUtils;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 我的店铺页面
 */
public class MyShopActivity extends BaseActivity {

    private RelativeLayout mRlNoShop;
    private ImageView mIvShopLogo;
    private TextView mTvShopName;
    private TextView mTvShopAddress;

    private HomeShop myShop;
    private TextView mTvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        initView();
        initData();
        setOnClick();
    }

    private void initData() {
        //获取店铺信息
        BmobQuery<HomeShop> query = new BmobQuery<>();
        query.addWhereEqualTo("user_id", BmobUser.getCurrentUser().getObjectId());
        query.findObjects(new FindListener<HomeShop>() {
            @Override
            public void done(List<HomeShop> list, BmobException e) {
                if (e == null){
                    //没有开店
                    if (list != null && list.size() != 0){
                        //有店铺
                        myShop = list.get(0);
                        mRlNoShop.setVisibility(View.GONE);
                        initShopData();
                    }
                }
            }
        });
    }

    private void initShopData() {
        //logo 店名 店铺地址
        Glide.with(this).load(myShop.getShop_logo()).into(mIvShopLogo);
        mTvShopAddress.setText("店铺地址：" + myShop.getSchool());
        mTvAddress.setText("寝室号：" +myShop.getAddress());
        mTvShopName.setText(myShop.getShopName());

    }

    private void setOnClick() {
        findViewById(R.id.tv_go).setOnClickListener(this);
        findViewById(R.id.ll_push_product).setOnClickListener(this);
    }

    private void initView() {
        setTitle("我的店铺");
        mRlNoShop = (RelativeLayout)findViewById(R.id.rl_no_shop);
        mTvShopAddress = (TextView) findViewById(R.id.tv_shop_address);
        mIvShopLogo = (ImageView) findViewById(R.id.iv_shop_logo);
        mTvShopName = (TextView) findViewById(R.id.tv_shop_name);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_go:
                startActivity(new Intent(this,PushShopInfoActivity.class));
                break;
            case R.id.ll_push_product:
                startActivity(new Intent(this,PushProductInfoActivity.class));
                break;

        }
    }
}
