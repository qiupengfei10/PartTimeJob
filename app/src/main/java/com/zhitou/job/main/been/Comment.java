package com.zhitou.job.main.been;

import com.zhitou.job.parttimejob.been.MyUser;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {
    private MyUser user; // 自己
    private MyUser returnUser; //回复评论的人
    private String content; //内容

    public Comment(MyUser user, MyUser returnUser, String content) {
        this.user = user;
        this.returnUser = returnUser;
        this.content = content;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public MyUser getReturnUser() {
        return returnUser;
    }

    public void setReturnUser(MyUser returnUser) {
        this.returnUser = returnUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
