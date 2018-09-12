package com.zhitou.job.parttimejob.been;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

/**
 * Created by qiupengfei on 2017/10/17.
 */
public class MyUser extends BmobUser implements Serializable{
    private String see_password;//可见密码
    private String real_name;//真是姓名
    private int is_approve;//是否实名  0 未实名 1实名  2实名资料已提交 3实名未通过
    private String school;//学校

    private String NickName;
    private String UserImage;

    private String IDCard1;
    private String IDCard2;


    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public String getIDCard1() {
        return IDCard1;
    }

    public void setIDCard1(String IDCard1) {
        this.IDCard1 = IDCard1;
    }

    public String getIDCard2() {
        return IDCard2;
    }

    public void setIDCard2(String IDCard2) {
        this.IDCard2 = IDCard2;
    }

    public String getSee_password() {
        return see_password;
    }

    public void setSee_password(String see_password) {
        this.see_password = see_password;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }


    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getIs_approve() {
        return is_approve;
    }

    public void setIs_approve(int is_approve) {
        this.is_approve = is_approve;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "see_password='" + see_password + '\'' +
                ", real_name='" + real_name + '\'' +
                ", is_approve=" + is_approve +
                ", school='" + school + '\'' +
                ", NickName='" + NickName + '\'' +
                ", UserImage='" + UserImage + '\'' +
                ", IDCard1='" + IDCard1 + '\'' +
                ", IDCard2='" + IDCard2 + '\'' +
                '}';
    }
}
