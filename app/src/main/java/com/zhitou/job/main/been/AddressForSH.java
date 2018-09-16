package com.zhitou.job.main.been;

import cn.bmob.v3.BmobObject;

public class AddressForSH extends BmobObject {
    private String userId;
    private String City;
    private String DetailAddress;
    private String District;
    private String Phone;
    private String Province;
    private String UserName;
    private String YZcode;

    public AddressForSH(String userId, String city, String detailAddress, String district, String phone, String province, String userName, String YZcode) {
        this.userId = userId;
        City = city;
        DetailAddress = detailAddress;
        District = district;
        Phone = phone;
        Province = province;
        UserName = userName;
        this.YZcode = YZcode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDetailAddress() {
        return DetailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        DetailAddress = detailAddress;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getYZcode() {
        return YZcode;
    }

    public void setYZcode(String YZcode) {
        this.YZcode = YZcode;
    }
}
