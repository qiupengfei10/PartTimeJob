package com.zhitou.job.main.inter;

/**
 * Created by icebox12 on 2017/a/13.
 */

public interface ResultListener {
    void onSucceeded(Object object);
    void onFailed(String content);
    void onErr(String e);
}
