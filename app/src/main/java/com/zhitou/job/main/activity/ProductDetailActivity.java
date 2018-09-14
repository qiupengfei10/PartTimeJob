package com.zhitou.job.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhitou.job.MyApplication;
import com.zhitou.job.R;
import com.zhitou.job.main.adapter.ProductDetailAdapter;
import com.zhitou.job.main.adapter.TaoBaoProductHAdapter;
import com.zhitou.job.main.been.ShopInfo;
import com.zhitou.job.main.been.TaoBaoBeen;
import com.zhitou.job.main.been.TaoBaoProductDetailBeen;
import com.zhitou.job.main.constant.Constant;
import com.zhitou.job.main.inter.ResultListener;
import com.zhitou.job.main.utils.BaseOkGoUtils;
import com.zhitou.job.main.utils.GlideImageLoader;
import com.zhitou.job.main.utils.GlideUtils;
import com.zhitou.job.main.utils.JsonUtils;
import com.zhitou.job.main.utils.TaoBaoUtils;
import com.zhitou.job.main.view.NoScrollGridView;
import com.zhitou.job.parttimejob.base.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品详情
 */
public class ProductDetailActivity extends BaseActivity implements View.OnClickListener{

    TextView mTvProductTitle;
    TextView mTvPrice;
    TextView mTvNum;
    ImageView mIvShopLogo;
    TextView mTvShopName;
    LinearLayout mLlGoDetail;
    LinearLayout mLlGoShop;
    LinearLayout mLlGoHome;
    Banner banner;
    TextView tv_bottom_price;
    TextView tv_baoyou;
    LinearLayout mLlGoBus;
    NoScrollGridView gridview;


