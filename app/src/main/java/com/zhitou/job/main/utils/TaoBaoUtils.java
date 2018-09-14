package com.zhitou.job.main.utils;

import android.app.Activity;
import android.util.Log;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;

import java.util.HashMap;

/**
 * Created by LCH on 2018/9/5.
 */
public class TaoBaoUtils {

    //打开商品详情
    public static void showDetailById(Activity activity,String productId){
        AlibcBasePage alibcBasePage;
        alibcBasePage = new AlibcDetailPage(productId);

        //打开商品的方式 打开方式
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams();

        alibcTaokeParams.adzoneid = "12159000127";
        alibcTaokeParams.pid = "mm_159320187_62600381_12159000127";
        alibcTaokeParams.subPid = "mm_159320187_62600381_12159000127";
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey","25004000");

        //打开商品详情
        AlibcTrade.show(activity, alibcBasePage, alibcShowParams, alibcTaokeParams, null, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                Log.e("qpf","打开详情页面成功 --- " + alibcTradeResult.toString());
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("qpf","打开详情页面失败 --- " + i + "】," + s);
            }
        });

    }

    //打开我的购物车
    public static void showCart(Activity activity){
        AlibcBasePage alibcBasePage;
        alibcBasePage = new AlibcMyCartsPage();

        //打开商品的方式 打开方式
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams();

        alibcTaokeParams.adzoneid = "12159000127";
        alibcTaokeParams.pid = "mm_159320187_62600381_12159000127";
        alibcTaokeParams.subPid = "mm_159320187_62600381_12159000127";
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey","25004000");

        //打开商品详情
        AlibcTrade.show(activity, alibcBasePage, alibcShowParams, alibcTaokeParams, null, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                Log.e("qpf","打开详情页面成功 --- " + alibcTradeResult.toString());
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("qpf","打开详情页面失败 --- " + i + "】," + s);
            }
        });

    }

    /**
     * @param url
     * 打开指定链接
     */
    public static void showShopByUrl(Activity activity, String url) {
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams(); // 若非淘客taokeParams设置为null即可
        alibcTaokeParams.adzoneid = "12159000127";
        alibcTaokeParams.pid = "mm_159320187_62600381_12159000127";
        alibcTaokeParams.subPid = "mm_159320187_62600381_12159000127";
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey","25004000");
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        HashMap<String, String> exParams = new HashMap<>();
        AlibcTrade.show(activity, new AlibcPage(url), alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }



    //打开店铺通过banner
    /**
     * @param url
     * 打开指定链接
     *
     */
    public static void showShopByUrlForBanner(Activity activity, String url) {
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams(); // 若非淘客taokeParams设置为null即可
        alibcTaokeParams.adzoneid = "20302050140";
        alibcTaokeParams.pid = "mm_159320187_62600381_20302050140";
        alibcTaokeParams.subPid = "mm_159320187_62600381_20302050140";
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey","25004000");
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        HashMap<String, String> exParams = new HashMap<>();
        AlibcTrade.show(activity, new AlibcPage(url), alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }


}
