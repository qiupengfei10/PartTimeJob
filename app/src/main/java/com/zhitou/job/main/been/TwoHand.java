package com.zhitou.job.main.been;

import com.zhitou.job.parttimejob.been.MyUser;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class TwoHand extends BmobObject implements Serializable{
    private String Title;
    private String Content;
    private String Address;
    private String Price;
    private String Sub;
    private String Postage; //邮费
    private MyUser PushUser;
    private ArrayList<ImageBeen> imageBeens;

    public ArrayList<ImageBeen> getImageBeens() {
        return imageBeens;
    }

    public String getPostage() {
        return Postage;
    }

    public void setPostage(String postage) {
        Postage = postage;
    }


    public void setImageBeens(ArrayList<ImageBeen> imageBeens) {
        this.imageBeens = imageBeens;
    }

    public MyUser getPushUser() {
        return PushUser;
    }

    public void setPushUser(MyUser pushUser) {
        PushUser = pushUser;
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
