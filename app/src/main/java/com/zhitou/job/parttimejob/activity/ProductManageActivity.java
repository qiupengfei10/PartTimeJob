package com.zhitou.job.parttimejob.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhitou.job.R;
import com.zhitou.job.parttimejob.adapter.ProductManageAdapter;
import com.zhitou.job.parttimejob.base.BaseActivity;
import com.zhitou.job.parttimejob.been.Product;
import com.zhitou.job.parttimejob.been.ProductClassify;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 商品管理界面
 */
public class ProductManageActivity extends BaseActivity {

    private String shop_id;
    private List<ProductClassify> classifyList = new ArrayList<>();
    private List<Product> ProductAllList = new ArrayList<>();
    private List<Product> ProductList = new ArrayList<>();
    private HashMap<String,List<Product>> map = new HashMap();
    private ProductManageAdapter adapter;

    private LinearLayout mllClassify;
    TextView mTvCheck;
    private ListView mLvProductList;

    public static final int GET_DATA_OK = 100;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_OK:
                    if (isGetClassifyOk && isGetProductOk){
                        setData();
                    }
                    break;

            }
        }
    };
    private void setData() {
        for (int i = 0; i < classifyList.size();i++){
            map.put(classifyList.get(i).getObjectId(),new ArrayList<Product>());
        }

        for (int i = 0;i < ProductAllList.size();i++){
            map.get(ProductAllList.get(i).getClassify_id()).add(ProductAllList.get(i));
        }

        setClassifyAdapter();
        setProductAdapter(null);

    }

    private boolean isGetProductOk;
    private boolean isGetClassifyOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manage);
        shop_id = getIntent().getStringExtra("shop_id");

        initView();
        initData();

    }

    private void initData() {
        //获取本店类目
        //获取我的店铺标签
        BmobQuery<ProductClassify> queryClassify = new BmobQuery<>();
        queryClassify.addWhereEqualTo("shop_id",shop_id);
        queryClassify.findObjects(new FindListener<ProductClassify>() {
            @Override
            public void done(final List<ProductClassify> list, BmobException e) {
                if (e == null){
                    classifyList = list;
                    isGetClassifyOk = true;
                    handler.sendEmptyMessage(GET_DATA_OK);
                }
            }
        });
        //获取本店商品
        BmobQuery<Product> queryProduct = new BmobQuery<>();
        queryProduct.addWhereEqualTo("shop_id",shop_id);
        queryProduct.findObjects(new FindListener<Product>() {
            @Override
            public void done(final List<Product> list, BmobException e) {
                if (e == null){
                    ProductAllList = list;
                    isGetProductOk = true;
                    handler.sendEmptyMessage(GET_DATA_OK);
                }
            }
        });

    }

    private void setClassifyAdapter() {

        for (int i = 0; i < classifyList.size() + 1;i++){
            TextView tv = (TextView)LayoutInflater.from(this).inflate(R.layout.item_classify_textview,null);
            if (i == 0){
                int number = ProductAllList.size();
                tv.setText("全部("+number+")");
                mTvCheck = tv;
                mTvCheck.setTextColor(getResources().getColor(R.color.main_color));
            }else {
                int number = map.get(classifyList.get(i-1).getObjectId()).size();
                tv.setText(classifyList.get(i-1).getSubject() + "("+number+")");
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.rightMargin = 20;
            tv.setLayoutParams(params);
            mllClassify.addView(tv);

            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTvCheck.setTextColor(getResources().getColor(R.color.main_text_color));
                    mTvCheck = (TextView) view;
                    mTvCheck.setTextColor(getResources().getColor(R.color.main_color));
                    //设置adapter
                    if (finalI != 0){
                        setProductAdapter(classifyList.get(finalI - 1).getObjectId());
                    }else {
                        setProductAdapter(null);
                    }
                }
            });
        }

    }

    private void setProductAdapter(String classifyId) {
        if (adapter == null || classifyId == null){
            ProductList.addAll(ProductAllList);
            adapter = new ProductManageAdapter(this,ProductList);
            mLvProductList.setAdapter(adapter);
        }else {
            ProductList.clear();
            for (Product product:ProductAllList){
                if (product.getClassify_id().equals(classifyId)){
                    ProductList.add(product);
                }
            }

            adapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        setTitle("商品管理");
        mllClassify = (LinearLayout)findViewById(R.id.ll_classify);
        mLvProductList = (ListView) findViewById(R.id.lv_product_manage);
    }
}
