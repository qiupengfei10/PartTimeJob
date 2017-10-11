package com.zhitou.job.parttimejob.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
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

import com.zhitou.job.parttimejob.R;
import com.zhitou.job.parttimejob.adapter.ShopMainAdapter;
import com.zhitou.job.parttimejob.adapter.SubAdapter;
import com.zhitou.job.parttimejob.adapter.ProductAdapter;
import com.zhitou.job.parttimejob.been.Point;
import com.zhitou.job.parttimejob.been.Product;
import com.zhitou.job.parttimejob.been.ProductClassify;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 */
public class ShopMainActivity extends AppCompatActivity {

    private RelativeLayout mRlBackground;
    private ScrollView mSrvContent;
    private ListView mUlvSub;
    private ListView mUlvContent;
    private TextView mTvSubject;

    private List<String> subs;
    private List<ProductClassify> products;


    //当前选中的一级菜单
    private View currentSelecetView;

    private LinearLayout mllBg;

    private ImageView mIvShopBus;

    private TextView mtvBusQuantity; //购物车中商品的数量

    private List<Product> mShopBus = new ArrayList<>();


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
                    mShopBus.add(products.get(msg.arg1).getProducts().get(msg.what));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_main);
        initView();
        initData();
        setClick();
    }

    private void setClick() {

        mUlvContent.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("qpf", "可见的第一个item:" + firstVisibleItem);
                //可见的第一个item
                mTvSubject.setText(products.get(firstVisibleItem).getSubject());
                mUlvSub.smoothScrollToPositionFromTop(firstVisibleItem, 0, 300);
                setSelectedStatus(firstVisibleItem);
            }
        });

        mUlvSub.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setSelectedStatus(position);
                mUlvContent.setSelection(position);
            }
        });


    }

    private void initData() {
        subs = new ArrayList<>();
        products = new ArrayList<>();

        subs.add("巧乐滋系列");
        subs.add("冰红茶系列");
        subs.add("康师傅系列");
        subs.add("麦当劳");
        subs.add("坑德基");
        subs.add("统一系列");
        subs.add("小米系列");
        subs.add("魅族系列");
        subs.add("华为系列");
        subs.add("三星系列");
        subs.add("诺基亚系列");

        products.add(new ProductClassify("巧乐滋系列"));
        products.add(new ProductClassify("冰红茶系列"));
        products.add(new ProductClassify("康师傅系列"));
        products.add(new ProductClassify("麦当劳"));
        products.add(new ProductClassify("坑德基"));
        products.add(new ProductClassify("统一系列"));
        products.add(new ProductClassify("小米系列"));
        products.add(new ProductClassify("魅族系列"));
        products.add(new ProductClassify("华为系列"));
        products.add(new ProductClassify("三星系列"));
        products.add(new ProductClassify("诺基亚系列"));


        for (ProductClassify product : products) {
            product.setProducts(new ArrayList<Product>());
            product.getProducts().add(new Product());
            product.getProducts().add(new Product());
            product.getProducts().add(new Product());
            product.getProducts().add(new Product());
            product.getProducts().add(new Product());
            product.getProducts().add(new Product());
        }

        mUlvContent.setAdapter(new ShopMainAdapter(this, products, handlder));

        mUlvSub.setAdapter(new SubAdapter(this, subs));
        mllBg = (LinearLayout) findViewById(R.id.ll_bg);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initView() {
        mRlBackground = (RelativeLayout) findViewById(R.id.rl_background);
        mUlvSub = (ListView) findViewById(R.id.lv_sub_table);
        mUlvContent = (ListView) findViewById(R.id.lv_content);
        mTvSubject = (TextView) findViewById(R.id.tv_subject);
        mIvShopBus = (ImageView) findViewById(R.id.iv_shop_bus);

        mtvBusQuantity = (TextView) findViewById(R.id.tv_bus_quantity);
    }

    /**
     * 设置选中状态
     *
     * @param position
     */
    public void setSelectedStatus(int position) {
        //找到第一个可见的
        int firstPosition = mUlvSub.getFirstVisiblePosition();
        position = position - firstPosition;
        View view = mUlvSub.getChildAt(position);
        if (view == null) {
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
}
