package com.zhitou.job.parttimejob.base;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by qiupengfei on 2017/10/11.
 */
public class MyApplication extends Application{
    private static MyApplication ourInstance = new MyApplication();

    public static MyApplication getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"00486c3673bc2dd80341ca35b507659b");
    }
}
