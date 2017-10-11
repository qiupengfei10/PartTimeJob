package com.zhitou.job.parttimejob.been;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by qiupengfei on 2017/7/14.
 *
 * 首页的店铺
 */
public class HomeShop extends BmobObject{
    private String shop_logo;//商店logo
    private String shopName;//店名
    private String sale;//月销量
    private String reputation;//好评
    private String longitude;//经度
    private String latitude;//纬度
    private String mininum_consume;//最低消费(多少钱起送)
    private String postage;//配送费
    //本店活动
    private List<ShopDiscounts> discountsList;

    public List<ShopDiscounts> getDiscountsList() {
        return discountsList;
    }

    public void setDiscountsList(List<ShopDiscounts> discountsList) {
        this.discountsList = discountsList;
    }

    public HomeShop(String shop_logo, String shopName) {
        this.shop_logo = shop_logo;
        this.shopName = shopName;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getReputation() {
        return reputation;
    }

    public void setReputation(String reputation) {
        this.reputation = reputation;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getMininum_consume() {
        return mininum_consume;
    }

    public void setMininum_consume(String mininum_consume) {
        this.mininum_consume = mininum_consume;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }
}
