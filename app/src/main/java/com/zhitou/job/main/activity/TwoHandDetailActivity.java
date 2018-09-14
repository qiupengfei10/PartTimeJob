package com.zhitou.job.main.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhitou.job.R;
import com.zhitou.job.main.adapter.ImageAdapter;
import com.zhitou.job.main.been.ImageBeen;
import com.zhitou.job.main.been.TwoHand;
import com.zhitou.job.main.utils.GlideUtils;
import com.zhitou.job.main.view.NoScrollListView;
import com.zhitou.job.parttimejob.base.BaseActivity;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 二手详情页面
 */
public class TwoHandDetailActivity extends BaseActivity {

    private TwoHand twoHand;
    private NoScrollListView mLvImages;
    private TextView mTvPrice;
    private TextView mTvContent;
    private TextView mTvName;
    private TextView mTvUserName;
    private ImageView mIvUserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_hand_detail);
        //获取详情
        twoHand = (TwoHand)getIntent().getSerializableExtra("twoHand");
        initView();

    }

    private void initView() {
        mLvImages = (NoScrollListView) findViewById(R.id.lv_images);
        mLvImages.setAdapter(new ImageAdapter(this,twoHand.getImageBeens()));
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvName.setText(twoHand.getTitle());
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvPrice.setText(twoHand.getPrice());
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mTvContent.setText(twoHand.getContent());
        mTvUserName = (TextView) findViewById(R.id.tv_user_name);
        mTvUserName.setText(twoHand.getPushUser().getNickName());
        mIvUserImage = (ImageView) findViewById(R.id.iv_user_logo);
        GlideUtils.showPic(this,twoHand.getPushUser().getUserImage(),mIvUserImage);
    }
}