    private String itemId;
    private TaoBaoProductDetailBeen taoBaoProductDetailBeen;
    private TaoBaoProductDetailBeen.DetailBean productDetail;
    private String shopId;
    private List<String> imgList;
    private String shopName;
    private ShopInfo.ResultsBean shopInfo;
    private List<TaoBaoBeen.GoodsBean> recommendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        itemId = getIntent().getStringExtra("itemId");
        initView();
        initClick();
        initData(itemId);
        //获取相关推荐
        getRecomment();
    }

    private void initClick() {
        mLlGoDetail.setOnClickListener(this);
        mLlGoShop.setOnClickListener(this);
        mLlGoHome.setOnClickListener(this);
        mLlGoBus.setOnClickListener(this);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetailNew(recommendList.get(position).getNumIid(),recommendList.get(position).getNick());
            }
        });
    }

    private void showDetailNew(String numIid,String shopName) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("itemId",numIid);
        startActivity(intent);
    }


    private void initView() {
        mTvProductTitle = (TextView) findViewById(R.id.tv_product_title);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvNum = (TextView) findViewById(R.id.tv_num);
        mIvShopLogo = (ImageView) findViewById(R.id.iv_shop_logo);
        mTvShopName = (TextView) findViewById(R.id.tv_shop_name);
        mLlGoDetail = (LinearLayout) findViewById(R.id.ll_go_taobao_detail);
        mLlGoShop = (LinearLayout) findViewById(R.id.ll_go_shop);
        mLlGoHome = (LinearLayout) findViewById(R.id.ll_go_home);
        banner = (Banner) findViewById(R.id.iv_banner);
        tv_bottom_price = (TextView) findViewById(R.id.tv_bottom_price);
        tv_baoyou = (TextView) findViewById(R.id.tv_baoyou);
        mLlGoBus = (LinearLayout) findViewById(R.id.ll_go_bus);
        gridview = (NoScrollGridView) findViewById(R.id.gridview);



        ViewGroup.LayoutParams params = banner.getLayoutParams();
        params.width = MyApplication.windowwidth;
        params.height = MyApplication.windowwidth;
        banner.setLayoutParams(params);
    }

    private void initData(String itemId) {
        Map<String, Object> mapDetail = new HashMap<>();
        mapDetail.put("numIid",itemId);
        BaseOkGoUtils.getOkGo(mapDetail, Constant.GET_TAOBAO_PTODUCT_DETAIL, this, new ResultListener() {
            @Override
            public void onSucceeded(Object object) {
                Log.e("qpf","商品详情 --- " + object);
                try{
                    taoBaoProductDetailBeen = JsonUtils.parseJsonWithGson(object, TaoBaoProductDetailBeen.class);
                    productDetail = taoBaoProductDetailBeen.getDetail();

                    shopName = productDetail.getNick();

                    mTvShopName.setText(shopName);

                    mTvProductTitle.setText(productDetail.getTitle());

                    mTvNum.setText("已抢购" + productDetail.getVolume()+"件");

                    mTvPrice.setText(productDetail.getZkFinalPrice()+"");

                    tv_bottom_price.setText("¥"+productDetail.getZkFinalPrice()+"");

                    if (productDetail.isFreeShipment()){
                        tv_baoyou.setVisibility(View.VISIBLE);
                    }else {
                        tv_baoyou.setVisibility(View.GONE);
                    }

                    getShopInfo();

                    setBanner();

                    setImageDetail();
                }catch (Exception e){
                    Log.e("qpf","商品详情有错误 -- " + e.toString());
                }

            }

            @Override
            public void onFailed(String content) {

                Log.e("qpf","获取商品详情 onFailed -- " + content.toString());
            }

            @Override
            public void onErr(String e) {
                Log.e("qpf","获取商品详情 onErr -- " + e.toString());
            }
        });
    }

    private void setImageDetail() {
        if (productDetail.getSmallImages() == null){
            return;
        }
        //设置显示的图片，这里通过过去item从而动态设置listview的总高度
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setFocusable(false);
        ProductDetailAdapter adapterList = new ProductDetailAdapter(this,productDetail.getSmallImages());
        int totalHeight = 0;
        for (int i = 0; i < adapterList.getCount(); i++) {
            View listItem = adapterList.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
//            totalHeight += MyApplication.windowwidth / showPicBean.getQiNius().get(i).getRatio();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (adapterList.getCount() - 1));
        listView.setLayoutParams(params);
        listView.setAdapter(adapterList);

        imgList  = new ArrayList<>();
        imgList = productDetail.getSmallImages();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProductDetailActivity.this, WatchImageActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("imagePaths", (Serializable) imgList);
                startActivity(intent);
            }
        });
    }

    private void setBanner() {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置图片集合
        if (productDetail != null && productDetail.getSmallImages() != null)
        banner.setImages(productDetail.getSmallImages());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        banner.start();
    }

    @Override
    public void onClick(View v) {  //打开淘宝相关的页面
        switch (v.getId()){
            case R.id.ll_go_home:
                finish();
                break;
            case R.id.ll_go_taobao_detail:
                TaoBaoUtils.showDetailById(this,itemId);
                break;
            case R.id.ll_go_shop:
                TaoBaoUtils.showShopByUrl(this,shopInfo.getShopUrl());
                break;
            case R.id.ll_go_bus:
                TaoBaoUtils.showCart(this);
                break;
        }
    }


    @Override
    public void onDestroy() {
//        AlibcTradeSDK.destory();
        super.onDestroy();
    }

    public void getShopInfo() {
        Map<String, Object> mapDetail = new HashMap<>();
        mapDetail.put("name",shopName);
        BaseOkGoUtils.getOkGo(mapDetail, Constant.GET_TAOBAO_PTODUCT_SHOP_INFO, this, new ResultListener() {
            @Override
            public void onSucceeded(Object object) {
                try{
                    Log.e("qpf","店铺信息 --- " + object);
                    shopInfo = JsonUtils.parseJsonWithGson(object,ShopInfo.class).getResults().get(0);

                    GlideUtils.showPicPlaceholderAndError(ProductDetailActivity.this,shopInfo.getPictUrl(), R.mipmap.default_image, R.mipmap.default_image,mIvShopLogo);

                    mTvShopName.setText(productDetail.getNick());
                }catch (Exception e){
                    Log.e("qpf","错误 -- " + e.toString());
                }

            }

            @Override
            public void onFailed(String content) {

                Log.e("qpf","店铺信息 onFailed -- " + content.toString());
            }

            @Override
            public void onErr(String e) {
                Log.e("qpf","店铺信息 onErr -- " + e.toString());
            }
        });
    }

    public void getRecomment() {
        Map<String, Object> mapDetail = new HashMap<>();
        mapDetail.put("numIid",itemId);
        BaseOkGoUtils.getOkGo(mapDetail, Constant.GET_TAOBAO_RECOMMEND_PRODUCT, this, new ResultListener() {
            @Override
            public void onSucceeded(Object object) {
                try{
                    Log.e("qpf","店铺信息 --- " + object);
                    recommendList = JsonUtils.parseJsonWithGson(object,TaoBaoBeen.class).getGoods();
                    gridview.setAdapter(new TaoBaoProductHAdapter(ProductDetailActivity.this,recommendList));

                }catch (Exception e){
                    Log.e("qpf","错误 -- " + e.toString());
                }

            }

            @Override
            public void onFailed(String content) {

                Log.e("qpf","店铺信息 onFailed -- " + content.toString());
            }

            @Override
            public void onErr(String e) {
                Log.e("qpf","店铺信息 onErr -- " + e.toString());
            }
        });
    }




}
