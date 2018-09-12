package com.zhitou.job.parttimejob.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhitou.job.R;
import com.zhitou.job.parttimejob.adapter.HomeShopAdapter;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.base.MyApplication;
import com.zhitou.job.parttimejob.been.HomeBanner;
import com.zhitou.job.parttimejob.been.HomeShop;
import com.zhitou.job.parttimejob.been.MyUser;
import com.zhitou.job.parttimejob.been.Special;
import com.zhitou.job.parttimejob.fragments.FragmentBanner;
import com.zhitou.job.parttimejob.utils.LocationUtil;
import com.zhitou.job.parttimejob.view.UnScrollListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;

public class HomeActivity extends BaseActivity {
    private DrawerLayout drawerLayout;
    private RelativeLayout mrightMenu;

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
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    AMapLocation aMapLocation = (AMapLocation) msg.obj;
                    mTvTitleLeft.setText(aMapLocation.getAddress());
                    Log.e("qpf", "经纬度:(" + aMapLocation.getLatitude() + "," + aMapLocation.getLongitude() + ")");
                    Toast.makeText(HomeActivity.this, "(" + aMapLocation.getLatitude() + "," + aMapLocation.getLongitude() + ")", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private TextView mTvUserName;
    private MyUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        initViews();
        initData();
        setClick();
        //设置图片无限循环
        setLoopBanner();
        //获取地理位置
        LocationUtil.getLocation(this, handler);
        //获取手机型号
        Log.e("qpf", "(手机型号:" + android.os.Build.MODEL + ",nSDK版本:" +
                android.os.Build.VERSION.SDK + ",系统版本:" +
                android.os.Build.VERSION.RELEASE + ")");
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
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        isRun = true;
                        if (thread.isAlive()) {
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


        if (Double.valueOf(android.os.Build.VERSION.SDK) > 23) {
            scv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Log.e("qpf", "头高:" + mllHead.getHeight() + "滑动:" + "(" + scrollX + "," + scrollY + "," + oldScrollX + "," + oldScrollY + ")");
                    if (mllHead.getHeight() < scrollY) {
                        mllMenuTop.setVisibility(View.VISIBLE);
                    } else {
                        mllMenuTop.setVisibility(View.GONE);
                    }
                }
            });
        }

    }

    private void setDocCheck(int position) {
        for (TextView tv : tvDocs) {
            tv.setBackgroundResource(R.drawable.shape_whit_dot);
        }
        tvDocs[position % tvDocs.length].setBackgroundResource(R.drawable.shape_main_dot);
    }

    private void initData() {
        //刷新用户信息
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                //刷新用户信息
                if (e == null){
                    Gson gson = new Gson();
                    user = gson.fromJson(s,MyUser.class);
                    MyApplication.getInstance().setUser(user);
                    log("");
                }else {
                    log("获取用户信息失败："+e.toString());
                }
            }
        });

        //获取轮播图
        BmobQuery<HomeBanner> query = new BmobQuery<>();
        query.findObjects(new FindListener<HomeBanner>() {
            @Override
            public void done(List<HomeBanner> list, BmobException e) {
                if (e == null) {
                    Log.e("qpf", "首页轮播 == " + list.size() + "条");
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
                if (e == null) {
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
                if (e == null) {
                    data = list;
                    mUlvShop.setAdapter(new HomeShopAdapter(HomeActivity.this, data));
                }
            }
        });

    }


    private void setSpecials() {
        for (int i = 0; i < mTvSpecialTitles.length; i++) {
            mTvSpecialTitles[i].setText(specials.get(i).getTitle());
            mTvSubTitles[i].setText(specials.get(i).getState());
            Glide.with(this).load(specials.get(i).getUrl()).into(mIvSpecial[i]);
        }
    }

    private void setHomeBanner() {
        fragments = new Fragment[banners.size()];
        tvDocs = new TextView[banners.size()];
        for (int i = 0; i < banners.size(); i++) {
            fragments[i] = new FragmentBanner(banners.get(i));
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
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
                return fragments[position % fragments.length];
            }

            @Override
            public int getCount() {  //无限大
                if (fragments.length > 3) {
                    return Integer.MAX_VALUE;
                }
                return fragments.length;
            }
        });

        mVpBanner.setCurrentItem(index);
        setDocCheck(index);
    }

    private void initViews() {
        initView();

        //打开右边侧拉菜单栏
        mrightMenu = (RelativeLayout)findViewById(R.id.right_menu);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        //关闭手势滑动
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //打开手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        int Wight = getWindowManager().getDefaultDisplay().getWidth();
        int windowsWight = (int) (Wight * 0.85);
        ViewGroup.LayoutParams layoutParams = mrightMenu.getLayoutParams();
        layoutParams.width = windowsWight;
        mrightMenu.setLayoutParams(layoutParams);


        mTvTitleLeft = (TextView) findViewById(R.id.tv_title_left);
        mTvTitleLeft.setSelected(true);
        mUlvShop = (UnScrollListView) findViewById(R.id.ulv_shop);
        scv = (ScrollView) findViewById(R.id.scv);
        mllHead = (LinearLayout) findViewById(R.id.ll_head);
        mllMenuTop = (LinearLayout) findViewById(R.id.ll_menu_top);
        mVpBanner = (ViewPager) findViewById(R.id.vp_banner);
        mLlDot = (LinearLayout) findViewById(R.id.ll_dot);
        mTvSpecialTitles = new TextView[]{(TextView) findViewById(R.id.tv_main_title1), (TextView) findViewById(R.id.tv_main_title2), (TextView) findViewById(R.id.tv_main_title3)};
        mTvSubTitles = new TextView[]{(TextView) findViewById(R.id.tv_sub_title1), (TextView) findViewById(R.id.tv_sub_title2), (TextView) findViewById(R.id.tv_sub_title3)};
        mIvSpecial = new ImageView[]{(ImageView) findViewById(R.id.iv_image1), (ImageView) findViewById(R.id.iv_image2), (ImageView) findViewById(R.id.iv_image3)};
    }


    private void initView() {
        //侧滑菜单中的控件
        mTvUserName = (TextView)findViewById(R.id.tv_user_name);

    }


    public void menuClick(View view) {
        switch (view.getId()){
            case R.id.ll_my_shop: // 我的店铺
                openMyShop();
                break;
        }
    }

    private void openMyShop() {
        if (user == null){
            showDialog();
        }else if (user.getIs_approve() == NOT_APPLY){
            goApprove();  // 去申请
        }else if (user.getIs_approve() == APPLY_FOR){
            //申请失败，申请中
            Intent intent = new Intent(this,ApplyActivity.class);
            intent.putExtra("status","申请中");
            intent.putExtra("hint","我们将在1-3个工作日内完成审核，届时将通过手机短信的方式将结果通知到您！");
            startActivity(intent);
        }else if (user.getIs_approve() == APPLY_FAIL){
            Intent intent = new Intent(this,ApplyActivity.class);
            intent.putExtra("status","审核未通过");
            intent.putExtra("hint","什么原因会造成实名未通过？" +
                    "\n1.上传身份证图片不清晰。" +
                    "\n2.身份信息不实。" +
                    "\n3.身份信息不符合平台要求。" +
                    "\n如以上信息均没问题，请联系官方客服进行咨询QQ：1715120163");
            startActivity(intent);

        }else if (user.getIs_approve() == APPLY_SUCCESS){
            //实名认证完成后跳转到我的店铺页面
            startActivity(new Intent(this,MyShopActivity.class));
        }
    }

    private void goApprove() {
        showDialogTwoBtn("实名认证后才能开通店铺哦~", "暂不认证", "立即认证", new OnClickListenerForDialogTwoBtn() {
            @Override
            public void onClickListenerForDialog(TextView tvBtn1, TextView tvBtn2) {
                tvBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissAler();
                    }
                });
                tvBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this,IDImageActivity.class);
                        startActivity(intent);
                        dismissAler();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("qpf","onActivityResult" + "("+requestCode+","+resultCode+")");
        switch (resultCode){
            case 0:  // 登录成功返回
                user = (MyUser) data.getSerializableExtra("user");
                mTvUserName.setText(user.getUsername());
                break;
        }
    }

    public void showDialog() {
        showDialogTwoBtn("登录后才能查看更多内容！", "暂不登录", "立即登录", new OnClickListenerForDialogTwoBtn() {
            @Override
            public void onClickListenerForDialog(TextView tvBtn1, TextView tvBtn2) {
                tvBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissAler();
                    }
                });
                tvBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(HomeActivity.this,LoginActivity.class);
                        startActivityForResult(i,100);
                        dismissAler();
                    }
                });
            }
        });

    }
}
