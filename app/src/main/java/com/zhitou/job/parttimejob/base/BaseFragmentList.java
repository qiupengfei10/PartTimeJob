package com.zhitou.job.parttimejob.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zhitou.job.parttimejob.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by qiupengfei on 2017/10/13.
 */
public abstract class BaseFragmentList<T> extends Fragment{
    protected View view;
    protected ListView listview;

    protected String key,value;

    protected List<T> data = new ArrayList<>();

    protected MyBaseAdapter<T> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView();
        adapter = getAdapter();
        listview.setAdapter(adapter);
        initData();
        return view;
    }

    protected abstract void initData();
    protected void initView(){
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list,null);
        listview = (ListView) view.findViewById(R.id.lv_list);
    }

    protected abstract MyBaseAdapter<T> getAdapter();
}
