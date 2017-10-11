package com.zhitou.job.parttimejob.been;

import java.util.List;

/**
 * Created by qiupengfei on 2017/6/26.
 *
 * 商品分类
 */
public class ProductClassify {
    //种类
    private String subject;
    //商品
    private List<Product> products;

    public ProductClassify(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
