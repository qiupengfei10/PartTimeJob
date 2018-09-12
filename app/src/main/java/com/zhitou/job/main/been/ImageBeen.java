package com.zhitou.job.main.been;

import cn.bmob.v3.BmobObject;

/**
 * Created by LCH on 2018/9/12.
 *
 * 图片的长宽高
 */
public class ImageBeen extends BmobObject{

    private String with;
    private String height;
    private String path;
    private String ratio;

    public ImageBeen(String with, String height, String path, String ratio) {
        this.with = with;
        this.height = height;
        this.path = path;
        this.ratio = ratio;
    }

    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}
