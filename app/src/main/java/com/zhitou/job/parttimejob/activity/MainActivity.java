package com.zhitou.job.parttimejob.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.adapter.HomeShopAdapter;
import com.zhitou.job.parttimejob.been.HomeBanner;
import com.zhitou.job.parttimejob.been.HomeShop;
import com.zhitou.job.parttimejob.been.Special;
import com.zhitou.job.parttimejob.fragments.FragmentBanner;
import com.zhitou.job.parttimejob.utils.LocationUtil;
import com.zhitou.job.parttimejob.view.UnScrollListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends FragmentActivity {
    private UnScrollListView mUlvShop;
    private ScrollView scv;
    private LinearLayout mllHead;
    private LinearLayout mllMenuTop;

    private ViewPager mVpBanner;

    private List<HomeShop> data = new ArrayList<>();
    private List<HomeBanner> banners;
    private Fragment[] fragments;
    private TextView[] tvDocs;

    private LinearLayout mLlDot;
    private int index = 0;
    private boolean isRun = true;
    private Thread thread;


    private TextView[] mTvSpecialTitles;//专题标题
    private TextView[] mTvSubTitles;//二级标题
    private ImageView[] mIvSpecial;//专题图片
    private List<Special> specials;//专题集合

    private TextView mTvTitleLeft;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    AMapLocation aMapLocation = (AMapLocation) msg.obj;
                    mTvTitleLeft.setText(aMapLocation.getAddress());
                    Log.e("qpf","经纬度:(" + aMapLocation.getLatitude()+","+aMapLocation.getLongitude()+")");
                    Toast.makeText(MainActivity.this,"(" + aMapLocation.getLatitude()+","+aMapLocation.getLongitude()+")",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//13407197673
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setClick();
        //设置图片无限循环
        setLoopBanner();
        //获取地理位置
        LocationUtil.getLocation(this,handler);
        //获取手机型号
        Log.e("qpf","(手机型号:" + android.os.Build.MODEL + ",nSDK版本:" +
                               android.os.Build.VERSION.SDK + ",系统版本:" +
                android.os.Build.VERSION.RELEASE+")");
    }

    private void setLoopBanner() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRun) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVpBanner.setCurrentItem(index);
                            index++;
                        }
                    });
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {

                    }
                }
            }
        });
        thread.start();

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setClick() {
        mVpBanner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        isRun = true;
                        if (thread.isAlive()){
                            thread.start();
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:
                        isRun = false;
                        break;
                }
                return false;
            }
        });

        mVpBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               setDocCheck(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        if (Double.valueOf(android.os.Build.VERSION.SDK) > 23){
            scv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Log.e("qpf","头高:"+mllHead.getHeight() + "滑动:" + "(" + scrollX+","+scrollY+","+oldScrollX+","+oldScrollY+")");
                    if (mllHead.getHeight() < scrollY){
                        mllMenuTop.setVisibility(View.VISIBLE);
                    }else {
                        mllMenuTop.setVisibility(View.GONE);
                    }
                }
            });
        }

    }

    private void setDocCheck(int position) {
        for (TextView tv :tvDocs) {
            tv.setBackgroundResource(R.drawable.shape_whit_dot);
        }
        tvDocs[position%tvDocs.length].setBackgroundResource(R.drawable.shape_main_dot);
    }

    private void initData() {
        //获取轮播图
        BmobQuery<HomeBanner> query = new BmobQuery<>();
        query.findObjects(new FindListener<HomeBanner>() {
            @Override
            public void done(List<HomeBanner> list, BmobException e) {
                if (e == null){
                    Log.e("qpf","首页轮播 == " + list.size() +"条");
                    banners = list;
                    setHomeBanner();
                }
            }
        });

        //获取首页活动
        BmobQuery<Special> specialQuery = new BmobQuery<>();
        specialQuery.findObjects(new FindListener<Special>() {
            @Override
            public void done(List<Special> list, BmobException e) {
                if (e == null){
                    specials = list;
                    setSpecials();
                }
            }
        });

        //获取首页商店列表
        BmobQuery<HomeShop> shopQuery = new BmobQuery<>();
        shopQuery.findObjects(new FindListener<HomeShop>() {
            @Override
            public void done(List<HomeShop> list, BmobException e) {
                if (e == null){
                    data = list;
                    mUlvShop.setAdapter(new HomeShopAdapter(MainActivity.this,data));
                }
            }
        });

        //为商店上传商品分类
//        ProductClassify productClassify = new ProductClassify();
//        productClassify.setSubject("饮料");
//        productClassify.setShop_id("RJcm1118");
//        List<Product> products = new ArrayList<>();
//
//        Product product = new Product();
//        product.setShop_id("RJcm1118");
//        product.setClassify_id(productClassify.getObjectId());
//        product.setName("雪碧");
//        product.setImage_url("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2141856011,3445730211&fm=27&gp=0.jpg");
//        product.setStatus("雪碧-雪碧诞生于1961年,1984年正式进入中国。作为柠檬口味碳酸饮料的领导品牌,雪碧畅销于190多个国家,是世界排名第4的碳酸饮料。");
//        product.setPrice(3.00);
//        products.add(product);
//
//        productClassify.setProducts(products);
//
//        productClassify.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null){
//                    Log.e("qpf","成功");
//                }else {
//                    Log.e("qpf","失败" + e.toString());
//                }
//            }
//        });



//        //上传一个商店
//        HomeShop shop = new HomeShop("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3245281083,1654430883&fm=27&gp=0.jpg","啤酒和炸鸡");
//        ShopDiscounts discounts = new ShopDiscounts();
//        discounts.setKey_word("满");
//        discounts.setType(0);
//        discounts.setDiscounts_price("100");
//        discounts.setStatus("满20元减2元");
//        discounts.setMin_price("20");
//        List<ShopDiscounts> discountses = new ArrayList<>();
//        discountses.add(discounts);
//
//        ShopDiscounts discounts1 = new ShopDiscounts();
//        discounts1.setKey_word("新");
//        discounts1.setType(1);
//        discounts1.setDiscounts_price("100");
//        discounts1.setStatus("新用户立减5元");
//        discounts1.setMin_price("20");
//        discountses.add(discounts1);
//
//        ShopDiscounts discounts2 = new ShopDiscounts();
//        discounts2.setKey_word("券");
//        discounts2.setType(2);
//        discounts2.setDiscounts_price("100");
//        discounts2.setStatus("满100送20元的代金券");
//        discounts2.setMin_price("20");
//        discountses.add(discounts2);
//
//        shop.setDiscountsList(discountses);
//
//        shop.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null){
//                    Log.e("qpf","成功");
//                }else {
//                    Log.e("qpf","失败" + e.toString());
//                }
//            }
//        });
    }


    private void setSpecials() {
        for (int i = 0;i<mTvSpecialTitles.length;i++){
            mTvSpecialTitles[i].setText(specials.get(i).getTitle());
            mTvSubTitles[i].setText(specials.get(i).getState());
            Glide.with(this).load(specials.get(i).getUrl()).into(mIvSpecial[i]);
        }
    }

    private void setHomeBanner() {
        fragments = new Fragment[banners.size()];
        tvDocs = new TextView[banners.size()];
        for (int i = 0; i < banners.size(); i++){
            fragments[i] = new FragmentBanner(banners.get(i));
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
            params.leftMargin = 5;
            params.rightMargin = 5;
            tv.setBackgroundResource(R.drawable.shape_whit_dot);
            tv.setLayoutParams(params);
            mLlDot.addView(tv);
            tvDocs[i] = tv;
        }

        mVpBanner.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) { // 0/2 1/2 2/2 3/2
                //0 1 0 1
                return fragments[position%fragments.length];
            }

            @Override
            public int getCount() {  //无限大
                if (fragments.length > 3){
                    return Integer.MAX_VALUE;
                }
                return fragments.length;
            }
        });

        mVpBanner.setCurrentItem(index);
        setDocCheck(index);
    }

    private void initView() {
        mTvTitleLeft = (TextView) findViewById(R.id.tv_title_left);
        mTvTitleLeft.setSelected(true);
        mUlvShop = (UnScrollListView) findViewById(R.id.ulv_shop);
        scv = (ScrollView) findViewById(R.id.scv);
        mllHead = (LinearLayout) findViewById(R.id.ll_head);
        mllMenuTop = (LinearLayout) findViewById(R.id.ll_menu_top);
        mVpBanner = (ViewPager) findViewById(R.id.vp_banner);
        mLlDot = (LinearLayout) findViewById(R.id.ll_dot);
        mTvSpecialTitles = new TextView[]{(TextView) findViewById(R.id.tv_main_title1),(TextView) findViewById(R.id.tv_main_title2),(TextView) findViewById(R.id.tv_main_title3)};
        mTvSubTitles = new TextView[]{(TextView) findViewById(R.id.tv_sub_title1),(TextView) findViewById(R.id.tv_sub_title2),(TextView) findViewById(R.id.tv_sub_title3)};
        mIvSpecial = new ImageView[]{(ImageView) findViewById(R.id.iv_image1),(ImageView) findViewById(R.id.iv_image2),(ImageView) findViewById(R.id.iv_image3)};
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isRun = true;
        if (thread.isAlive()){
            thread.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRun = false;
    }
}
