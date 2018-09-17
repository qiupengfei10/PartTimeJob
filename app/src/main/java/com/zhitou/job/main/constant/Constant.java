package com.zhitou.job.main.constant;

/**
 * Created by LCH on 2018/9/13.
 */
public class Constant {
    public static String TR_PAY_APPKEY = "abab2e460219495da06ee4ca26ef6d31";

    //    淘宝客的域名
    private static String TAOBAO_LINKURIPRE = "http://tbk.mjcos.com:80";

    //获取淘宝客列表数据数据
    public static final String GET_TAOBAO_PTODUCT = TAOBAO_LINKURIPRE + "/api/TBK/GET";

    //获取淘宝详情数据
    public static final String GET_TAOBAO_PTODUCT_DETAIL = TAOBAO_LINKURIPRE + "/api/TBK/GetDetail";

    //获取店铺信息
    public static final String GET_TAOBAO_PTODUCT_SHOP_INFO = TAOBAO_LINKURIPRE + "/api/TBK/GetShopInfo";

    //获取相关推荐商品
    public static final String GET_TAOBAO_RECOMMEND_PRODUCT = TAOBAO_LINKURIPRE + "/api/TBK/GetRecommend";


}
