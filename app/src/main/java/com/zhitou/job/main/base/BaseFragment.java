package com.zhitou.job.main.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhitou.job.R;
import com.zhitou.job.main.view.LoadingView;


public abstract class BaseFragment extends Fragment{
    private View mContentView;
    private Context mContext;
    private LoadingView loadingView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(),container,false);
//        ButterKnife.bind(this,mContentView);
        mContext = getContext();
        init();
        setUpView();
        setUpData();
        return mContentView;
    }

    public void showLoading(){
        if (loadingView == null){
            loadingView = new LoadingView(getActivity(), R.style.CustomDialog);
        }
        loadingView.show();
    }

    public void dismissLoading(){
        if (loadingView == null){
            return;
        }
        loadingView.dismiss();
    }

    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID
     *
     * @return 布局文件资源ID
     */
    protected abstract int setLayoutResourceID();

    /**
     * 一些View的相关操作
     */
    protected abstract void setUpView();

    /**
     * 一些Data的相关操作
     */
    protected abstract void setUpData();

    /**
     * 此方法用于初始化成员变量及获取Intent传递过来的数据
     * 注意：这个方法中不能调用所有的View，因为View还没有被初始化，要使用View在initView方法中调用
     */
    protected void init() {}

    public View getContentView() {
        return mContentView;
    }

    public Context getMContext() {
        return mContext;
    }

    protected void L(String tag,String content){
        Log.e(getMContext().getPackageName(),"【"+tag+"】"+content);
    }

}
