package com.zhitou.job.parttimejob.been;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by qiupengfei on 2017/6/26.
 *
 * 商品分类
 */
public class ProductClassify extends BmobObject{

    private int index;  // 序号

    private String shop_id; // 商店id

    private String subject; // 分类名称


    public String getShop_id() {
        return shop_id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public ProductClassify(String subject) {
        this.subject = subject;
    }
    public ProductClassify() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}
