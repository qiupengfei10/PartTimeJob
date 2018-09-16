package com.zhitou.job.main.been;

import com.zhitou.job.parttimejob.been.MyUser;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

public class CommentForTwoHand extends BmobObject{

    private MyUser user;
    private TwoHand twoHand;
    private String content;
    private ArrayList<Comment> returnCommnet;

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public TwoHand getTwoHand() {
        return twoHand;
    }

    public void setTwoHand(TwoHand twoHand) {
        this.twoHand = twoHand;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Comment> getReturnCommnet() {
        return returnCommnet;
    }

    public void setReturnCommnet(ArrayList<Comment> returnCommnet) {
        this.returnCommnet = returnCommnet;
    }
}
