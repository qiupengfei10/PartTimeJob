package com.zhitou.job.parttimejob.been;

import cn.bmob.v3.BmobObject;

/**
 * Created by qiupengfei on 2017/10/11.
 *
 * 店内活动
 *
 * 满 赠 返 减
 */
public class ShopDiscounts extends BmobObject{
    private int type;               //活动类型
    private String key_word;        //　关键字
    private String status;          //  活动描述
    private String min_price;       // 消费多少可用
    private String discounts_price; //优惠价格

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey_word() {
        return key_word;
    }

    public void setKey_word(String key_word) {
        this.key_word = key_word;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMin_price() {
        return min_price;
    }

    public void setMin_price(String min_price) {
        this.min_price = min_price;
    }

    public String getDiscounts_price() {
        return discounts_price;
    }

    public void setDiscounts_price(String discounts_price) {
        this.discounts_price = discounts_price;
    }
}
