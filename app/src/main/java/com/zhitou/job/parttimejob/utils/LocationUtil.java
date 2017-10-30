package com.zhitou.job.parttimejob.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by qiupengfei on 2017/7/14.
 *
 * 高德地图定位工具
 */
public class LocationUtil extends Activity{
    private static Handler handler;

    //声明AMapLocationClient类对象
    public static AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public static AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.e("qpf","定位结果:" + aMapLocation.getAddress());
            Message msg = Message.obtain();
            msg.what = 100;
            msg.obj = aMapLocation;
            handler.sendMessage(msg);
        }
    };

    //声明AMapLocationClientOption对象
    public static AMapLocationClientOption mLocationOption = null;

    public static void getLocation(Activity context,Handler handler){
        LocationUtil.handler = handler;
        openAuthority(context);
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }








    private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 0;
//SDK在Android 6.0下需要进行运行检测的权限如下：
//    Manifest.permission.ACCESS_COARSE_LOCATION,
//    Manifest.permission.ACCESS_FINE_LOCATION,
//    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//    Manifest.permission.READ_EXTERNAL_STORAGE,
//    Manifest.permission.READ_PHONE_STATE


    //检测权限
    public static void openAuthority(Activity context) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_PHONE_STATE},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        }
    }
//这里以ACCESS_COARSE_LOCATION为例


}
