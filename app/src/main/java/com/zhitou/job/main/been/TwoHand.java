package com.zhitou.job.main.been;

import com.zhitou.job.parttimejob.been.MyUser;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class TwoHand extends BmobObject {

    private String Images;
    private String Title;
    private String Content;
    private String Address;
    private String Price;
    private String Sub;

    private MyUser PushUser;

    public MyUser getPushUser() {
        return PushUser;
    }

    public void setPushUser(MyUser pushUser) {
        PushUser = pushUser;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSub() {
        return Sub;
    }

    public void setSub(String sub) {
        Sub = sub;
    }
}