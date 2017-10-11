package com.zhitou.job.parttimejob.been;

import cn.bmob.v3.BmobObject;

/**
 * Created by qiupengfei on 2017/7/14.
 *
 * 专题列表
 */
public class Special extends BmobObject{
    private String url;//图片
    private String title;//标题
    private String state;//二级标题

    public Special(String url, String title, String state) {
        this.url = url;
        this.title = title;
        this.state = state;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
