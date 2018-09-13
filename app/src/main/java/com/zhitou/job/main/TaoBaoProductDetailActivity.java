package com.zhitou.job.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhitou.job.R;
import com.zhitou.job.parttimejob.base.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品详情
 */
public class TaoBaoProductDetailActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.common_toolbar)
    RelativeLayout common_head;
    @BindView(R.id.common_toolbar_left_img)
    ImageView head_left_img;
    @BindView(R.id.common_toolbar_center_title)
    TextView head_center_title;
    @BindView(R.id.common_toolbar_right_img)
    ImageView head_right_img;

    @BindView(R.id.tv_product_title)
    TextView mTvProductTitle;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.iv_shop_logo)
    ImageView mIvShopLogo;
    @BindView(R.id.tv_shop_name)
    TextView mTvShopName;
    @BindView(R.id.ll_go_taobao_detail)
    LinearLayout mLlGoDetail;
    @BindView(R.id.ll_go_shop)
    LinearLayout mLlGoShop;
    @BindView(R.id.ll_go_home)
    LinearLayout mLlGoHome;
    @BindView(R.id.iv_banner)
    Banner banner;
    @BindView(R.id.tv_bottom_price)
    TextView tv_bottom_price;
    @BindView(R.id.tv_baoyou)
    TextView tv_baoyou;
    @BindView(R.id.ll_go_bus)
    LinearLayout mLlGoBus;
    @BindView(R.id.gridview)
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
        CommonHeadUtils.fullScreen(this);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
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
        CommonHeadUtils.allDone(this, common_head, R.color.white, head_left_img, R.mipmap.back_white, head_center_title, "商品详情");
        ViewUtils.setMargins(head_left_img, 8, 0, 0, 0);
        common_head.setBackgroundResource(R.mipmap.title_bg);
        head_right_img.setImageResource(R.mipmap.shoppingcart_icon);
        head_right_img.setVisibility(View.GONE);
        head_left_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        ViewGroup.LayoutParams params = banner.getLayoutParams();
        params.width = MyApplication.windowwidth;
        params.height = MyApplication.windowwidth;
        banner.setLayoutParams(params);
    }

    private void initData(String itemId) {
        Map<String, Object> mapDetail = new HashMap<>();
        mapDetail.put("numIid",itemId);
        BaseOkGoUtils.getOkGo(mapDetail, LinkUrlConstant.GET_TAOBAO_PTODUCT_DETAIL, this, new ResultListener() {
            @Override
            public void onSucceeded(Object object) {
                L.e("qpf","商品详情 --- " + object);
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
                    L.e("qpf","商品详情有错误 -- " + e.toString());
                }

            }

            @Override
            public void onFailed(String content) {

                L.e("qpf","获取商品详情 onFailed -- " + content.toString());
            }

            @Override
            public void onErr(String e) {
                L.e("qpf","获取商品详情 onErr -- " + e.toString());
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
    public void onClick(View v) {
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
        AlibcTradeSDK.destory();
        super.onDestroy();
    }

    public void getShopInfo() {
        Map<String, Object> mapDetail = new HashMap<>();
        mapDetail.put("name",shopName);
        BaseOkGoUtils.getOkGo(mapDetail, LinkUrlConstant.GET_TAOBAO_PTODUCT_SHOP_INFO, this, new ResultListener() {
            @Override
            public void onSucceeded(Object object) {
                try{
                    L.e("qpf","店铺信息 --- " + object);
                    shopInfo = JsonUtils.parseJsonWithGson(object,ShopInfo.class).getResults().get(0);

                    GlideUtils.showPicPlaceholderAndError(ProductDetailActivity.this,shopInfo.getPictUrl(), R.mipmap.default_image, R.mipmap.default_image,mIvShopLogo);

                    mTvShopName.setText(productDetail.getNick());
                }catch (Exception e){
                    L.e("qpf","错误 -- " + e.toString());
                }

            }

            @Override
            public void onFailed(String content) {

                L.e("qpf","店铺信息 onFailed -- " + content.toString());
            }

            @Override
            public void onErr(String e) {
                L.e("qpf","店铺信息 onErr -- " + e.toString());
            }
        });
    }

    public void getRecomment() {
        Map<String, Object> mapDetail = new HashMap<>();
        mapDetail.put("numIid",itemId);
        BaseOkGoUtils.getOkGo(mapDetail, LinkUrlConstant.GET_TAOBAO_RECOMMEND_PRODUCT, this, new ResultListener() {
            @Override
            public void onSucceeded(Object object) {
                try{
                    L.e("qpf","店铺信息 --- " + object);
                    recommendList = JsonUtils.parseJsonWithGson(object,TaoBaoBeen.class).getGoods();
                    gridview.setAdapter(new TaoBaoProductHAdapter(ProductDetailActivity.this,recommendList));

                }catch (Exception e){
                    L.e("qpf","错误 -- " + e.toString());
                }

            }

            @Override
            public void onFailed(String content) {

                L.e("qpf","店铺信息 onFailed -- " + content.toString());
            }

            @Override
            public void onErr(String e) {
                L.e("qpf","店铺信息 onErr -- " + e.toString());
            }
        });
    }


    public void showDetail(Activity activity, String productId) {
        AlibcBasePage alibcBasePage;
        alibcBasePage = new AlibcDetailPage(productId);

        //打开商品的方式 打开方式
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams();

        alibcTaokeParams.adzoneid = "12159000127";
        alibcTaokeParams.pid = "mm_159320187_62600381_12159000127";
        alibcTaokeParams.subPid = "mm_159320187_62600381_12159000127";
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey","25004000");

        //打开商品详情
        AlibcTrade.show(activity, alibcBasePage, alibcShowParams, alibcTaokeParams, null, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                L.e("qpf","打开详情页面成功 --- " + alibcTradeResult.toString());
            }

            @Override
            public void onFailure(int i, String s) {
                L.e("qpf","打开详情页面失败 --- " + i + "】," + s);
            }
        });

    }

    /**
     * @param url
     * 打开指定链接
     */
    public void showUrl(Activity activity, String url) {
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams(); // 若非淘客taokeParams设置为null即可
        alibcTaokeParams.adzoneid = "12159000127";
        alibcTaokeParams.pid = "mm_159320187_62600381_12159000127";
        alibcTaokeParams.subPid = "mm_159320187_62600381_12159000127";
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey","25004000");
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        HashMap<String, String> exParams = new HashMap<>();
        AlibcTrade.show(activity, new AlibcPage(url), alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    /**
     * 显示商品详情页  打开商品详情
     */
    public void showMyShopBus(Activity activity) {
        //实例化店铺打开page
        AlibcBasePage myCartsPage = new AlibcMyCartsPage();

        HashMap<String, String> exParams = new HashMap<>();

        //打开商品的方式 打开方式
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Native, false);

        //
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams();

        alibcTaokeParams.adzoneid = "12159000127";
        alibcTaokeParams.pid = "mm_159320187_62600381_12159000127";
        alibcTaokeParams.subPid = "mm_159320187_62600381_12159000127";
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey","25004000");

        //打开商品详情
        AlibcTrade.show(activity, myCartsPage, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                L.e("qpf","打开详情页面成功 --- " + alibcTradeResult.toString());
            }

            @Override
            public void onFailure(int i, String s) {
                L.e("qpf","打开详情页面失败 --- " + i + "】," + s);
            }
        });

    }

}
