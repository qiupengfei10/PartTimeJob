package com.zhitou.job.parttimejob.been;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

/**
 * Created by qiupengfei on 2017/10/17.
 */
public class MyUser extends BmobUser implements Serializable{
    private String see_password;//可见密码
    private String real_name;//真是姓名
    private boolean is_approve;//是否实名
    private String school;//学校

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

    public boolean is_approve() {
        return is_approve;
    }

    public void setIs_approve(boolean is_approve) {
        this.is_approve = is_approve;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
