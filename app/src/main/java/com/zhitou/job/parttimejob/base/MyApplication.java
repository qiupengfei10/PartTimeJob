package com.zhitou.job.parttimejob.base;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * Created by qiupengfei on 2017/10/11.
 */
public class MyApplication extends Application{
    public static MyApplication ourInstance = new MyApplication();
    public static MyApplication getInstance() {
        return ourInstance;
    }

    public static List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"00486c3673bc2dd80341ca35b507659b");
    }

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }
    public static void finshActivity(Activity activity) {
        for (Activity a:activities) {
            if (a.equals(activity)){
                finshActivity(a);
            }
        }
    }
}
