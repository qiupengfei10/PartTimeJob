package com.zhitou.job.main.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhitou.job.R;
import com.zhitou.job.main.been.AddressForSH;
import com.zhitou.job.main.been.TwoHand;
import com.zhitou.job.main.utils.GlideUtils;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.been.MyUser;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 确认订单页面
 */
public class AffirmOrderActivity extends BaseActivity {

    private TwoHand twoHand;
    private ImageView mIvUserLogo;
    private TextView mTvUserName;
    private TextView mTvProductName;
    private TextView mTvPrice;
    private ImageView mIvPriductImage;
    private Object address;
    private AddressForSH addressForSH;
    private TextView mTvName;
    private TextView mTvPhone;
    private TextView mTvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affirm);
        twoHand = (TwoHand)getIntent().getSerializableExtra("twoHand");
        initView();
        initDatas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
    }

    private void initDatas() {
        mTvProductName.setText(twoHand.getTitle());
        mTvUserName.setText(twoHand.getPushUser().getNickName());
        mTvPrice.setText(twoHand.getPrice());

        GlideUtils.showPic(this,twoHand.getImageBeens().get(0).getPath(),mIvPriductImage);
        GlideUtils.showPic(this,twoHand.getPushUser().getUserImage(),mIvUserLogo);

    }

    private void initView() {
        mIvUserLogo = (ImageView)findViewById(R.id.iv_user_image);
        mTvUserName = (TextView)findViewById(R.id.tv_user_name);
        mIvPriductImage = (ImageView)findViewById(R.id.iv_product_image);
        mTvProductName = (TextView)findViewById(R.id.tv_product_name);
        mTvPrice = (TextView)findViewById(R.id.tv_price);

        mTvName = (TextView)findViewById(R.id.tv_name);
        mTvPhone = (TextView)findViewById(R.id.tv_phone);
        mTvAddress = (TextView)findViewById(R.id.tv_address);

        findViewById(R.id.ll_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AffirmOrderActivity.this,AddAddressSHActivity.class));
            }
        });
    }

    public void getAddress() {
        BmobQuery<AddressForSH> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", BmobUser.getCurrentUser().getObjectId());
        query.order("-CreateAt");
        query.findObjects(new FindListener<AddressForSH>() {
            @Override
            public void done(List<AddressForSH> list, BmobException e) {
                if (e == null){
                    Log.e("qpf","查询地址成功 -- " + BmobUser.getCurrentUser().getObjectId() + list.size());
                    if (list.size() > 0){
                        addressForSH = list.get(0);
                        mTvName.setText(addressForSH.getUserName());
                        mTvPhone.setText(addressForSH.getPhone());
                        mTvAddress.setText(addressForSH.getProvince()+addressForSH.getCity()+addressForSH.getDistrict() + " "+addressForSH.getDetailAddress());
                    }else {
                        mTvName.setText("");
                        mTvPhone.setText("");
                        mTvAddress.setText("添加地址");
                    }
                }else {
                    Log.e("qpf","查询地址失败 -- " + e.toString());
                }
            }
        });
    }
}
