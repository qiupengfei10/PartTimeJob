package com.zhitou.job.parttimejob.activity;

import android.annotation.TargetApi;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.adapter.ShopMainAdapter;
import com.zhitou.job.parttimejob.adapter.SubAdapter;
import com.zhitou.job.parttimejob.adapter.ProductAdapter;
import com.zhitou.job.parttimejob.base.BaseFragmentList;
import com.zhitou.job.parttimejob.been.HomeShop;
import com.zhitou.job.parttimejob.been.Point;
import com.zhitou.job.parttimejob.been.Product;
import com.zhitou.job.parttimejob.been.ProductClassify;
import com.zhitou.job.parttimejob.fragments.FragmentProductList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 主页
 */
public class ShopMainActivity extends FragmentActivity {
    private ImageView mIvShopLogo;
    private TextView mTvShopName;

    private ListView mUlvSub;      //种类列表
    private TextView mTvSubject;   //商品种类

    private List<ProductClassify> classifyList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();


    //当前选中的一级菜单
    private View currentSelecetView;

    private TextView mtvBusQuantity; //购物车中商品的数量

    private HomeShop shop;

    private int num = 0;
    private Handler handlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:  //让加号加1
                    num ++;
                    mtvBusQuantity.setText(num + "");
                    mtvBusQuantity.setVisibility(View.GONE);
                    if (num > 0){
                        mtvBusQuantity.setVisibility(View.VISIBLE);
                    }

                    //将选中的商品加入购物车
//                    mShopBus.add(products.get(msg.arg1).getProducts().get(msg.what));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_main);
        shop = (HomeShop) getIntent().getSerializableExtra("shop");
        initView();
        initData();
        setClick();
    }

    private void initData() {
        mTvShopName.setText(shop.getShopName());
        Glide.with(this).load(shop.getShop_logo()).diskCacheStrategy(DiskCacheStrategy.NONE).into(mIvShopLogo);

        //获取分类列表
        BmobQuery<ProductClassify> query = new BmobQuery<>();
        query.addWhereEqualTo("shop_id",shop.getObjectId());
        query.findObjects(new FindListener<ProductClassify>() {
            @Override
            public void done(List<ProductClassify> list, BmobException e) {
                if (e == null && list != null && list.size() != 0){
                    classifyList = list;
                    mUlvSub.setAdapter(new SubAdapter(ShopMainActivity.this,classifyList));
                    fragments = new Fragment[list.size()];
                    showFragment(0);
                    mUlvSub.setSelection(0);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setSelectedStatus(0);
                        }
                    },200);
                }
            }
        });


    }

    private void setClick() {
        mUlvSub.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setSelectedStatus(position);
                showFragment(position);
            }
        });


    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initView() {
        mIvShopLogo = (ImageView) findViewById(R.id.iv_shop_logo);
        mTvShopName = (TextView) findViewById(R.id.tv_shop_name);
        mUlvSub = (ListView) findViewById(R.id.lv_sub_table);
        mTvSubject = (TextView) findViewById(R.id.tv_subject);
        mtvBusQuantity = (TextView) findViewById(R.id.tv_bus_quantity);
    }

    /**
     * 设置选中状态
     *
     * @param position
     */
    public void setSelectedStatus(int position) {
        Log.e("qpf","第" + position + "个");
        mTvSubject.setText(classifyList.get(position).getSubject());
        //找到第一个可见的
        View view = mUlvSub.getChildAt(position);
        if (view == null) {
            Log.e("qpf","view为null");
            return;
        }
        //先将其他的置为未选中状态
        if (currentSelecetView != null) {
            TextView tvName = (TextView) currentSelecetView.findViewById(R.id.tv_sub);
            tvName.setTextColor(getResources().getColor(R.color.text_gray_color));
            currentSelecetView.findViewById(R.id.tv_line).setVisibility(View.GONE);
            tvName.setBackgroundColor(getResources().getColor(R.color.label_background_color));
        }
        //选中的
        TextView tvName = (TextView) view.findViewById(R.id.tv_sub);
        tvName.setBackgroundColor(getResources().getColor(R.color.main_background));
        tvName.setTextColor(getResources().getColor(R.color.main_color));
        view.findViewById(R.id.tv_line).setVisibility(View.VISIBLE);
        currentSelecetView = view;
    }

    private Fragment[] fragments;
    public void showFragment(int index){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null){
                transaction.hide(fragment);
            }
        }
        if (fragments[index] == null){
            fragments[index] = (new FragmentProductList("classify_id",classifyList.get(index).getObjectId()));
            transaction.add(R.id.fl_content,fragments[index]);
        }else {
            transaction.show(fragments[index]);
        }
        transaction.commit();
    }
}
