package com.zhitou.job.main.been;

/**
 * Created by LCH on 2018/9/12.
 *
 * 图片的长宽高
 */
public class ImageBeen {

    private String image_key;
    private String with;
    private String height;
    private String path;
    private String ratio;

    public ImageBeen(String image_key, String with, String height, String path, String ratio) {
        this.image_key = image_key;
        this.with = with;
        this.height = height;
        this.path = path;
        this.ratio = ratio;
    }

    public String getImage_key() {
        return image_key;
    }

    public void setImage_key(String image_key) {
        this.image_key = image_key;
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
