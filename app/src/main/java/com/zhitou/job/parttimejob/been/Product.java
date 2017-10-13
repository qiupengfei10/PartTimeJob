package com.zhitou.job.parttimejob.been;

import cn.bmob.v3.BmobObject;

/**
 * Created by qiupengfei on 2017/6/26.
 */
public class Product extends BmobObject{
    private String shop_id;  //商店id
    private String classify_id; //分类id
    private String image_url;//图片
    private String name;//名称
    private String status;//介绍
    private double price;//单价
    private int sale;//月售量

    public String getClassify_id() {
        return classify_id;
    }

    public void setClassify_id(String classify_id) {
        this.classify_id = classify_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "Product{" +
                "shop_id='" + shop_id + '\'' +
                ", classify_id='" + classify_id + '\'' +
                ", image_url='" + image_url + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", sale=" + sale +
                '}';
    }
}
