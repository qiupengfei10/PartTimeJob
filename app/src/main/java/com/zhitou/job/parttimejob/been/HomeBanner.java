package com.zhitou.job.parttimejob.been;

import cn.bmob.v3.BmobObject;

/**
 * Created by qiupengfei on 2017/7/13.
 */
public class HomeBanner extends BmobObject{
    /**
     * title : null
     * url : https://app.jia16.com/mjia/external_page/allDivide/allDivide.html
     * type : https://www.jia16.com/static/dam/jcr:e70c57e1-ee99-4351-8008-bd306c55a449/20170630APp%E5%85%A8%E6%B0%91%E7%93%9C%E5%88%862%E7%89%88%E7%A7%BB%E5%8A%A8banner.png
     * date : null
     * totalPage : null
     */
    private String url;
    private String type;

    public HomeBanner(String url, String type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
